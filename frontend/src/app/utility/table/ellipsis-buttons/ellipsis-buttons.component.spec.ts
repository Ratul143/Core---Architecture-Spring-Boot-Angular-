import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EllipsisButtonsComponent } from './ellipsis-buttons.component';

describe('ActionButtonsComponent', () => {
  let component: EllipsisButtonsComponent;
  let fixture: ComponentFixture<EllipsisButtonsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EllipsisButtonsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EllipsisButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
