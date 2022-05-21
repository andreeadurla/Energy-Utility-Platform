import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { MonitoredValuesStatistics } from 'src/app/models/monitored-values-statistics';
import { DatePipe } from '@angular/common';
import { MonitoredValueDetails } from 'src/app/models/monitored-value-details';
import { Pageable } from 'src/app/commons/pageable';
import { Page } from 'src/app/commons/page';
import { map } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class MonitoredValueService {

  constructor(
    @Inject('BASE_API_URL') private baseUrl: string,
    private httpClient: HttpClient) { 
  }

  getMonitoredValuesForStatistics(clientId: string, sensorId: string, date: Date) {
    let url = `${this.baseUrl}/monitored-values/statistics`;

    const datepipe: DatePipe = new DatePipe('en-US')
    let formattedDate = datepipe.transform(date, 'yyyy-MM-dd')

    let params = new HttpParams();
    if(sensorId !== 'all') params = params.append('sensor-id', sensorId);

    params = params.append('client-id', clientId);
    params = params.append('date', formattedDate!);
    params = params.append('timeZone', Intl.DateTimeFormat().resolvedOptions().timeZone);
    
    return this.httpClient.get<MonitoredValuesStatistics>(url, {params: params});
  }

  getMonitoredValues(clientId: string, sensorId: string, date: Date, pageable: Pageable) {
    let url = `${this.baseUrl}/monitored-values`;

    const datepipe: DatePipe = new DatePipe('en-US')
    let formattedDate = datepipe.transform(date, 'yyyy-MM-dd')

    let params = new HttpParams();
    if(sensorId !== 'all') params = params.append('sensor-id', sensorId);

    params = params.append('client-id', clientId);
    params = params.append('date', formattedDate!);
    params = params.append('timeZone', Intl.DateTimeFormat().resolvedOptions().timeZone);

    params = params.append('page', pageable.page.toString());
    params = params.append('size', pageable.size.toString());
  
    return this.httpClient.get<Page<MonitoredValueDetails>>(url, {params: params})
  }

}
