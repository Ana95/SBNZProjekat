import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, AbstractControl, ValidatorFn } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../model/user';
import { ROLE } from '../../model/role.enum';
import { UserService } from '../../services/user.service';
import { Subscription } from 'rxjs';
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
  email : FormControl;
  password : FormControl;
  repeatedPassword : FormControl;
  institution : string;
  doctor : User;
  doctorForUpdate : User;
  doctors : User[];

  constructor(private fb : FormBuilder, private userService : UserService) {
    var emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";
    this.email = new FormControl('', [
      Validators.required,
      Validators.pattern(emailPattern)
    ]);
    this.password =new FormControl('', [
      Validators.required,
      Validators.minLength(8)
    ]);
    this.repeatedPassword =new FormControl('', [
      Validators.required, 
      Validators.minLength(8), 
      this.matchOtherValidator('password')
    ]);
    this.rForm = this.fb.group({
    'username' : new FormControl(null, [Validators.required]),
    'password': this.password,
    'repeatedPassword': this.repeatedPassword,
    'name': new FormControl(null, [Validators.required]),
    'surname' : new FormControl(null, Validators.required),
    'email': this.email
    });
   }

  ngOnInit() {
    this.userService.getUsers().subscribe(
      res => {
        this.doctors = res;
      }, err => this.errorHandle(err));
  }

  addDoctor(data){
    var doctor = new User(data.username, data.password, data.name, data.surname, data.email, ROLE.DOCTOR);
    this.userService.addUser(doctor).subscribe(
      res => {
        this.doctor = res;
        this.doctors.push(this.doctor);
      }, err => this.errorHandle(err));
      $('#addDoctor').modal("toggle");
  }

  matchOtherValidator(otherControlName: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
        const otherControl: AbstractControl = control.root.get(otherControlName);

        if (otherControl) {
            const subscription: Subscription = otherControl
                .valueChanges
                .subscribe(() => {
                    control.updateValueAndValidity();
                    subscription.unsubscribe();
                });
        }

        return (otherControl && control.value !== otherControl.value) ? {match: true} : null;
    };
  } 

  deleteDoctor(doctor : User){
    this.userService.deleteUser(doctor.id).subscribe(
      res =>{
        console.log(res);
        for(let i in this.doctors){
          if(this.doctors[i].id == doctor.id){
            this.doctors.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err));
  }

  updateDoctor(doctor){
    this.doctorForUpdate = doctor;
    this.rForm.controls['username'].setValue(this.doctorForUpdate.username);
    this.rForm.controls['password'].setValue(this.doctorForUpdate.password);
    this.rForm.controls['repeatedPassword'].setValue(this.doctorForUpdate.password);
    this.rForm.controls['name'].setValue(this.doctorForUpdate.name);
    this.rForm.controls['surname'].setValue(this.doctorForUpdate.surname);
    this.rForm.controls['email'].setValue(this.doctorForUpdate.email);
  }

  updateDoctorsValue(data){
    this.doctorForUpdate.username = data.username;
    this.doctorForUpdate.password = data.password;
    this.doctorForUpdate.name = data.name;
    this.doctorForUpdate.surname = data.surname;
    this.doctorForUpdate.email = data.email;
    this.userService.updateUser(this.doctorForUpdate).subscribe(
      res => {
        this.rForm.controls['username'].setValue("");
        this.rForm.controls['password'].setValue("");
        this.rForm.controls['repeatedPassword'].setValue("");
        this.rForm.controls['name'].setValue("");
        this.rForm.controls['surname'].setValue("");
        this.rForm.controls['email'].setValue("");
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
