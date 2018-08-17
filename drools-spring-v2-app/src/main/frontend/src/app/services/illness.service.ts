import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Illness } from '../model/illness';
import { Symptom } from '../model/symptom';

@Injectable({
  providedIn: 'root'
})
export class IllnessService {

  constructor(private http: HttpClient) {

   }

   addIllness(illness : Illness){
     return this.http.post<Illness>("/api/illnesses", 
      {
        "name" : illness.name,
        "illness_type" : illness.illness_type,
        "symptoms" : illness.symptoms
      })
   }

   getIllnesses(){
     return this.http.get<Illness[]>("/api/illnesses");
   }

   getIllnessByName(illnessName : string){
     let param = new HttpParams().set('illnessName', illnessName);
     return this.http.get<Illness>("/api/illnesses", {params : param});
   }

   getOneIllness(symptoms: Symptom[], patientId: number){
    let param = new HttpParams().set('patientId', patientId.toString());
    return this.http.post<Illness[]>("/api/illnesses/getOneIllness", symptoms, {params : param});
   }

   getSimilarIllnesses(symptoms: Symptom[], patientId: number){
    let param = new HttpParams().set('patientId', patientId.toString());
    return this.http.post<Illness[]>("/api/illnesses/getAllIllness", symptoms, {params : param});
   }

   getIllness(illnessId : number){
     return this.http.get<Illness>("/api/illnesses/" + illnessId);
   }

   getIllnessSymptoms(illness: Illness){
     return this.http.post<Symptom[]>("/api/illnesses/symptoms", illness);
   }

   updateIllness(illness : Illness){
     return this.http.put<Illness>("/api/illnesses", 
      {
        "id" : illness.id,
        "name" : illness.name,
        "illness_type" : illness.illness_type,
        "symptoms" : illness.symptoms,
      })
   }

  deleteIllnessById(illnessId : number){
     return this.http.delete('/api/illnesses/' + illnessId, {responseType : 'text'});
  }
}
