import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Ingredient } from '../model/ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(private http : HttpClient) {

  }

  addIngredient(ingredient : Ingredient){
    return this.http.post<Ingredient>("/api/ingredients", 
      {
        "name" : ingredient.name,
        "medicament" : ingredient.medicament
      });
  }

  getIngredients(){
    return this.http.get<Ingredient[]>("/api/ingredients");
  }

  getIngredientsByMedicament(medicamentId : number){
    let param = new HttpParams().set('medicamentId', medicamentId.toString());
    return this.http.get<Ingredient[]>("/api/ingredients", {params : param});
  }

  getIngredient(ingredientId : number){
    return this.http.get<Ingredient>("/api/ingredients/" + ingredientId);
  }

  deleteIngredient(ingredientId : number){
    return this.http.delete('/api/ingredients/' + ingredientId, { responseType : "text"});
  }

  updateIngredient(ingredient : Ingredient){
    return this.http.put<Ingredient>("/api/ingredients", 
    {
      "id" : ingredient.id,
      "name" : ingredient.name,
      "medicament" : ingredient.medicament
    });
  }
  
}
