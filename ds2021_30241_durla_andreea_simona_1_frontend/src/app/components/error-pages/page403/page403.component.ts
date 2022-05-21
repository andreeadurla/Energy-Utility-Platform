import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { UserDetails } from 'src/app/models/user-details';

@Component({
  selector: 'app-page403',
  templateUrl: './page403.component.html',
  styleUrls: ['./page403.component.scss']
})
export class Page403Component implements OnInit {

  constructor(private authService: AuthenticationService,
        private router: Router) { 
  }

  ngOnInit(): void {
  }

  goHome() {
    const user: UserDetails = this.authService.user;

    if(Object.keys(user).length !== 0) {
      const roles = user.roles;
      
      if(roles.indexOf(Role.ADMIN) !== -1) {
        this.router.navigate(['/admin']);
        return ;
      }
      
      if(roles.indexOf(Role.CLIENT) !== -1) {
        this.router.navigate(['/client']);
        return ;
      }
    }

    this.router.navigate(['/']);
  }

}
