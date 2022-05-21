import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { Page } from 'src/app/commons/page';
import { Pageable, PageableBuilder } from 'src/app/commons/pageable';
import { Device } from 'src/app/models/device';
import { DeviceDetails } from 'src/app/models/device-details';
import { DeviceService } from 'src/app/services/device/device.service';
import { ConfirmDialogComponent } from '../../../confirm-dialog/confirm-dialog.component';
import { DeviceAddFormComponent } from '../device-add-form/device-add-form.component';

@Component({
  selector: 'app-device-list',
  templateUrl: './device-list.component.html',
  styleUrls: ['./device-list.component.scss']
})
export class DeviceListComponent implements OnInit {

  @ViewChild('table') table!: MatTable<DeviceDetails[]>;
  @ViewChild('paginator') paginator!: MatPaginator;
  
  displayedColumns: string[] = ['assigned', 'id', 'description', 'address', 
                                'maxEnergyConsumption', 'avgEnergyConsumption', 'actions'];
  dataSource!: Page<DeviceDetails>;
  selection: any;

  constructor(private deviceService: DeviceService,
      private dialog: MatDialog,
      private snackBar: MatSnackBar) { 
  }

  ngOnInit(): void {
    this.initDataSource();
  }

  initDataSource() {
    let pageable = new PageableBuilder()
                          .page(0)
                          .size(5)
                          .build();

    this.getDevices(pageable);
  }

  onPageChange(event: PageEvent) {
    let pageable = new PageableBuilder()
                          .page(event.pageIndex)
                          .size(event.pageSize)
                          .build();

    this.getDevices(pageable);
  }

  highlight(row: any) {
    this.selection = row;
  }

  private getDevices(pageable: Pageable) {
    this.deviceService.getDevices(pageable).subscribe(
      data => {
        this.dataSource = data;
        this.selection = this.dataSource.content[0];
      }
    )
  }

  onCreate() {
    let dialogRef = this.dialog.open(DeviceAddFormComponent, {
      disableClose: true,
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(
      change => {
        if(change)
          this.initDataSource();
      }
    );
  }

  onEdit(rowIndex: number, device: DeviceDetails) {
    let dialogRef = this.dialog.open(DeviceAddFormComponent, {
      disableClose: true,
      width: '600px',
      data: device
    });

    dialogRef.afterClosed().subscribe(
      data => {
        if(data) {
          this.dataSource.content[rowIndex] = {
            id: device.id,
            description: data.description,
            address: data.address,
            maxEnergyConsumption: data.maxEnergyConsumption,
            avgEnergyConsumption: data.avgEnergyConsumption,
            assigned: device.assigned
          }
          this.table.renderRows();
        }
      }
    );
  }

  onDelete(rowIndex: number, id: string) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        message: "Are you sure you want to delete this device?"
      }
    });

    let result: boolean;
    
    dialogRef.afterClosed().subscribe(dialogResult => {
        result = dialogResult;
        if(result) this.deleteDevice(rowIndex, id);
    });
  }

  deleteDevice(rowIndex: number, id: string) {
    this.deviceService.deleteDevice(id).subscribe(
      () => {
        this.openSnackBar("Device deleted successfully", "X", 7000);
        this.dataSource.content.splice(rowIndex, 1);
        this.dataSource.totalElements -= 1;
        this.table.renderRows();

        this.selection = undefined;

        if(rowIndex > 0) {
          this.selection = this.dataSource.content[rowIndex - 1];
        } else {
          if(rowIndex == 0) {
            if(this.dataSource.content.length > 0)
              this.selection = this.dataSource.content[0];
          }
        }

        if(this.dataSource.totalElements % this.paginator.pageSize === 0)
          this.paginator.previousPage();
      }
    );
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {duration: duration});
  }

}
