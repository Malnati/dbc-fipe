import { TestBed } from '@angular/core/testing';

import { PriceVariationService } from './price-variation.service';

describe('PriceVariationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PriceVariationService = TestBed.get(PriceVariationService);
    expect(service).toBeTruthy();
  });
});
