import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Client } from 'src/app/models/client';
import { ClientDetails } from 'src/app/models/client-details';
import { ClientService } from 'src/app/services/client/client.service';
import { AddressValidator } from 'src/app/validators/address-validator';
import { NameValidator } from 'src/app/validators/name-validator';

@Component({
  selector: 'app-client-edit-form',
  templateUrl: './client-edit-form.component.html',
  styleUrls: ['./client-edit-form.component.scss']
})
export class ClientEditFormComponent implements OnInit {

  detailsFormGroup!: FormGroup;
  
  constructor(private dialogRef: MatDialogRef<ClientEditFormComponent>,
            @Inject(MAT_DIALOG_DATA) private data: ClientDetails,
            private formBuilder: FormBuilder,
            private snackBar: MatSnackBar,
            private clientService: ClientService) { 
  }

  ngOnInit(): void {
    this.createDetailsFormGroup();
  }

  createDetailsFormGroup() {
    this.detailsFormGroup = this.formBuilder.group({
      name: [this.data.name, [
        Validators.required,
        NameValidator.valid()
      ]],
      birthDate: [this.data.birthDate, Validators.required],
      address: [this.data.address, [
        Validators.required,
        AddressValidator.valid()
      ]]
    });
  }

  closeDialog(client?: Client) {
    this.dialogRef.close(client);
  }

  onSubmit() {
    if(!this.detailsFormGroup.valid)
      return ;

    let id: string = this.data.id;

    let client: Client = {
      name: this.name?.value,
      birthDate: this.birthDate?.value,
      address: this.address?.value
    }

    this.updateClient(id, client);
  }

  updateClient(id: string, client: Client) {
    this.clientService.updateClient(id, client).subscribe(
      () => {
        this.closeDialog(client);
        this.openSnackBar("Client details updated successfully", "X", 7000);
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

  get maxDate() { return new Date(); }

  get minDate() { 
    let date = new Date();
    date.setFullYear(date.getFullYear() - 100);
    return date;
  }

  get name() { return this.detailsFormGroup.get('name'); }

  get birthDate() { return this.detailsFormGroup.get('birthDate'); }

  get address() { return this.detailsFormGroup.get('address'); }

}
