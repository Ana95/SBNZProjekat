import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemDiagnosisComponent } from './system-diagnosis.component';

describe('SystemDiagnosisComponent', () => {
  let component: SystemDiagnosisComponent;
  let fixture: ComponentFixture<SystemDiagnosisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SystemDiagnosisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemDiagnosisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
