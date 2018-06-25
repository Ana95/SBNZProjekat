import { Component, OnInit } from '@angular/core';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Disease } from '../../model/disease';
import { DiseaseService } from '../../services/disease.service';
import { Medicine } from '../../model/medicine';
import { MedicineService } from '../../services/medicine.service';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../model/patient';
declare var $ : any; 

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  myOptions: IMultiSelectOption[];
  myOptions1: IMultiSelectOption[];
  selecOptions : number[] = [];
  selecOptions1 : number[] = [];
  rForm : FormGroup;
  updateForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  diseases : Disease[];
  medicines : Medicine[];
  patients : Patient[];
  patientForUpdate : Patient;

  constructor(private fb:FormBuilder, private medicineService : MedicineService, private diseaseService : DiseaseService,
    private patientService : PatientService) {
    this.rForm = fb.group({
      'name':[null, Validators.required],
      'surname': [null, Validators.required],
      'age': [null, Validators.required],
      'optionsModel' : [null, Validators.required],
      'optionsModel1' : [null, Validators.required],
    });
    this.updateForm = fb.group({
      'name':[null, Validators.required],
      'surname': [null, Validators.required],
      'age': [null, Validators.required],
      'optionsModel' : [null, Validators.required],
      'optionsModel1' : [null, Validators.required],
    });
   }

  ngOnInit() {
    this.diseaseService.getDiseases().subscribe(
      res=>{
        this.myOptions1 = [];
        for(let i of res){
          this.myOptions1.push(
            {
              id: i.id,
              name: i.title
            }
          );
        }
        this.medicineService.getMedicines().subscribe(
          res => {
            this.myOptions = [];
            for(let j of res){
              this.myOptions.push(
                {
                  id : j.id,
                  name : j.title
                }
              );
            }
            this.patientService.getPatients().subscribe(
              res => {
                this.patients = res;
              }
            ), err => this.errorHandle(err);
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
  }

  addPatient(post){
    let name = post.name;
    let surname = post.surname;
    let age = post.age;
    let data = post.optionsModel;
    let data1 = post.optionsModel1;
    let medicines : Medicine[] =[];
    let diseases : Disease[] = [];
    this.medicineService.getMedicines().subscribe(
      res => {
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              medicines.push(j);
            }
          }
        }
        this.diseaseService.getDiseases().subscribe(
          res => {
            for(let i of data1){
              for(let j of res){
                if(i == j.id){
                  diseases.push(j);
                }
              }
            }
            let newPatient = new Patient(name, surname, age, diseases, medicines);
            this.patientService.addPatient(newPatient).subscribe(
              res =>{
                this.patients.push(res);
              }
            ), err => this.errorHandle(err);
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
    $('#addPatient').modal("toggle");
  }

  deletePatient(patient){
    this.patientService.deletePatientById(patient.id).subscribe(
      res => {
        for(let i in this.patients){
          if(this.patients[i].id == patient.id){
            this.patients.splice(Number(i), 1);
          }
        }
      }
    ), err => this.errorHandle(err);
  }

  updatePatient(patient){
    this.patientForUpdate = patient;
    for(let i of patient.medicines){
      this.selecOptions.push(i.id);
    }
    for(let i of patient.diseases){
      this.selecOptions1.push(i.id);
    }
    this.updateForm.controls['name'].setValue(patient.name);
    this.updateForm.controls['surname'].setValue(patient.surname);
    this.updateForm.controls['age'].setValue(patient.age);
  }
  updatePatientValues(post){
    this.patientForUpdate.name = post.name;
    this.patientForUpdate.surname = post.surname;
    this.patientForUpdate.age = post.age;
    let data = post.optionsModel;
    let data1 = post.optionsModel1;
    let medicine : Medicine[] = [];
    let disease : Disease[] = [];
    this.medicineService.getMedicines().subscribe(
      res => {
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              medicine.push(j);
            }
          }
        }
        this.diseaseService.getDiseases().subscribe(
          res => {
            for(let i of data1){
              for(let j of res){
                if(i == j.id){
                  disease.push(j);
                }
              }
            }
            this.patientForUpdate.medicines = medicine;
            this.patientForUpdate.diseases = disease;
            this.patientService.updatePatient(this.patientForUpdate).subscribe(
              res =>{

              }
            ), err => this.errorHandle(err);
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
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
