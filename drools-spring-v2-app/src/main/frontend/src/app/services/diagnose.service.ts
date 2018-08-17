import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Record } from '../model/record';
import { Diagnose } from '../model/diagnose';
import { DiagnoseMedicament } from '../model/diagnose-medicament';

@Injectable({
  providedIn: 'root'
})
export class DiagnoseService {

  constructor(private http: HttpClient) {

   }

   setDiagnose(record: Record){
      return this.http.post("/api/diagnoses", record, {responseType : "text"});
   }

   deleteDiagnose(diagnoseId: number){
     return this.http.delete("/api/diagnoses/" + diagnoseId, {responseType : "text"});
   }

   getDiagnosesByPatientId(patientId: number){
     let param = new HttpParams().set("patientId", patientId.toString());
     return this.http.get<Diagnose[]>("/api/diagnoses", {params : param});
   }

   getMedicamentsByDiagnose(diagnoseId: number){
     return this.http.get<DiagnoseMedicament[]>("/api/diagnoses/" + diagnoseId +"/medicaments");
   }

}
