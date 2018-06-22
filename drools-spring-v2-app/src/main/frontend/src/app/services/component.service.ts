import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MedicineComponent } from '../model/medicine-component';

@Injectable({
  providedIn: 'root'
})
export class ComponentService {

  readonly URL : string = 'http://localhost:8080/components';

  constructor(private http : HttpClient) {

  }

  addComponent(component : MedicineComponent){
    return this.http.post<MedicineComponent>(this.URL, 
      {
        "title" : component.title
      });
  }

  getComponents(){
    return this.http.get<MedicineComponent[]>(this.URL);
  }

  deleteComponent(componentId : number){
    return this.http.delete(this.URL + '/' + componentId);
  }

  updateComponent(component : MedicineComponent){
    return this.http.put<MedicineComponent>(this.URL, 
    {
      "id" : component.id,
      "title" : component.title
    });
  }
  
}
