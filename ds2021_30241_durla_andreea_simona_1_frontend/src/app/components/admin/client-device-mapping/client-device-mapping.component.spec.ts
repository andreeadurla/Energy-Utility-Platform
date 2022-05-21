import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientDeviceMappingComponent } from './client-device-mapping.component';

describe('ClientDeviceMappingComponent', () => {
  let component: ClientDeviceMappingComponent;
  let fixture: ComponentFixture<ClientDeviceMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientDeviceMappingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientDeviceMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
