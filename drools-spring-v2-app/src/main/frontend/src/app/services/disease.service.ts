import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Disease } from '../model/disease';

@Injectable({
  providedIn: 'root'
})
export class DiseaseService {

  readonly URL : string = 'http://localhost:8080/diseases';

  constructor(private http: HttpClient) {

   }

   addDisease(disease : Disease){
     return this.http.post<Disease>(this.URL, 
      {
        "title" : disease.title,
        "symptoms" : disease.symptoms,
        "type" : disease.type
      })
   }

   getDiseases(){
     return this.http.get<Disease[]>(this.URL);
   }

   updateDisease(disease : Disease){
     return this.http.put<Disease>(this.URL, 
      {
        "id" : disease.id,
        "title" : disease.title,
        "symptoms" : disease.symptoms,
        "type" : disease.type
      })
   }

   deleteDiseaseById(diseaseId : number){
     return this.http.delete(this.URL + '/' + diseaseId);
   }
}
