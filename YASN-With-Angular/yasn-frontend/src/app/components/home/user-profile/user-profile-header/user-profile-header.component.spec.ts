import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserProfileHeaderComponent} from './user-profile-header.component';

describe('UserProfileHeaderComponent', () => {
  let component: UserProfileHeaderComponent;
  let fixture: ComponentFixture<UserProfileHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserProfileHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
