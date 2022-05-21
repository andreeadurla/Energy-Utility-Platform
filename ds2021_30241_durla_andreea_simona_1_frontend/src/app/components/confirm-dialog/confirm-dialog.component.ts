import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  readonly message: string = "Are you sure?";
  readonly confirmButtonText: string = "Yes";
  readonly cancelButtonText: string = "No";

  constructor(private dialogRef: MatDialogRef<ConfirmDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogModel) { 
  }

  ngOnInit(): void {
  }

  onConfirm() {
    this.dialogRef.close(true);
  }

  onDismiss() {
    this.dialogRef.close(false);
  }

}

export interface ConfirmDialogModel {
  title?: string;
  message: string;
  confirmButtonText: string;
  cancelButtonText: string;
}
