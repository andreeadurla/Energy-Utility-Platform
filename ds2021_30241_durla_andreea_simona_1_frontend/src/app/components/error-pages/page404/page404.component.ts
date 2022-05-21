import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { UserDetails } from 'src/app/models/user-details';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-page404',
  templateUrl: './page404.component.html',
  styleUrls: ['./page404.component.scss']
})
export class Page404Component implements OnInit {

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
