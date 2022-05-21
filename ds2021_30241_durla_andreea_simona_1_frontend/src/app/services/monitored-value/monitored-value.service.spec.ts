import { TestBed } from '@angular/core/testing';

import { MonitoredValueService } from './monitored-value.service';

describe('MonitoredValueService', () => {
  let service: MonitoredValueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonitoredValueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
