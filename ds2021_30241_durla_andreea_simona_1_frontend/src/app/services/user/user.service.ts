import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Client } from 'src/app/models/client';
import { Role } from 'src/app/models/role';
import { User } from 'src/app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient) { 
  }

  insertClient(user: User, client: Client) {

    let url = `${this.baseUrl}/users`;
    let body = {
      username: user.username,
      password: user.password,
      clientDetailsDTO: client
    };

    return this.httpClient.post(url, body);
  }
}
