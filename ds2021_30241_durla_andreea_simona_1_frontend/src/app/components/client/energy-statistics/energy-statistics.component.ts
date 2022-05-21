import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { Chart } from 'angular-highcharts';
import { Page } from 'src/app/commons/page';
import { Pageable, PageableBuilder } from 'src/app/commons/pageable';
import { DeviceSomeDetails } from 'src/app/models/device-some-details';
import { MonitoredValueDetails } from 'src/app/models/monitored-value-details';
import { MonitoredValuesStatistics } from 'src/app/models/monitored-values-statistics';
import { UserDetails } from 'src/app/models/user-details';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { DeviceService } from 'src/app/services/device/device.service';
import { MonitoredValueService } from 'src/app/services/monitored-value/monitored-value.service';

@Component({
  selector: 'app-energy-statistics',
  templateUrl: './energy-statistics.component.html',
  styleUrls: ['./energy-statistics.component.scss']
})
export class EnergyStatisticsComponent implements OnInit {

   @ViewChild('paginator') paginator!: MatPaginator;
   
   statisticsFormGroup!: FormGroup;
   authUser!: UserDetails;
   sensorId!: string;
   selectedDate!: Date;
   devices!: DeviceSomeDetails[];
   chart!: Chart;
   monitoredValuesStatistics!: MonitoredValuesStatistics;

   displayedColumns: string[] = ['sensorId', 'timestamp', 'energyConsumption'];
   dataSource!: Page<MonitoredValueDetails>;

   constructor(private formBuilder: FormBuilder,
            private route: ActivatedRoute,
            private authService: AuthenticationService,
            private deviceService: DeviceService,
            private monitoredValueService: MonitoredValueService) { 
   }

   ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
         this.sensorId = params.id;
         this.selectedDate = new Date(params.date);
         this.initData();
      });
   }

   initData() {
      this.authUser = this.authService.user;
      this.getMonitoredValuesForStatistics(this.sensorId, this.selectedDate);
      this.getDevices();
      this.createStatisticsFormGroup();
      this.initDataSource(this.sensorId, this.selectedDate);
   }

   createStatisticsFormGroup() {
      this.statisticsFormGroup = this.formBuilder.group({
         device: [this.sensorId],
         date: [this.selectedDate] 
      });
   }

   private getDevices() {
      const clientId: string = this.authUser.clientId!;

      this.deviceService.getDevicesWithSomeDetailsByClientId(clientId).subscribe(
         data => {
            this.devices = data;
         }
      )
   }

   selectionChange(event: any) {
      this.getMonitoredValuesForStatistics(event.value, this.date?.value);
      this.initDataSource(event.value, this.date?.value);

      this.updateRouteState(event.value, this.date?.value);
   }

   dateChange(event: MatDatepickerInputEvent<Date>) {
      this.getMonitoredValuesForStatistics(this.device?.value, event.value!);
      this.initDataSource(this.device?.value, event.value!);
      this.updateRouteState(this.device?.value, event.value!);
   }

   private updateRouteState(sensorId: string, date: Date) {

      let url = `/client/energy-statistics/?id=${sensorId}&date=${date}`
      window.history.replaceState({}, '', url);
   }

   private getMonitoredValuesForStatistics(sensorId: string, date: Date) {

      let clientId = this.authService.user.clientId;

      this.monitoredValueService.getMonitoredValuesForStatistics(clientId, sensorId, date).subscribe(
         data => {
            this.monitoredValuesStatistics = data;
            this.initChart();
         }
      )
   }

   initChart() {

      this.chart = new Chart({
         chart: {
            type: "line",
            width: 1000,
            height: 430
         },
         title: {
            text: "Energy consumption",
            align: "center",
            margin: 50
         },
         xAxis:{
            categories:["12AM", "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", 
                     "12PM", "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM"]
         },
         yAxis: {          
            title:{
               text:"Energy consumption kW",
               margin: 30
            } 
         },
         tooltip: {
            valueSuffix:" kW",
            valueDecimals: 2
         },
         series: [{
            showInLegend: false,
            type: 'line',
            data: this.monitoredValuesStatistics.statisticsOnHours,
            color: 'green'
         }]
      });
   }

   initDataSource(sensorId: string, date: Date) {
      let pageable = new PageableBuilder()
                           .page(0)
                           .size(5)
                           .build();

      this.getMonitoredValues(sensorId, date, pageable);    
   }

   onPageChange(event: PageEvent) {
      let pageable = new PageableBuilder()
                            .page(event.pageIndex)
                            .size(event.pageSize)
                            .build();
  
      this.getMonitoredValues(this.device?.value, this.date?.value, pageable);
   }

   private getMonitoredValues(sensorId: string, date: Date, pageable: Pageable) {
      let clientId = this.authService.user.clientId;

      this.monitoredValueService.getMonitoredValues(clientId, sensorId, date, pageable).subscribe(
        (data) => {
          this.dataSource = data;
        }
      )
   }

   get maxDate() { return new Date(); }

   get minDate() { 
      let date = new Date();
      date.setFullYear(date.getFullYear() - 5);
      return date;
   }

   get device() { return this.statisticsFormGroup.get('device'); }

   get date() { return this.statisticsFormGroup.get('date'); }
}

