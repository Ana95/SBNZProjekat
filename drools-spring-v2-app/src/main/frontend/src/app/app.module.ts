import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';

import { AppComponent } from './app.component';
import { AdministratorComponent } from './components/administrator/administrator.component';
import { DoctorComponent } from './components/doctor/doctor.component';
import { IllnessComponent } from './components/illness/illness.component';
import { CureComponent } from './components/cure/cure.component';
import { LoginComponent } from './components/login/login.component';
import { MedicalWorkerComponent } from './components/medical-worker/medical-worker.component';
import { PatientComponent } from './components/patient/patient.component';
import { SystemDiagnosisComponent } from './components/system-diagnosis/system-diagnosis.component';
import { CureAllergyComponent } from './components/cure-allergy/cure-allergy.component';
import { ReportComponent } from './components/report/report.component';

import { IngredientService } from './services/ingredient.service';
import { IllnessService } from './services/illness.service';
import { SymptomService } from './services/symptom.service';
import { MedicamentService } from './services/medicament.service';
import { WebSocketServiceService } from './services/web-socket-service.service';

const appRoutes: Routes = [
  { path: 'administrator', component: AdministratorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'doctor', component: MedicalWorkerComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    DoctorComponent,
    IllnessComponent,
    CureComponent,
    LoginComponent,
    MedicalWorkerComponent,
    PatientComponent,
    SystemDiagnosisComponent,
    CureAllergyComponent,
    ReportComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MultiselectDropdownModule,
    RouterModule.forRoot(
      appRoutes,
    { enableTracing: true })
  ],
  providers: [
    IngredientService,
    IllnessService,
    SymptomService,
    MedicamentService,
    WebSocketServiceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
