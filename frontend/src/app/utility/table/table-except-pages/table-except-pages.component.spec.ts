import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TableExceptPagesComponent } from './table-except-pages.component';

describe('TableExceptPagesComponent', () => {
  let component: TableExceptPagesComponent;
  let fixture: ComponentFixture<TableExceptPagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableExceptPagesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableExceptPagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
