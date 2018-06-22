import { Symptom } from '../model/symptom';
import { DISEASETYPE } from '../model/diseasetype.enum'; 

export class Disease {
    id : number;
    diseaseType : string;
    title : string;
    symptoms : Symptom[];
    type : DISEASETYPE;
    constructor(title : string, symptoms : Symptom[], type : DISEASETYPE){
        this.title = title;
        this.symptoms = symptoms;
        this.type = type;
    }

}
