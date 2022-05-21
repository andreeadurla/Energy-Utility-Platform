import { StepperSelectionEvent } from '@angular/cdk/stepper';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Client } from 'src/app/models/client';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user/user.service';
import { AddressValidator } from 'src/app/validators/address-validator';
import { NameValidator } from 'src/app/validators/name-validator';
import { PasswordValidator } from 'src/app/validators/password-validator';
import { UsernameValidator } from 'src/app/validators/username-validator';

@Component({
  selector: 'app-client-add-form',
  templateUrl: './client-add-form.component.html',
  styleUrls: ['./client-add-form.component.scss']
})
export class ClientAddFormComponent implements OnInit {

  accountFormGroup!: FormGroup;
  detailsFormGroup!: FormGroup;

  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  invalidCredentials: boolean = false;

  constructor(private dialogRef: MatDialogRef<ClientAddFormComponent>,
            private formBuilder: FormBuilder,
            private userService: UserService,
            private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.createAccountFormGroup();
    this.createDetailsFormGroup();
  }

  createAccountFormGroup() {
    this.accountFormGroup = this.formBuilder.group({
      username: ['', [
          Validators.required,
          UsernameValidator.valid()
      ]],
      password: ['', [
        Validators.required,
        PasswordValidator.valid()
      ]],
      confirmPassword: ['', [
        Validators.required,
        PasswordValidator.valid()
      ]]
    }, {
      validator: PasswordValidator.match('password', 'confirmPassword')
    });
  }

  createDetailsFormGroup() {
    this.detailsFormGroup = this.formBuilder.group({
      name: ['', [
        Validators.required,
        NameValidator.valid()
      ]],
      birthDate: ['', Validators.required],
      address: ['', [
        Validators.required,
        AddressValidator.valid()
      ]]
    });
  }

  closeDialog(insert: boolean) {
    this.dialogRef.close(insert);
  }

  stepChanged(event: StepperSelectionEvent){
    event.previouslySelectedStep.interacted = false;
  }

  onSubmit() {
    if(!this.accountFormGroup.valid || !this.detailsFormGroup.valid)
      return ;

    let user: User = {
      username: this.username?.value,
      password: this.password?.value
    }

    let userDetails: Client = {
      name: this.name?.value,
      birthDate: this.birthDate?.value,
      address: this.address?.value
    }

    this.insertUser(user, userDetails)
  }

  insertUser(user: User, userDetails: Client) {
    this.userService.insertClient(user, userDetails).subscribe(
      () => {
        this.closeDialog(true);
        this.openSnackBar("Client created successfully", "X", 7000);
      },
      (error) => {
        if(error.status === 404)
          this.closeDialog(false);
      
        if(error.status === 409)
          this.invalidCredentials = true;
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

  get username() { return this.accountFormGroup.get('username'); }

  get password() { return this.accountFormGroup.get('password'); }

  get confirmPassword() { return this.accountFormGroup.get('confirmPassword'); }

  get name() { return this.detailsFormGroup.get('name'); }

  get birthDate() { return this.detailsFormGroup.get('birthDate'); }

  get address() { return this.detailsFormGroup.get('address'); }

}
