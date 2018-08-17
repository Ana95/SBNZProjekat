import { Term } from '../model/term.enum';
import { Illness } from '../model/illness';

export class Symptom {
    id : number;
    term : Term;
    helper : string;
    temperature : number;
    isSpecific : boolean;
    isSpecificHelper : string;
    illness : Illness;
    constructor(term : Term, helper : string, temperature : number, isSpecific : boolean, illness : Illness){
        this.term = term;
        this.helper = helper;
        this.temperature = temperature;
        this.isSpecific = isSpecific;
        this.illness = illness;
    }
}
