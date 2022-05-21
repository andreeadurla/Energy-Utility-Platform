import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { UsernameValidator } from 'src/app/validators/username-validator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginFormGroup!: FormGroup;

  hidePassword: boolean = true;

  invalidCredentials: boolean = false;

  constructor(private router: Router,
          private formBuilder: FormBuilder,
          private authService: AuthenticationService,
          private notificationService: NotificationService) { 
  }

  ngOnInit(): void {
    this.authService.logout();
    this.notificationService.disconnect();
    this.createLoginFormGroup();
  }

  createLoginFormGroup() {
    this.loginFormGroup = this.formBuilder.group({
      username: ['', [
        Validators.required,
        UsernameValidator.valid()
      ]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if(!this.loginFormGroup.valid)
      return ;

    this.authService.login(this.username?.value!, this.password?.value!)
            .subscribe(
              (data) => {
                if(data.roles.indexOf(Role.ADMIN) !== -1)
                  this.router.navigate(['/admin']);

                if(data.roles.indexOf(Role.CLIENT) !== -1)
                  this.router.navigate(['/client']);
              },
              (error) => {
                if(error.status === 404)
                  this.invalidCredentials = true;

                if(error.status === 401)
                  this.invalidCredentials = true;
              }
            );
  }

  get username() { return this.loginFormGroup.get('username'); }

  get password() { return this.loginFormGroup.get('password'); }

}
