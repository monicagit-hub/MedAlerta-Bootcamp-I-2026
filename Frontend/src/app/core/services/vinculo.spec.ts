import { TestBed } from '@angular/core/testing';

import { Vinculo } from './vinculo';

describe('Vinculo', () => {
  let service: Vinculo;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Vinculo);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
