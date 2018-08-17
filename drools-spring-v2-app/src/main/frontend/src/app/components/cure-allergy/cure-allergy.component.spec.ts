import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CureAllergyComponent } from './cure-allergy.component';

describe('TherapyComponent', () => {
  let component: CureAllergyComponent;
  let fixture: ComponentFixture<CureAllergyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CureAllergyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CureAllergyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
