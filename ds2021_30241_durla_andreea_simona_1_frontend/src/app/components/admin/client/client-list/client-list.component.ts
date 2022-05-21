import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Page } from 'src/app/commons/page';
import { Pageable, PageableBuilder } from 'src/app/commons/pageable';
import { Client } from 'src/app/models/client';
import { ClientDetails } from 'src/app/models/client-details';
import { ClientService } from 'src/app/services/client/client.service';
import { ClientEditFormComponent } from '../client-edit-form/client-edit-form.component';
import { ClientAddFormComponent } from '../client-add-form/client-add-form.component';
import { ConfirmDialogComponent } from '../../../confirm-dialog/confirm-dialog.component';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.scss']
})
export class ClientListComponent implements OnInit {

  @ViewChild('table') table!: MatTable<ClientDetails[]>;
  @ViewChild('paginator') paginator!: MatPaginator;
  
  displayedColumns: string[] = ['position', 'username', 'name', 'birthDate', 'address', 'actions'];
  dataSource!: Page<ClientDetails>;
  selection: any;

  constructor(private clientService: ClientService,
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

    this.getClients(pageable);
  }

  onPageChange(event: PageEvent) {
    let pageable = new PageableBuilder()
                          .page(event.pageIndex)
                          .size(event.pageSize)
                          .build();

    this.getClients(pageable);
  }

  highlight(row: any) {
    this.selection = row;
  }

  private getClients(pageable: Pageable) {
    this.clientService.getClients(pageable).subscribe(
      (data) => {
        this.dataSource = data;
        this.selection = this.dataSource.content[0];
      }
    )
  }

  onCreate() {
    let dialogRef = this.dialog.open(ClientAddFormComponent, {
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

  onEdit(rowIndex: number, client: ClientDetails) {
    let dialogRef = this.dialog.open(ClientEditFormComponent, {
      disableClose: true,
      width: '600px',
      data: client
    });

    dialogRef.afterClosed().subscribe(
      data => {
        if(data) {
          this.dataSource.content[rowIndex] = {
            id: client.id,
            username: client.username,
            name: data.name,
            birthDate: data.birthDate,
            address: data.address
          }
          this.table.renderRows();
        }
      }
    );
  }

  onDelete(rowIndex: number, username: string) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        message: "Are you sure you want to delete this client?"
      }
    });

    let result: boolean;
    
    dialogRef.afterClosed().subscribe(dialogResult => {
        result = dialogResult;
        if(result) this.deleteClient(rowIndex, username);
    });
  }

  deleteClient(rowIndex: number, username: string) {
    this.clientService.deleteClient(username).subscribe(
      () => {
        this.openSnackBar("Client deleted successfully", "X", 7000);
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

