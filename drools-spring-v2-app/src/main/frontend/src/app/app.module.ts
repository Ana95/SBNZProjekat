import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiselectDropdownModule } from 'angular-2-dropdown-multiselect';

import { AppComponent } from './app.component';
import { AdministratorComponent } from './components/administrator/administrator.component';
import { DoctorComponent } from './components/doctor/doctor.component';
import { MedicinecomponentComponent } from './components/medicinecomponent/medicinecomponent.component';
import { DiseaseComponent } from './components/disease/disease.component';
import { SymptomComponent } from './components/symptom/symptom.component';

import { DoctorService } from './services/doctor.service';
import { ComponentService } from './services/component.service';
import { DiseaseService } from './services/disease.service';
import { SymptomService } from './services/symptom.service';
import { MedicineService } from './services/medicine.service';
import { CureComponent } from './components/cure/cure.component';

const appRoutes: Routes = [
  { path: 'administrator', component: AdministratorComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    AdministratorComponent,
    DoctorComponent,
    MedicinecomponentComponent,
    DiseaseComponent,
    SymptomComponent,
    CureComponent
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
    DoctorService,
    ComponentService,
    DiseaseService,
    SymptomService,
    MedicineService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
