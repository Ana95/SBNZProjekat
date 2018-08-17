import { Medicament } from '../model/medicament';

export class Ingredient {
    id : number;
    name : string;
    medicament : Medicament;
    constructor(name : string, medicament : Medicament){
        this.name = name;
        this.medicament = medicament;
    }
}
