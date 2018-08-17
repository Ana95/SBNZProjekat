import { Patient } from '../model/patient';
import { REPORTTYPE } from '../model/report-type.enum';

export class Report {
    id: number;
    patient: Patient;
    category: REPORTTYPE;
    helper: string;
    constructor(id: number, patient: Patient, category: REPORTTYPE, helper: string){
        this.id = id;
        this.patient = patient;
        this.category = category;
        this.helper = helper;
    }

}
