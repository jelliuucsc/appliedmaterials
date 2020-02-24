import { TestBed } from '@angular/core/testing';

import { GithubRepoService } from './github-repo.service';

describe('GithubRepoService', () => {
  let service: GithubRepoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GithubRepoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
