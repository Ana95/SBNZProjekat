import { Disease } from '../model/disease';
import { Medicine } from '../model/medicine';

export class Patient {
    id : number;
    name : string;
    surname : string;
    age : number;
    diseases : Disease[];
    medicines : Medicine[];
    constructor(name : string, surname : string, age : number, diseases : Disease[], medicines : Medicine[]){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.medicines = medicines;
        this.diseases = diseases;
    }
}
