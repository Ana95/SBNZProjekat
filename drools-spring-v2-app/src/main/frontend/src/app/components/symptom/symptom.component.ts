import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Symptom } from '../../model/symptom';
import { SymptomService } from '../../services/symptom.service';
declare var $ : any;

@Component({
  selector: 'app-symptom',
  templateUrl: './symptom.component.html',
  styleUrls: ['./symptom.component.css']
})
export class SymptomComponent implements OnInit {

  rForm : FormGroup;
  updateForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  content : string;
  symptom : Symptom;
  symptomForUpdate : Symptom;
  symptoms : Symptom[];

  constructor(private fb:FormBuilder, private symptomService : SymptomService) {
    this.rForm = fb.group({
      'content':[null, Validators.required]
    });
    this.updateForm = fb.group({
      'content':[null, Validators.required]
    });
  }

  ngOnInit() {
    this.symptomService.getSymptoms().subscribe(
      res => this.symptoms = res
    ), err => this.errorHandle(err);
  }

  addSymptom(post){
    this.content = post.content;
    let newSymptom = new Symptom(this.content);
    this.symptomService.addSymptom(newSymptom).subscribe(
      res =>{
        this.symptom = res;
        this.symptoms.push(this.symptom);
      }
    ), err => this.errorHandle(err);
  }

  deleteSymptom(symptom){
    this.symptomService.deleteSymptomById(symptom.id).subscribe(
      res =>{
        for(let i in this.symptoms){
          if(this.symptoms[i].id == symptom.id){
            this.symptoms.splice(Number(i), 1);
          }
        }
      }
    ), err => this.errorHandle(err);
  }

  updateSymptom(symptom){
    this.symptomForUpdate = symptom;
    this.updateForm.controls['content'].setValue(symptom.title);
  }

  updateSymptomValue(post){
    this.symptomForUpdate.title = post.content;
    this.symptomService.updateSymptom(this.symptomForUpdate).subscribe(
      res =>{
      }
    ), err => this.errorHandle(err);
    $('#updateSymptom').modal("toggle");
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
