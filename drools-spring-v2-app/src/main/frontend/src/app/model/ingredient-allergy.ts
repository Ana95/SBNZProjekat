import { Medicament } from '../model/medicament';

export class IngredientAllergy {
    id : number;
    ingredientId : number;
    ingredientName : string;
    medicament : Medicament;
    patientId : number;
    constructor(ingredientId : number, ingredientName : string, patientId : number){
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.patientId = patientId;
    }
}
