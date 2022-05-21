import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { LimitsExceededNotification } from 'src/app/models/limits-exceeded-notification';

@Component({
  selector: 'app-limits-exceeded-notification',
  templateUrl: './limits-exceeded-notification.component.html',
  styleUrls: ['./limits-exceeded-notification.component.scss']
})
export class LimitsExceededNotificationComponent implements OnInit {

  constructor(public snackBarRef: MatSnackBarRef<LimitsExceededNotificationComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: LimitsExceededNotification) { }

  ngOnInit(): void {
  }

  dismissSnackBar() {
    this.snackBarRef.dismissWithAction();
  }

  get currentDate() {
    return new Date();
  }
}
