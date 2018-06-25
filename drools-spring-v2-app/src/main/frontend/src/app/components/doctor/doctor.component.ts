import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Doctor } from '../../model/doctor';
import { DoctorService } from '../../services/doctor.service';
declare var $ : any;

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent implements OnInit {

  image_doctor : string = "assets/img/doctor.png";
  rForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  username : string;
  password : string;
  name : string;
  surname : string;
  institution : string;
  doctor : Doctor;
  doctorForUpdate : Doctor;
  doctors : Doctor[];

  constructor(private fb:FormBuilder, private doctorService : DoctorService) {
    this.rForm = fb.group({
      'username':[null, Validators.required],
      'password': [null, Validators.required],
      'name': [null, Validators.required],
      'surname': [null, Validators.required],
      'institution': [null, Validators.required]
    });
   }

  ngOnInit() {
    this.doctorService.getDoctors().subscribe(
      res => {
        this.doctors = res;
      }
    ), err => this.errorHandle(err);
  }

  addDoctor(post){
    this.username = post.username;
    this.password = post.password;
    this.name = post.name;
    this.surname = post.surname;
    this.institution = post.institution;
    let newDoctor = new Doctor(this.username, this.password, this.name, this.surname, this.institution);
    this.doctorService.addDoctor(newDoctor).subscribe(
      res =>{
        this.doctors.push(res);
      }
    ), err => this.errorHandle(err);
    $('#addDoctor').modal("toggle");

  }

  deleteDoctor(doctor : Doctor){
    this.doctorService.deleteDoctorById(doctor.id).subscribe(
      res =>{
        for(let i in this.doctors){
          if(this.doctors[i].id == doctor.id){
            this.doctors.splice(Number(i), 1);
          }
        }
      }), err => this.errorHandle(err);
  }

  updateDoctor(doctor){
    this.doctorForUpdate = doctor;
    this.rForm.controls['username'].setValue(this.doctorForUpdate.username);
    this.rForm.controls['password'].setValue(this.doctorForUpdate.password);
    this.rForm.controls['name'].setValue(this.doctorForUpdate.name);
    this.rForm.controls['surname'].setValue(this.doctorForUpdate.surname);
    this.rForm.controls['institution'].setValue(this.doctorForUpdate.institution);
  }

  updateDoctorsValue(post){
    this.doctorForUpdate.username = post.username;
    this.doctorForUpdate.password = post.password;
    this.doctorForUpdate.name = post.name;
    this.doctorForUpdate.surname = post.surname;
    this.doctorForUpdate.institution = post.institution;
    this.doctorService.updateDoctor(this.doctorForUpdate).subscribe(
      res => {
        this.rForm.controls['username'].setValue("");
        this.rForm.controls['password'].setValue("");
        this.rForm.controls['name'].setValue("");
        this.rForm.controls['surname'].setValue("");
        this.rForm.controls['institution'].setValue("");
      }
    ), err => this.errorHandle(err);
    $('#updateDoctor').modal("toggle");
  }

  errorHandle(err: HttpErrorResponse){
    if(err.error instanceof Error){
      console.log("Client-side Error occured!");
    }else{
      console.log("Server-side Error occured!");
      console.log(err.message);
    }
  }

}
