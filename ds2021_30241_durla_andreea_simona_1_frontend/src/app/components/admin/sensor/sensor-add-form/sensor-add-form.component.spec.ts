import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorAddFormComponent } from './sensor-add-form.component';

describe('SensorAddFormComponent', () => {
  let component: SensorAddFormComponent;
  let fixture: ComponentFixture<SensorAddFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SensorAddFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SensorAddFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
