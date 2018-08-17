import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Medicament } from '../model/medicament';

@Injectable({
  providedIn: 'root'
})
export class MedicamentService {

  constructor(private http : HttpClient) {

   }

  addMedicament(medicament : Medicament){
    return this.http.post<Medicament>("/api/medicaments", 
      {
        "name" : medicament.name,
        "category" : medicament.category,
        "ingredients" : medicament.ingredients
      }
    );
  }

  getMedicaments(){
    return this.http.get<Medicament[]>("/api/medicaments");
  }

  getMedicament(medicamentId : number){
    return this.http.get<Medicament>("/api/medicaments/" + medicamentId);
  }

  updateMedicament(medicament : Medicament){
    return this.http.put<Medicament>("/api/medicaments", 
      {
        "id" : medicament.id,
        "name" : medicament.name,
        "category" : medicament.category,
        "ingredients" : medicament.ingredients
      }
    );
  }

  deleteMedicamentById(medicamentId : number){
    return this.http.delete('/api/medicaments/' + medicamentId, { responseType : "text"});
  }

}
