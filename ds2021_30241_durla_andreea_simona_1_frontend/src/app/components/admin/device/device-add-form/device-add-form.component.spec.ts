import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceAddFormComponent } from './device-add-form.component';

describe('DeviceAddFormComponent', () => {
  let component: DeviceAddFormComponent;
  let fixture: ComponentFixture<DeviceAddFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceAddFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceAddFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
