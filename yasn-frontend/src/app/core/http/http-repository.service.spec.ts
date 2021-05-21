import {TestBed} from '@angular/core/testing';

import {HttpRepositoryService} from './http-repository.service';

describe('HttpRepositoryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HttpRepositoryService = TestBed.get(HttpRepositoryService);
    expect(service).toBeTruthy();
  });
});
