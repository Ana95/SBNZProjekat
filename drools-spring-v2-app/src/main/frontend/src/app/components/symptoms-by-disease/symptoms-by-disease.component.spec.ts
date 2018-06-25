import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SymptomsByDiseaseComponent } from './symptoms-by-disease.component';

describe('SymptomsByDiseaseComponent', () => {
  let component: SymptomsByDiseaseComponent;
  let fixture: ComponentFixture<SymptomsByDiseaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SymptomsByDiseaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SymptomsByDiseaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
