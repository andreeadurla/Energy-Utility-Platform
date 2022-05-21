import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DeviceDetails } from 'src/app/models/device-details';
import { DeviceMoreDetails } from 'src/app/models/device-more-details';
import { UserDetails } from 'src/app/models/user-details';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { DeviceService } from 'src/app/services/device/device.service';

@Component({
  selector: 'app-client-devices',
  templateUrl: './client-devices.component.html',
  styleUrls: ['./client-devices.component.scss']
})
export class ClientDevicesComponent implements OnInit {

  authUser!: UserDetails;
  devices!: DeviceMoreDetails[];

  constructor(private deviceService: DeviceService,
        private authService: AuthenticationService,
        private route: ActivatedRoute,
        private router: Router) { 
  }

  ngOnInit(): void {
    this.authUser = this.authService.user;
    this.getDevices();
  }

  private getDevices() {
    const clientId: string = this.authUser.clientId!;

    this.deviceService.getDevicesWithMoreDetailsByClientId(clientId).subscribe(
      data => {
        this.devices = data;
      }
    )
  }

  viewStatistics(device: DeviceMoreDetails) {
    this.router.navigate(['energy-statistics'], { 
            queryParams: {
              id: device.sensorDetails.id,
              date: new Date()
            }, 
            relativeTo: this.route
    });
  }

}
