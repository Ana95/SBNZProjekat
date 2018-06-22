import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Medicine } from '../model/medicine';

@Injectable({
  providedIn: 'root'
})
export class MedicineService {

  readonly URL : string = 'http://localhost:8080/medicines';

  constructor(private http : HttpClient) {

   }

  addMedicine(medicine : Medicine){
    return this.http.post<Medicine>(this.URL, 
      {
        "title" : medicine.title,
        "components" : medicine.components,
        "type" : medicine.type
      }
    );
  }

  getMedicines(){
    return this.http.get<Medicine[]>(this.URL);
  }

  updateMedicine(medicine : Medicine){
    return this.http.put<Medicine>(this.URL, 
      {
        "id" : medicine.id,
        "title" : medicine.title,
        "components" : medicine.components,
        "type" : medicine.type
      }
    );
  }

  deleteMedicineById(medicineId : number){
    return this.http.delete(this.URL + '/' + medicineId);
  }
}
