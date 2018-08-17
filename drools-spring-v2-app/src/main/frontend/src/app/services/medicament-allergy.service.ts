import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { MedicamentAllergy } from '../model/medicament-allergy';

@Injectable({
  providedIn: 'root'
})
export class MedicamentAllergyService {

  constructor(private http : HttpClient) {

   }

   addMedicamentAllergy(medicamentAllergy : MedicamentAllergy){
    return this.http.post<MedicamentAllergy>("/api/medicamentAllergies", 
      {
        "medicamentId" : medicamentAllergy.medicamentId,
        "medicamentName" : medicamentAllergy.medicamentName,
        "patientId" : medicamentAllergy.patientId
      }
    );
   }

   getMedicamentAllergiesByPatientId(patientId : number){
     let param = new HttpParams().set("patientId", patientId.toString());
     return this.http.get<MedicamentAllergy[]>("/api/medicamentAllergies", {params : param});
   }

   deleteMedicamentAllergy(medicamentAllergyId : number){
     return this.http.delete("/api/medicamentAllergies/" + medicamentAllergyId, {responseType : "text"});
   }
}
