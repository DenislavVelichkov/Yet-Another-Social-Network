import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UserProfileBodyComponent} from './user-profile-body.component';

describe('UserProfileBodyComponent', () => {
  let component: UserProfileBodyComponent;
  let fixture: ComponentFixture<UserProfileBodyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UserProfileBodyComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserProfileBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
