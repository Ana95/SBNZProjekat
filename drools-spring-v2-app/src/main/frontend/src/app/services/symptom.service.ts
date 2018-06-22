import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Symptom } from '../model/symptom';

@Injectable({
  providedIn: 'root'
})
export class SymptomService {

  readonly URL : string = 'http://localhost:8080/symptoms';

  constructor(private http : HttpClient) {

  }

  addSymptom(symptom : Symptom){
    return this.http.post<Symptom>(this.URL, 
      {
        "title" : symptom.title
      });
  }

  getSymptoms(){
    return this.http.get<Symptom[]>(this.URL);
  }

  updateSymptom(symptom : Symptom){
    return this.http.put<Symptom>(this.URL, 
    {
      "id" : symptom.id,
      "title" : symptom.title
    });
  }
  
  deleteSymptomById(symptomId : number){
    return this.http.delete(this.URL + '/' + symptomId);
  }
  
}
