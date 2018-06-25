import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalWorkerComponent } from './medical-worker.component';

describe('MedicalWorkerComponent', () => {
  let component: MedicalWorkerComponent;
  let fixture: ComponentFixture<MedicalWorkerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicalWorkerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicalWorkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
