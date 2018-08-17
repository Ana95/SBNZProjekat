import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Symptom } from '../model/symptom';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  constructor(private http : HttpClient) {

  }

  addSymptom(symptom : Symptom){
    console.log(symptom);
    return this.http.post<Symptom>("/api/symptoms", 
      {
        "term" : symptom.term,
        "helper" : symptom.helper,
        "temperature" : symptom.temperature,
        "isSpecific" : symptom.isSpecific,
        "illness" : symptom.illness
      });
  }

  getSymptoms(){
    return this.http.get<Symptom[]>("/api/symptoms");
  }

  getSymptomsByIllness(illnessId : number){
    let param = new HttpParams().set('illnessId', illnessId.toString());
    return this.http.get<Symptom[]>("/api/symptoms", {params : param});
  }

  getSortedSymptomsByIllness(illness_id : number){
    let param = new HttpParams().set('illness_id', illness_id.toString());
    return this.http.get<Symptom[]>("/api/symptoms", {params : param});
  }

  updateSymptom(symptom : Symptom){
    return this.http.put<Symptom>("/api/symptoms", 
    {
      "id" : symptom.id,
      "term" : symptom.term,
      "helper" : symptom.helper,
      "temperature" : symptom.temperature,
      "isSpecific" : symptom.isSpecific,
      "illness" : symptom.illness
    });
  }
  
  deleteSymptomById(symptomId : number){
    return this.http.delete('/api/symptoms/' + symptomId, {responseType : 'text'});
  }
  
}
