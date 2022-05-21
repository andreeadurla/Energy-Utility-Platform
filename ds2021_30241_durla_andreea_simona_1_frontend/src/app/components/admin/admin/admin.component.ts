import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  selected: number = 0;

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  activate(n: number) {
    this.selected = n;
  }

  get username() {
    const user = this.authService.user;
    return user?.username;
  }

  logout() {
    this.authService.logout();
  }

}
