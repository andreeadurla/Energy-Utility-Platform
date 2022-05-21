import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LimitsExceededNotification } from 'src/app/models/limits-exceeded-notification';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { LimitsExceededNotificationComponent } from '../limits-exceeded-notification/limits-exceeded-notification.component';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss']
})
export class ClientComponent implements OnInit {

  selected: number = 0;
  notifications: LimitsExceededNotification[] = [];
  unreadNotifications: number = 0;

  constructor(private authService: AuthenticationService,
    private notificationService: NotificationService,
    private snackBar: MatSnackBar,
    private changeDetectorRef: ChangeDetectorRef) { 
  }

  ngOnInit(): void {
    this.notificationService.connect();

    this.notificationService.notifications$.subscribe(
      (data) => { 
        let notification = data as LimitsExceededNotification;
        
        let snackBarRef = this.openSnackBar(notification, 7000);
        snackBarRef.afterDismissed().subscribe(info => {
          if (info.dismissedByAction === false) {
            this.unreadNotifications++;
            notification.read = false;
            this.notifications.push(notification);
            this.changeDetectorRef.detectChanges();
          } else {
            notification.read = true;
            this.notifications.push(notification);
          }
        });
      }
    );
  }

  activate(n: number) {
    this.selected = n;
  }

  get username() {
    const user = this.authService.user;
    return user?.username;
  }

  logout() {
    this.notificationService.disconnect();
    this.authService.logout();
  }

  get currentDate() {
    return new Date();
  }

  openSnackBar(notification: LimitsExceededNotification, duration: number) {
    return this.snackBar.openFromComponent(LimitsExceededNotificationComponent, {
        data: notification,
        duration: duration,
        verticalPosition: 'top',
        horizontalPosition: 'center',
        panelClass: ['notification-snackbar']
      });
  }

  readNotifications() {
    this.unreadNotifications = 0;
    this.notifications.forEach(n => n.read = true);
  }
}
