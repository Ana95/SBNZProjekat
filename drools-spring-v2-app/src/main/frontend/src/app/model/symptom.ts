export class Symptom {
    id : number;
    exist : boolean = false;
    title : string;
    isSpecific : boolean = false;
    constructor(title : string){
        this.title = title;
    }
}
