import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CureComponent } from './cure.component';

describe('CureComponent', () => {
  let component: CureComponent;
  let fixture: ComponentFixture<CureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
