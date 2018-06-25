import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiseasesBySimptomsComponent } from './diseases-by-simptoms.component';

describe('DiseasesBySimptomsComponent', () => {
  let component: DiseasesBySimptomsComponent;
  let fixture: ComponentFixture<DiseasesBySimptomsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiseasesBySimptomsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiseasesBySimptomsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
