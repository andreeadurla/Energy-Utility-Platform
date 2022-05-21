import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin/admin.component';
import { ClientListComponent } from './components/admin/client/client-list/client-list.component';
import { DeviceListComponent } from './components/admin/device/device-list/device-list.component';
import { HomeComponent } from './components/admin/home/home.component';
import { SensorListComponent } from './components/admin/sensor/sensor-list/sensor-list.component';
import { ClientComponent } from './components/client/client/client.component';
import { LoginComponent } from './components/login/login.component';
import { Page403Component } from './components/error-pages/page403/page403.component';
import { Page404Component } from './components/error-pages/page404/page404.component';
import { AuthGuard } from './guards/auth-guard';
import { Role } from './models/role';
import { EnergyStatisticsComponent } from './components/client/energy-statistics/energy-statistics.component';
import { ClientDevicesComponent } from './components/client/client-devices/client-devices.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'admin', 
    component: AdminComponent,
    data: { roles: [Role.ADMIN] },
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: '/admin/clients', pathMatch: 'full'},
      { path: 'clients', component: ClientListComponent },
      { path: 'devices', component: DeviceListComponent },
      { path: 'sensors', component: SensorListComponent }
    ],
  },
  { path: 'client',
    component: ClientComponent,
    data: { roles: [Role.CLIENT] },
    canActivate: [AuthGuard],
    children: [
      { path: '', component: ClientDevicesComponent },
      { path: 'energy-statistics', component: EnergyStatisticsComponent }
    ]
  },
  { path: '403error', component: Page403Component },
  { path: '404error', component: Page404Component },
  { path: '**', component: Page404Component },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
