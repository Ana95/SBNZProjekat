import { Illness } from '../model/illness';
import { Patient } from '../model/patient';
import { User } from '../model/user';
import { Medicament } from '../model/medicament';

export class Record {
    id: number;
    illness: Illness;
    patient: Patient;
    date: Date;
    doctor: User;
    medicaments: Medicament[];
    constructor(illness: Illness, patient: Patient, date: Date, doctor: User, medicaments: Medicament[]){
        this.illness = illness;
        this.patient = patient;
        this.date = date;
        this.doctor = doctor;
        this.medicaments = medicaments;
    }

}
