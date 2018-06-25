import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Symptom } from '../../model/symptom';
import { Disease } from '../../model/disease';
import { SymptomService } from '../../services/symptom.service';
import { DiseaseService } from '../../services/disease.service';
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
  diseases : Disease[];

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private diseaseService : DiseaseService) {
    this.rForm = fb.group({
      'content':[null, Validators.required]
    });
    this.updateForm = fb.group({
      'content':[null, Validators.required]
    });
  }

  ngOnInit() {
    this.diseaseService.getDiseases().subscribe(
      res =>{
        this.diseases = res;
        this.symptomService.getSymptoms().subscribe(
          data =>{
            this.symptoms = data;
            for(let i of this.diseases){
              let diseaseSymptoms : Symptom[] = i.symptoms;
              for(let j of diseaseSymptoms){
                for(let h of this.symptoms){
                  if(j.id == h.id){
                    h.exist = true;
                  }
                }
              }
            }
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
  }

  addSymptom(post){
    this.content = post.content;
    let newSymptom = new Symptom(this.content);
    console.log(newSymptom.isSpecific);
    if($('#addSpecific').is(":checked")){
      newSymptom.isSpecific = true;
    }
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
    console.log(symptom.isSpecific);
    this.updateForm.controls['content'].setValue(symptom.title);
    if(symptom.isSpecific){
      $('#updateSpecific').attr('checked', 'checked'); 
    }
  }

  updateSymptomValue(post){
    this.symptomForUpdate.title = post.content;
    this.symptomForUpdate.isSpecific = $('#updateSpecific').is(":checked");
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
