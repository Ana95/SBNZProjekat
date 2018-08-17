import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { IngredientAllergy } from '../model/ingredient-allergy';

@Injectable({
  providedIn: 'root'
})
export class IngredientAllergyService {

  constructor(private http : HttpClient) { 

  }

  addIngredientAllergy(ingredientAllergy : IngredientAllergy){
    return this.http.post<IngredientAllergy>("/api/ingredientAllergies", 
      {
        "ingredientId" : ingredientAllergy.ingredientId,
        "ingredientName" : ingredientAllergy.ingredientName,
        "patientId" : ingredientAllergy.patientId

      }
    );
  }
  
  getIngredientAllergiesByPatientId(patientId : number){
    let param = new HttpParams().set("patientId", patientId.toString());
    return this.http.get<IngredientAllergy[]>("/api/ingredientAllergies", { params : param});
  }

  deleteIngredientAllergy(ingredientAllergyId : number){
    return this.http.delete("/api/ingredientAllergies/" + ingredientAllergyId, {responseType : "text"});
  }
}
