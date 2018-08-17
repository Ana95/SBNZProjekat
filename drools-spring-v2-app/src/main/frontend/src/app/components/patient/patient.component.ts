import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../model/patient';
declare var $ : any; 

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  rForm : FormGroup;
  updateForm : FormGroup;
  image_patient : string = "assets/img/patientImg.png";
  image_medicine : string = "assets/img/pillImage.png";
  age : FormControl;
  updateAge : FormControl;
  error:string ="Polje je obavezno!";
  patients : Patient[];
  patientForUpdate : Patient;

  constructor(private fb:FormBuilder, private patientService : PatientService) {
    this.age = new FormControl('', [
      Validators.required,
      this.validateNumber.bind(this)
    ]);
    this.updateAge = new FormControl('', [
      Validators.required,
      this.validateNumber.bind(this)
    ]);
    this.rForm = fb.group({
      'patientId' : [null, Validators.required],
      'name': [null, Validators.required],
      'surname': [null, Validators.required],
      'age': this.age
    });
    this.updateForm = fb.group({
      'patientId' : [null, Validators.required],
      'name': [null, Validators.required],
      'surname': [null, Validators.required],
      'updateAge': this.updateAge
    });
   }

  ngOnInit() {
    this.patientService.getPatients().subscribe(
      res => this.patients = res,
      err => this.errorHandle(err));
  }

  validateNumber(control: FormControl){
    if (isNaN(control.value)) {
      return { numberInvalid : true };
    }else{
      return null;
    } 
  }

  addPatient(data){
    this.patientService.addPatient(new Patient(data.patientId, data.name, data.surname, data.age)).subscribe(
      res => {
        this.patients.push(res);
      },
      err => this.errorHandle(err));
    $('#addPatient').modal("toggle");
  }

  deletePatient(patient){
    this.patientService.deletePatientById(patient.id).subscribe(
      res => {
        console.log(res);
        for(let i in this.patients){
          if(this.patients[i].id == patient.id){
            this.patients.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err));
  }

  updatePatient(patient){
    this.patientForUpdate = patient;
    this.updateForm.controls['patientId'].setValue(patient.patientId);
    this.updateForm.controls['name'].setValue(patient.name);
    this.updateForm.controls['surname'].setValue(patient.surname);
    this.updateForm.controls['updateAge'].setValue(patient.age);
  }

  updatePatientValues(post){
    this.patientForUpdate.patientId = post.patientId;
    this.patientForUpdate.name = post.name;
    this.patientForUpdate.surname = post.surname;
    this.patientForUpdate.age = post.updateAge;
    this.patientService.updatePatient(this.patientForUpdate).subscribe(
      res =>{},
      err => this.errorHandle(err));
    $('#updatePatient').modal("toggle");
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
