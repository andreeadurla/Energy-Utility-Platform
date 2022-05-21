import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Page } from 'src/app/commons/page';
import { Pageable, PageableBuilder } from 'src/app/commons/pageable';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';
import { SensorDetails } from 'src/app/models/sensor-details';
import { SensorService } from 'src/app/services/sensor/sensor.service';
import { SensorAddFormComponent } from '../sensor-add-form/sensor-add-form.component';

@Component({
  selector: 'app-sensor-list',
  templateUrl: './sensor-list.component.html',
  styleUrls: ['./sensor-list.component.scss']
})
export class SensorListComponent implements OnInit {

  @ViewChild('table') table!: MatTable<SensorDetails[]>;
  @ViewChild('paginator') paginator!: MatPaginator;
  
  displayedColumns: string[] = ['assigned', 'id', 'description', 'maxValue', 'actions'];
  dataSource!: Page<SensorDetails>;

  constructor(private sensorService: SensorService,
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

    this.getSensors(pageable);
  }

  onPageChange(event: PageEvent) {
    let pageable = new PageableBuilder()
                          .page(event.pageIndex)
                          .size(event.pageSize)
                          .build();

    this.getSensors(pageable);
  }

  private getSensors(pageable: Pageable) {
    this.sensorService.getSensors(pageable).subscribe(
      data => {
        this.dataSource = data;
      }
    )
  }

  onCreate() {
    let dialogRef = this.dialog.open(SensorAddFormComponent, {
      disableClose: true,
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(
      data => {
        if(data)
          this.initDataSource();
      }
    );
  }

  onEdit(rowIndex: number, sensor: SensorDetails) {
    let dialogRef = this.dialog.open(SensorAddFormComponent, {
      disableClose: true,
      width: '600px',
      data: sensor
    });

    dialogRef.afterClosed().subscribe(
      data => {
        if(data) {
          this.dataSource.content[rowIndex] = {
            id: sensor.id,
            description: data.description,
            maxValue: data.maxValue,
            assigned: sensor.assigned
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
        message: "Are you sure you want to delete this sensor?"
      }
    });

    let result: boolean;
    
    dialogRef.afterClosed().subscribe(dialogResult => {
        result = dialogResult;
        if(result) this.deleteSensor(rowIndex, id);
    });
  }

  deleteSensor(rowIndex: number, id: string) {
    this.sensorService.deleteSensor(id).subscribe(
      () => {
        this.openSnackBar("Device deleted successfully", "X", 7000);

        this.dataSource.content.splice(rowIndex, 1);
        this.dataSource.totalElements -= 1;
        this.table.renderRows();

        if(this.dataSource.totalElements % this.paginator.pageSize === 0)
          this.paginator.previousPage();
      }
    );
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {duration: duration});
  }
}
