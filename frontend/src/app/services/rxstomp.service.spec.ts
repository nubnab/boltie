import { TestBed } from '@angular/core/testing';

import { RxstompService } from './rxstomp.service';

describe('RxstompService', () => {
  let service: RxstompService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RxstompService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
