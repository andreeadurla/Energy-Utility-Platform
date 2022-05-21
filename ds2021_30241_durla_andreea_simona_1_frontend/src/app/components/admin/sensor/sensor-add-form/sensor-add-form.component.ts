import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Sensor } from 'src/app/models/sensor';
import { SensorDetails } from 'src/app/models/sensor-details';
import { SensorService } from 'src/app/services/sensor/sensor.service';

@Component({
  selector: 'app-sensor-add-form',
  templateUrl: './sensor-add-form.component.html',
  styleUrls: ['./sensor-add-form.component.scss']
})
export class SensorAddFormComponent implements OnInit {

  detailsFormGroup!: FormGroup;
  title!: String;

  constructor(private dialogRef: MatDialogRef<SensorAddFormComponent>,
        @Inject(MAT_DIALOG_DATA) private data: SensorDetails,    
        private formBuilder: FormBuilder,
        private snackBar: MatSnackBar,
        private sensorService: SensorService) { }

  ngOnInit(): void {
    this.createDetailsFormGroup();
    this.title = (this.data) ? "Edit sensor details": "Add sensor";
  }

  closeDialog(sensor?: Sensor) {
    this.dialogRef.close(sensor);
  }

  createDetailsFormGroup() {
    this.detailsFormGroup = this.formBuilder.group({
      description: [this.data?.description, 
        Validators.required
      ],
      maxValue: [this.data?.maxValue, [
        Validators.required,
        Validators.min(0),
        Validators.max(9999.99)
      ]]
    });
  }

  onSubmit() {
    if(!this.detailsFormGroup.valid)
      return ;

    let sensor: Sensor = {
      description: this.description?.value,
      maxValue: this.maxValue?.value
    }

    if(this.data) {
      let id: string = this.data.id;
      this.updateSensor(id, sensor);
    } else {
      this.insertSensor(sensor);
    }
  }

  insertSensor(sensor: Sensor) {
    this.sensorService.insertSensor(sensor).subscribe(
      () => {
        this.closeDialog(sensor);
        this.openSnackBar("Sensor created successfully", "X", 7000);
      },
      (error) => {
        if(error.status === 404)
          this.closeDialog(undefined);
      
        if(error.status === 401)
          this.closeDialog(undefined);
      }
    );
  }

  updateSensor(id: string, sensor: Sensor) {
    this.sensorService.updateSensor(id, sensor).subscribe(
      () => {
        this.closeDialog(sensor);
        this.openSnackBar("Sensor details updated successfully", "X", 7000);
      },
      (error) => {
        if(error.status === 404)
          this.closeDialog(undefined);
      
        if(error.status === 401)
          this.closeDialog(undefined);
      }
    );
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {duration: duration});
  }

  get description() { return this.detailsFormGroup.get('description'); }

  get maxValue() { return this.detailsFormGroup.get('maxValue'); }
}
