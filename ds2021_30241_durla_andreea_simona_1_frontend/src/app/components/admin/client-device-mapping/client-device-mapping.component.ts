import { transferArrayItem } from '@angular/cdk/drag-drop';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild } from '@angular/core';
import { MatExpansionPanel } from '@angular/material/expansion';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { DeviceDetails } from 'src/app/models/device-details';
import { DeviceService } from 'src/app/services/device/device.service';

@Component({
  selector: 'app-client-device-mapping',
  templateUrl: './client-device-mapping.component.html',
  styleUrls: ['./client-device-mapping.component.scss']
})
export class ClientDeviceMappingComponent implements OnInit, OnChanges {

  @ViewChild('table1') table1!: MatTable<DeviceDetails[]>;
  @ViewChild('table2') table2!: MatTable<DeviceDetails[]>;
  @ViewChild('matExpansionPanel1') matExpansionPanel1!: MatExpansionPanel;
  @ViewChild('matExpansionPanel2') matExpansionPanel2!: MatExpansionPanel;

  @Input() clientId!: string;

  clientDevices!: DeviceDetails[];
  unassignedDevices!: DeviceDetails[];

  newDevices!: Set<string>;
  removedDevices!: Set<string>;

  displayedColumns: string[] = ['actions', 'id', 'description', 'address', 
                                'maxEnergyConsumption', 'avgEnergyConsumption'];


  constructor(private deviceService: DeviceService,
        private snackBar: MatSnackBar) { 
  }

  ngOnInit(): void {
    this.getClientDevices(this.clientId);
    this.getUnassignedDevices();
    this.newDevices = new Set();
    this.removedDevices = new Set();
  }

  ngOnChanges(): void {
    this.getClientDevices(this.clientId);
    this.getUnassignedDevices();
    this.newDevices = new Set();
    this.removedDevices = new Set();
  }

  getClientDevices(clientId: string) {
    this.deviceService.getDevicesByClientId(clientId).subscribe(
      data => {
        this.clientDevices = data;
      }
    );
  }

  getUnassignedDevices() {
    this.deviceService.getUnassignedDevices().subscribe(
      data => {
        this.unassignedDevices = data;
      }
    );
  }

  onSubmit() {
    let newDevicesId: string[] = Array.from(this.newDevices.values());
    let removedDevicesId: string[] = Array.from(this.removedDevices.values());

    this.deviceService
        .assignClientToDevices(this.clientId, newDevicesId, removedDevicesId)
        .subscribe(() => {
            this.openSnackBar("Devices assigned successfully", "X", 7000);
            this.newDevices = new Set();
            this.removedDevices = new Set();
        })
  }

  add(index: number, device: DeviceDetails) {
    this.matExpansionPanel1.open();

    this.newDevices.add(device.id);
    this.removedDevices.delete(device.id);

    transferArrayItem(this.unassignedDevices, this.clientDevices, 
                      index, this.clientDevices.length);

    this.table1?.renderRows();
    this.table2?.renderRows();
  }

  remove(index: number, device: DeviceDetails) {
    this.matExpansionPanel2.open();
    
    this.removedDevices.add(device.id);
    this.newDevices.delete(device.id);

    transferArrayItem(this.clientDevices, this.unassignedDevices, 
                    index, this.unassignedDevices.length);
    
    this.table1?.renderRows();
    this.table2?.renderRows();
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {duration: duration});
  }
}
