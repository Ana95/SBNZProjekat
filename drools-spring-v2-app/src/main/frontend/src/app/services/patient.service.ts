import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Patient } from '../model/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  
  constructor(private http : HttpClient) { 

  }

  getPatients(){
    return this.http.get<Patient[]>("/api/patients");
  }

  getPatient(patientId : number){
    return this.http.get<Patient>("/api/patients/" + patientId);
  }

  addPatient(patient : Patient){
    return this.http.post<Patient>("/api/patients", 
      {
        "patientId" : patient.patientId,
        "name" : patient.name,
        "surname" : patient.surname,
        "age" : patient.age
      }
    );
  }

  updatePatient(patient : Patient){
    return this.http.put<Patient>("/api/patients", 
      {
        "id" : patient.id,
        "patientId" : patient.patientId,
        "name" : patient.name,
        "surname" : patient.surname,
        "age" : patient.age
      }
    );
  }

  deletePatientById(patientId : number){
    return this.http.delete('/api/patients/' + patientId, {responseType : 'text'});
  }
  
}
