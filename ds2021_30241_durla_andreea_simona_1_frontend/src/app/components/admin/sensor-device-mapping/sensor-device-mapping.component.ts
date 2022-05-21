import { transferArrayItem } from '@angular/cdk/drag-drop';
import { Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { MatExpansionPanel } from '@angular/material/expansion';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';
import { SensorDetails } from 'src/app/models/sensor-details';
import { SensorService } from 'src/app/services/sensor/sensor.service';

@Component({
  selector: 'app-sensor-device-mapping',
  templateUrl: './sensor-device-mapping.component.html',
  styleUrls: ['./sensor-device-mapping.component.scss']
})
export class SensorDeviceMappingComponent implements OnInit, OnChanges {

  @ViewChild('table') table!: MatTable<SensorDetails[]>;
  @ViewChild('matExpansionPanel1') matExpansionPanel1!: MatExpansionPanel;
  @ViewChild('matExpansionPanel2') matExpansionPanel2!: MatExpansionPanel;

  @Input() deviceId!: string;

  deviceSensor!: SensorDetails[];
  unassignedSensors!: SensorDetails[];

  newSensor?: string;
  removedSensor?: string;

  displayedColumns: string[] = ['actions', 'id', 'description', 'maxValue'];

  constructor(private sensorService: SensorService,
        private snackBar: MatSnackBar) { 
  }

  ngOnInit(): void {
    this.getDeviceSensor(this.deviceId);
    this.getUanssignedSensors();
    this.newSensor = undefined;
    this.removedSensor = undefined;
  }

  ngOnChanges(): void {
    this.getDeviceSensor(this.deviceId);
    this.getUanssignedSensors();
    this.newSensor = undefined;
    this.removedSensor = undefined;
  }

  getDeviceSensor(deviceId: string) {
    this.sensorService.getSensorByDeviceId(deviceId).subscribe(
      data => {
        this.deviceSensor = [data];
      },
      (error) => {
        if(error.status === 404)
          this.deviceSensor = [];
      }
    )
  }

  getUanssignedSensors() {
    this.sensorService.getUnassignedSensors().subscribe(
      data => {
        this.unassignedSensors = data;
      }
    )
  }

  onSubmit() {
    this.sensorService
        .assignDeviceToSensor(this.deviceId, this.newSensor, this.removedSensor)
        .subscribe(() => {
          this.openSnackBar("Sensor assigned successfully", "X", 7000);
          this.newSensor = undefined;
          this.removedSensor = undefined;
        })
  }

  add(index: number, sensor: SensorDetails) {
    this.matExpansionPanel1.open();

    if(this.deviceSensor.length) {
      this.openSnackBar("This device has already a sensor", "X", 5000);
      return ;
    }

    if(this.removedSensor === sensor.id) {
      this.newSensor = sensor.id;
      this.removedSensor = undefined;
    }
    else 
      this.newSensor = sensor.id;

    transferArrayItem(this.unassignedSensors, this.deviceSensor, 
                index, this.deviceSensor.length);

    this.table?.renderRows();
  }

  remove() {
    if(!this.deviceSensor) 
      return ;

    this.matExpansionPanel2.open();

    if(!this.removedSensor)
      this.removedSensor = this.deviceSensor[0].id;

    transferArrayItem(this.deviceSensor, this.unassignedSensors, 
                0, this.unassignedSensors.length);

    this.table?.renderRows();
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {duration: duration});
  }

}
