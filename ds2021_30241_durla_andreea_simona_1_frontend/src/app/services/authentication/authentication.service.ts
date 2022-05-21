import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserDetails } from 'src/app/models/user-details';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NotificationService } from '../notification/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService implements OnDestroy {

  private userSubject!: BehaviorSubject<any>;

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient,
    private router: Router) 
  { 
    this.userSubject = new BehaviorSubject<UserDetails>(JSON.parse(localStorage.getItem('user') || '{}'));
  }

  ngOnDestroy(): void {
    this.logout();
  }

  public get user() {
    return this.userSubject.value;
  }

  login(username: string, password: string) {

    let url = `${this.baseUrl}/auth/login`;
    let body = {
      username: username,
      password: password
    };

    return this.httpClient.post<UserDetails>(url, body)
              .pipe(map(user => {
                  localStorage.setItem('user', JSON.stringify(user));
                  this.userSubject.next(user);
                  return user;
              }));
  }

  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }
}
