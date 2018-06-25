import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Doctor } from '../model/doctor';

@Injectable()
export class DoctorService {

  readonly URL : string = 'http://localhost:8080/doctors';

  constructor(private http : HttpClient) { 

  }

  addDoctor(doctor : Doctor){
    return this.http.post<Doctor>(this.URL,
      {
        'username' : doctor.username,
        'password' : doctor.password,
        'name' : doctor.name,
        'surname' : doctor.surname,
        'role' : doctor.role,
        'institution' : doctor.institution
      } 
    );
  }

  updateDoctor(doctor : Doctor){
    return this.http.put<Doctor>(this.URL, 
      {
        'id' : doctor.id,
        'username' : doctor.username,
        'password' : doctor.password,
        'name' : doctor.name,
        'surname' : doctor.surname,
        'role' : doctor.role,
        'institution' : doctor.institution
      }
    );
  }

  getDoctors(){
    return this.http.get<Doctor[]>(this.URL);
  }

  deleteDoctorById(doctorId : number){
    return this.http.delete(this.URL + '/' + doctorId);
  }

}
