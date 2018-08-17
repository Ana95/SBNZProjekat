export class DiagnoseMedicament {
    id: number;
    diagnoseId: number;
    medicamentId: number;
    medicamentName: string;
    medicamentCategory: string;
    patientId: number;
    doctorId: number;
    date: Date;
    illnessName: string;
    constructor(diagnoseId: number, medicamentId: number, medicamentName: string, medicamentCategory: string){
        this.diagnoseId = diagnoseId;
        this.medicamentId = medicamentId;
        this.medicamentName = medicamentName;
        this.medicamentCategory = medicamentCategory;
    }

}
