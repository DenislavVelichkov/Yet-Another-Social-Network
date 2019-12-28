import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnAuthorizedNavbarComponent } from './un-authorized-navbar.component';

describe('UnAuthorizedNavbarComponent', () => {
  let component: UnAuthorizedNavbarComponent;
  let fixture: ComponentFixture<UnAuthorizedNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnAuthorizedNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnAuthorizedNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
