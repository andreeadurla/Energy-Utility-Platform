import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LimitsExceededNotificationComponent } from './limits-exceeded-notification.component';

describe('LimitsExceededNotificationComponent', () => {
  let component: LimitsExceededNotificationComponent;
  let fixture: ComponentFixture<LimitsExceededNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LimitsExceededNotificationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LimitsExceededNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
