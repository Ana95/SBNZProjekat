import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinecomponentComponent } from './medicinecomponent.component';

describe('MedicinecomponentComponent', () => {
  let component: MedicinecomponentComponent;
  let fixture: ComponentFixture<MedicinecomponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicinecomponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinecomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
