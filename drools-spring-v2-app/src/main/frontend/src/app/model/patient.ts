import { Medicament } from '../model/medicament';

export class Patient {
    id : number;
    patientId: string;
    name : string;
    surname : string;
    age : number;
    constructor(patientId : string, name : string, surname : string, age : number){
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
