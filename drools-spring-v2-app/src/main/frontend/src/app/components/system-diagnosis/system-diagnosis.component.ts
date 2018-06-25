import { Component, OnInit } from '@angular/core';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SymptomService } from '../../services/symptom.service';
import { DiseaseService } from '../../services/disease.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Disease } from '../../model/disease';
import { User } from '../../model/user'
import { Symptom } from '../../model/symptom';

@Component({
  selector: 'app-system-diagnosis',
  templateUrl: './system-diagnosis.component.html',
  styleUrls: ['./system-diagnosis.component.css']
})
export class SystemDiagnosisComponent implements OnInit {

  myOptions: IMultiSelectOption[];
  rForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  symptoms : Symptom[];
  disease : Disease;
  diseases : Disease[];

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private diseaseService : DiseaseService) {
    this.rForm = fb.group({
      'optionsModel' : [null, Validators.required]
    });
   }

  ngOnInit() {
    this.symptomService.getSymptoms().subscribe(
      res => {
        this.symptoms = res;
        this.myOptions = [];
        for(let i of res){
          this.myOptions.push(
            {
              id : i.id,
              name : i.title
            }
          );
        }
      }
    ), err => this.errorHandle(err);
  }

  getSymptoms(post){
    let data = post.optionsModel;
    let symptoms : Symptom[] = [];
    for(let i of data){
      for(let j of this.symptoms){
        if(i == j.id){
          symptoms.push(j);
        }
      }
    }
    this.diseaseService.getDiseaseByRules(new Disease(null, symptoms, null)).subscribe(
      res =>{
        this.disease = res;
      }
    ), err => this.errorHandle(err);
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
