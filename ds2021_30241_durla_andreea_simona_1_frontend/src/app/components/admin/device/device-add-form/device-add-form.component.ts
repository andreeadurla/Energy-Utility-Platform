import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Device } from 'src/app/models/device';
import { DeviceDetails } from 'src/app/models/device-details';
import { DeviceService } from 'src/app/services/device/device.service';
import { AddressValidator } from 'src/app/validators/address-validator';

@Component({
  selector: 'app-device-add-form',
  templateUrl: './device-add-form.component.html',
  styleUrls: ['./device-add-form.component.scss']
})
export class DeviceAddFormComponent implements OnInit {

  detailsFormGroup!: FormGroup;
  title!: String;

  constructor(private dialogRef: MatDialogRef<DeviceAddFormComponent>,
        @Inject(MAT_DIALOG_DATA) private data: DeviceDetails,
        private formBuilder: FormBuilder,
        private snackBar: MatSnackBar,
        private deviceService: DeviceService) { }

  ngOnInit(): void {
    this.createDetailsFormGroup();
    this.title = (this.data) ? "Edit device details": "Add device";
  }

  closeDialog(device?: Device) {
    this.dialogRef.close(device);
  }

  createDetailsFormGroup() {
    this.detailsFormGroup = this.formBuilder.group({
      description: [this.data?.description, 
        Validators.required
      ],
      address: [this.data?.address, [
        Validators.required,
        AddressValidator.valid()
      ]],
      maxEnergyConsumption: [this.data?.maxEnergyConsumption, [
        Validators.required,
        Validators.min(0),
        Validators.max(9999.99)
      ]],
      avgEnergyConsumption: [this.data?.avgEnergyConsumption, [
        Validators.required,
        Validators.min(0),
        Validators.max(9999.99)
      ]]
    });
  }

  onSubmit() {
    if(!this.detailsFormGroup.valid)
      return ;

    let device: Device = {
      description: this.description?.value,
      address: this.address?.value,
      maxEnergyConsumption: this.maxEnergyConsumption?.value,
      avgEnergyConsumption: this.avgEnergyConsumption?.value
    }

    if(this.data) {
      let id: string = this.data.id;
      this.updateDevice(id, device);
    } else {
      this.insertDevice(device);
    }
  }

  insertDevice(device: Device) {
    this.deviceService.insertDevice(device).subscribe(
      () => {
        this.closeDialog(device);
        this.openSnackBar("Device created successfully", "X", 7000);
      },
      (error) => {
        if(error.status === 404)
          this.closeDialog(undefined);
      
        if(error.status === 401)
          this.closeDialog(undefined);
      }
    );
  }

  updateDevice(id: string, device: Device) {
    this.deviceService.updateDevice(id, device).subscribe(
      () => {
        this.closeDialog(device);
        this.openSnackBar("Device details updated successfully", "X", 7000);
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

  get address() { return this.detailsFormGroup.get('address'); }

  get maxEnergyConsumption() { return this.detailsFormGroup.get('maxEnergyConsumption'); }

  get avgEnergyConsumption() { return this.detailsFormGroup.get('avgEnergyConsumption'); }
}
