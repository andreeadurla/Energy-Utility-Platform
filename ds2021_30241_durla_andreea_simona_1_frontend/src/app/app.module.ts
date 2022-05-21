import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { environment } from 'src/environments/environment';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDividerModule } from '@angular/material/divider';
import { MatDialogModule } from '@angular/material/dialog';
import { ClientAddFormComponent } from './components/admin/client/client-add-form/client-add-form.component';
import { MatStepperModule } from '@angular/material/stepper';
import { MatFormFieldModule  } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ClientEditFormComponent } from './components/admin/client/client-edit-form/client-edit-form.component';
import { MatCardModule } from '@angular/material/card';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { ClientListComponent } from './components/admin/client/client-list/client-list.component';
import { DeviceListComponent } from './components/admin/device/device-list/device-list.component';
import { DeviceAddFormComponent } from './components/admin/device/device-add-form/device-add-form.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ClientDeviceMappingComponent } from './components/admin/client-device-mapping/client-device-mapping.component';
import { LineDividerComponent } from './components/line-divider/line-divider.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { JwtInterceptor } from './interceptors/jwt-interceptor';
import { ErrorInterceptor } from './interceptors/error-interceptor';
import { SensorListComponent } from './components/admin/sensor/sensor-list/sensor-list.component';
import { SensorAddFormComponent } from './components/admin/sensor/sensor-add-form/sensor-add-form.component';
import { SensorDeviceMappingComponent } from './components/admin/sensor-device-mapping/sensor-device-mapping.component';
import { HomeComponent } from './components/admin/home/home.component';
import { ClientComponent } from './components/client/client/client.component';
import { Page403Component } from './components/error-pages/page403/page403.component';
import { Page404Component } from './components/error-pages/page404/page404.component';
import { ClientDevicesComponent } from './components/client/client-devices/client-devices.component';
import { EnergyStatisticsComponent } from './components/client/energy-statistics/energy-statistics.component';
import { ChartModule } from 'angular-highcharts';
import { MatSelectModule } from '@angular/material/select';
import { MatBadgeModule } from '@angular/material/badge';
import { MatMenuModule } from '@angular/material/menu';
import { LimitsExceededNotificationComponent } from './components/client/limits-exceeded-notification/limits-exceeded-notification.component';

@NgModule({
  declarations: [
    AppComponent,
    ClientAddFormComponent,
    ClientEditFormComponent,
    ConfirmDialogComponent,
    ClientListComponent,
    DeviceListComponent,
    DeviceAddFormComponent,
    SensorListComponent,
    SensorAddFormComponent,
    ClientDeviceMappingComponent,
    LineDividerComponent,
    SensorDeviceMappingComponent,
    LoginComponent,
    AdminComponent,
    HomeComponent,
    ClientComponent,
    Page403Component,
    Page404Component,
    ClientDevicesComponent,
    EnergyStatisticsComponent,
    LimitsExceededNotificationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatDividerModule,
    MatDialogModule,
    MatStepperModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatCardModule,
    DragDropModule,
    MatExpansionModule,
    ChartModule,
    MatSelectModule,
    MatBadgeModule,
    MatMenuModule
  ],
  providers: [
    //{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' },
    { provide: "BASE_API_URL", useValue: environment.apiUrl },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
