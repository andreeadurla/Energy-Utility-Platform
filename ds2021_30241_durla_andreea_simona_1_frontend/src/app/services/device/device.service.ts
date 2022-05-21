import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Page } from 'src/app/commons/page';
import { Pageable } from 'src/app/commons/pageable';
import { Device } from 'src/app/models/device';
import { DeviceDetails } from 'src/app/models/device-details';
import { DeviceMoreDetails } from 'src/app/models/device-more-details';
import { DeviceSomeDetails } from 'src/app/models/device-some-details';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient) { 
  }

  getDevices(pageable: Pageable) {
    let params = new HttpParams();

    params = params.append('page', pageable.page.toString());
    params = params.append('size', pageable.size.toString());

    let url = `${this.baseUrl}/devices`;
    return this.httpClient.get<Page<DeviceDetails>>(url, {params});
  }

  getDevicesByClientId(clientId: string) {
    let url = `${this.baseUrl}/devices/client/${clientId}`;

    return this.httpClient.get<DeviceDetails[]>(url);
  }

  getDevicesWithMoreDetailsByClientId(clientId: string) {
    let url = `${this.baseUrl}/devices/client/${clientId}/more-details`;

    return this.httpClient.get<DeviceMoreDetails[]>(url);
  }

  getDevicesWithSomeDetailsByClientId(clientId: string) {
    let url = `${this.baseUrl}/devices/client/${clientId}/some-details`;

    return this.httpClient.get<DeviceSomeDetails[]>(url);
  }

  getUnassignedDevices() {
    let url = `${this.baseUrl}/devices/unassigned`;

    return this.httpClient.get<DeviceDetails[]>(url);
  }

  insertDevice(device: Device) {
    let url = `${this.baseUrl}/devices`;

    return this.httpClient.post(url, device);
  }

  updateDevice(id: string, device: Device) {
    let url = `${this.baseUrl}/devices/${id}`;

    return this.httpClient.put(url, device);
  }

  deleteDevice(id: string) {
    let url = `${this.baseUrl}/devices/${id}`;

    return this.httpClient.delete(url);
  }

  assignClientToDevices(clientId: string, newDevicesId: string[], removedDevicesId: string[]) {
    let url = `${this.baseUrl}/devices/client-assigning`;

    let body = {
      clientId: clientId,
      newDevicesId: newDevicesId,
      removedDevicesId: removedDevicesId
    };

    return this.httpClient.patch(url, body);
  }
}
