import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Patient } from '../model/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  readonly URL : string = 'http://localhost:8080/patients';

  constructor(private http : HttpClient) { 

  }

  getPatients(){
    return this.http.get<Patient[]>(this.URL);
  }

  addPatient(patient : Patient){
    return this.http.post<Patient>(this.URL, 
      {
        "name" : patient.name,
        "surname" : patient.surname,
        "age" : patient.age,
        "diseases" : patient.diseases,
        "medicines" : patient.medicines
      }
    );
  }

  updatePatient(patient : Patient){
    return this.http.put<Patient>(this.URL, 
      {
        "id" : patient.id,
        "name" : patient.name,
        "surname" : patient.surname,
        "age" : patient.age,
        "diseases" : patient.diseases,
        "medicines" : patient.medicines
      }
    );
  }

  deletePatientById(patientId : number){
    return this.http.delete(this.URL + '/' + patientId);
  }
  
}
