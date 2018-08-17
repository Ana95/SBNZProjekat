import { Ingredient } from '../model/ingredient';
import { MEDICAMENTTYPE } from '../model/medicamenttype.enum';

export class Medicament {
    id : number;
    name : string;
    category : MEDICAMENTTYPE;
    ingredients : Ingredient[];
    helper : string;
    constructor(name : string, category : MEDICAMENTTYPE, ingredients : Ingredient[]){
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
    }
}
