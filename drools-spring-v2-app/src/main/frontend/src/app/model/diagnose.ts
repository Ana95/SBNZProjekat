export class Diagnose {
    id: number;
    illnessName: string;
    date: Date;
    patientId: number;
    patientName: string;
    doctorId: number;
    doctorName;
    constructor(illnessName: string, date: Date, patientId: number, doctorId: number){
        this.illnessName = illnessName;
        this.date = date;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

}
