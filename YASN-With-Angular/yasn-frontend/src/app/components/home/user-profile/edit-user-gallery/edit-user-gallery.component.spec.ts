import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {EditUserGalleryComponent} from './edit-user-gallery.component';

describe('EditUserGalleryComponent', () => {
  let component: EditUserGalleryComponent;
  let fixture: ComponentFixture<EditUserGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditUserGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditUserGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
