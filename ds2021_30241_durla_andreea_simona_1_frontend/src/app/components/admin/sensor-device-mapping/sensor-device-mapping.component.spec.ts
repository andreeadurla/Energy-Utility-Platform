import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorDeviceMappingComponent } from './sensor-device-mapping.component';

describe('SensorDeviceMappingComponent', () => {
  let component: SensorDeviceMappingComponent;
  let fixture: ComponentFixture<SensorDeviceMappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SensorDeviceMappingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SensorDeviceMappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
