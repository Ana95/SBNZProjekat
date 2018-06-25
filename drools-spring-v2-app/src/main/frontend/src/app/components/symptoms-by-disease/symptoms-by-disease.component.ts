import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Symptom } from '../../model/symptom';
import { SymptomService } from '../../services/symptom.service';
import { Disease } from '../../model/disease';
import { DiseaseService } from '../../services/disease.service';

@Component({
  selector: 'app-symptoms-by-disease',
  templateUrl: './symptoms-by-disease.component.html',
  styleUrls: ['./symptoms-by-disease.component.css']
})
export class SymptomsByDiseaseComponent implements OnInit {

  rForm : FormGroup;
  error:string ="Polje je obavezno!";
  diseases : Disease[];
  symptoms : Symptom[];
  sortedList : Symptom[] = [];

  constructor(private fb:FormBuilder, private diseaseService : DiseaseService) {
    this.rForm = fb.group({
      'name':[null, Validators.required]
    });
   }

  ngOnInit() {
  }

  showSymptoms(post){
    let specificSymptoms : Symptom[] = [];
    let nonSpecificSymptoms : Symptom[] = [];
    let titleDisease = post.name;
    this.diseaseService.getDiseases().subscribe(
      res => {
        this.diseases = res;
        for(let i of this.diseases){
          if(i.title == titleDisease){
            this.symptoms = i.symptoms;
            for(let i of this.symptoms){
              if(i.isSpecific){
                specificSymptoms.push(i);
              }else{
                nonSpecificSymptoms.push(i);
              }
            }
          }
        }
        for(let i of specificSymptoms){
          this.sortedList.push(i);
        }
        for(let j of nonSpecificSymptoms){
          this.sortedList.push(j);
        }
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
