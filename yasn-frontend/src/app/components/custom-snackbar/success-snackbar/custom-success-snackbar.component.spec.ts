import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CustomSuccessSnackbarComponent} from './custom-success-snackbar.component';

describe('CustomSnackbarComponent', () => {
  let component: CustomSuccessSnackbarComponent;
  let fixture: ComponentFixture<CustomSuccessSnackbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomSuccessSnackbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomSuccessSnackbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
