import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { SymptomService } from '../../services/symptom.service';
import { Symptom } from '../../model/symptom';
import { Disease } from '../../model/disease';
import { DiseaseService } from '../../services/disease.service';

@Component({
  selector: 'app-diseases-by-simptoms',
  templateUrl: './diseases-by-simptoms.component.html',
  styleUrls: ['./diseases-by-simptoms.component.css']
})
export class DiseasesBySimptomsComponent implements OnInit {

  myOptions: IMultiSelectOption[];
  rForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  symptoms : Symptom[];
  diseases : Disease[];

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private diseaseService: DiseaseService) { 
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
    this.diseaseService.getDiseasesByRules(new Disease(null, symptoms, null)).subscribe(
      res =>{
        this.diseases = res;
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
