export class MedicamentAllergy {
    id : number;
    medicamentId : number;
    medicamentName : string;
    patientId : number;
    constructor(medicamentId : number, medicamentName : string, patientId : number){
        this.medicamentId = medicamentId;
        this.medicamentName = medicamentName;
        this.patientId = patientId;
    }
}
