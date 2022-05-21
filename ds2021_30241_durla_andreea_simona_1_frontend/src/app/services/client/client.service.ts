import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Page } from 'src/app/commons/page';
import { Pageable } from 'src/app/commons/pageable';
import { Client } from 'src/app/models/client';
import { ClientDetails } from 'src/app/models/client-details';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient) { 
  }

  getClients(pageable: Pageable) {
    let params = new HttpParams();

    params = params.append('page', pageable.page.toString());
    params = params.append('size', pageable.size.toString());

    let url = `${this.baseUrl}/clients`;
    return this.httpClient.get<Page<ClientDetails>>(url, {params});
  }

  updateClient(id: string, client: Client) {
    let url = `${this.baseUrl}/clients/${id}`;

    return this.httpClient.put(url, client);
  }

  deleteClient(username: string) {
    let url = `${this.baseUrl}/users/${username}`;

    return this.httpClient.delete(url);
  }

}
