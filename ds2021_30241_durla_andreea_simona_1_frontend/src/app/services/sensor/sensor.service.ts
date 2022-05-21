import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Page } from 'src/app/commons/page';
import { Pageable } from 'src/app/commons/pageable';
import { Sensor } from 'src/app/models/sensor';
import { SensorDetails } from 'src/app/models/sensor-details';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient) { 
  }

  getSensors(pageable: Pageable) {
    let params = new HttpParams();

    params = params.append('page', pageable.page.toString());
    params = params.append('size', pageable.size.toString());

    let url = `${this.baseUrl}/sensors`;
    return this.httpClient.get<Page<SensorDetails>>(url, {params});
  }

  getSensorByDeviceId(deviceId: string) {
    let url = `${this.baseUrl}/sensors/device/${deviceId}`;

    return this.httpClient.get<SensorDetails>(url);
  }

  getUnassignedSensors() {
    let url = `${this.baseUrl}/sensors/unassigned`;

    return this.httpClient.get<SensorDetails[]>(url);
  }

  insertSensor(sensor: Sensor) {
    let url = `${this.baseUrl}/sensors`;

    return this.httpClient.post(url, sensor);
  }

  updateSensor(id: string, sensor: Sensor) {
    let url = `${this.baseUrl}/sensors/${id}`;

    return this.httpClient.put(url, sensor);
  }

  deleteSensor(id: string) {
    let url = `${this.baseUrl}/sensors/${id}`;

    return this.httpClient.delete(url);
  }

  assignDeviceToSensor(deviceId: string, newSensorId?: string, removedSensorId?: string) {
    let url = `${this.baseUrl}/sensors/device-assigning`;

    let body = {
      deviceId: deviceId,
      newSensorId: newSensorId,
      removedSensorId: removedSensorId
    };

    return this.httpClient.patch(url, body);
  }
}
