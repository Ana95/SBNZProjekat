import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalDiagnosisComponent } from './personal-diagnosis.component';

describe('PersonalDiagnosisComponent', () => {
  let component: PersonalDiagnosisComponent;
  let fixture: ComponentFixture<PersonalDiagnosisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonalDiagnosisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalDiagnosisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
