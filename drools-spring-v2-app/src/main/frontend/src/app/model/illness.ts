import { Symptom } from '../model/symptom';
import { ILLNESSTYPE } from '../model/illnesstype.enum'; 

export class Illness {
    id : number;
    illness_type : ILLNESSTYPE;
    name : string;
    symptoms : Symptom[];
    illnessTypeHelper : string;
    symptomsFound : number;
    constructor(name : string, symptoms : Symptom[], illness_type : ILLNESSTYPE){
        this.name = name;
        this.symptoms = symptoms;
        this.illness_type = illness_type;
    }

}
