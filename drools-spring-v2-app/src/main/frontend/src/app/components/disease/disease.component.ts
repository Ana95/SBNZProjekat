import { Component, OnInit } from '@angular/core';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Symptom } from '../../model/symptom';
import { Disease } from '../../model/disease';
import { DISEASETYPE } from '../../model/diseasetype.enum';
import { SymptomService } from '../../services/symptom.service';
import { DiseaseService } from '../../services/disease.service';
declare var $:any;

@Component({
  selector: 'app-disease',
  templateUrl: './disease.component.html',
  styleUrls: ['./disease.component.css']
})
export class DiseaseComponent implements OnInit {
  myOptions: IMultiSelectOption[];
  image_disease : string = "assets/img/disease.png";
  rForm : FormGroup;
  updateForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  disease : Disease;
  diseaseForUpdate : Disease;
  diseases : Disease[];

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private diseaseService : DiseaseService) {
    this.rForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required],
      'optionsModel' : [null, Validators.required]
    });
    this.updateForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required],
      'optionsModel' : [null, Validators.required]
    });
  }

  ngOnInit() {
    this.symptomService.getSymptoms().subscribe(
      res =>{
        this.myOptions = [];
        for(let i of res){
          this.myOptions.push(
            {
              id : i.id,
              name : i.title
            }
          )
        }
        this.diseaseService.getDiseases().subscribe(
          data =>{
            this.diseases = data;
            for(let i of this.diseases){
              if(i.type.toString() == DISEASETYPE[DISEASETYPE.FIRST_GROUP]){
                i.diseaseType = "Bolest prve grupe";
              }else if(i.type.toString() == DISEASETYPE[DISEASETYPE.SECOND_GROUP]){
                i.diseaseType = "Bolest druge grupe";
              }else{
                i.diseaseType = "Bolest trece grupe";
              }
            }
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
  }

  addDisease(post){
    let title = post.title;
    let type = post.type;
    let data = post.optionsModel;
    let symptoms : Symptom[] = [];
    if(type == "Bolest prve grupe"){
      type = DISEASETYPE.FIRST_GROUP;
    }else if(type == "Bolest druge grupe"){
      type = DISEASETYPE.SECOND_GROUP
    }
    else{
      type = DISEASETYPE.THIRD_GROUP;
    }
    this.symptomService.getSymptoms().subscribe(
      res =>{
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              symptoms.push(j);
            }
          }
        }
        let newDisease = new Disease(title, symptoms, type);
        this.diseaseService.addDisease(newDisease).subscribe(
          data =>{
            this.disease = data;
            if(this.disease.type.toString() == DISEASETYPE[DISEASETYPE.FIRST_GROUP]){
              this.disease.diseaseType = "Bolest prve grupe";
            }else if(this.disease.type.toString() == DISEASETYPE[DISEASETYPE.SECOND_GROUP]){
              this.disease.diseaseType = "Bolest druge grupe";
            }else{
              this.disease.diseaseType = "Bolest trece grupe";
            }
            this.diseases.push(this.disease);
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
    $('#addDisease').modal("toggle");
  }

  deleteDisease(disease : Disease){
    this.diseaseService.deleteDiseaseById(disease.id).subscribe(
      res=>{
        for(let i in this.diseases){
          if(this.diseases[i].id == disease.id){
            this.diseases.splice(Number(i), 1)
          }
        }
      }
    ), err => this.errorHandle(err);
  }

  updateDisease(disease : Disease){
    this.diseaseForUpdate = disease;
    let selecOptions : number[] = [];
    for(let i of disease.symptoms){
      selecOptions.push(i.id);
    }
    this.updateForm.controls['title'].setValue(disease.title);
    this.updateForm.controls['optionsModel'].setValue(selecOptions);
    this.updateForm.controls['type'].setValue(disease.diseaseType);
  }

  updateDiseaseValue(post){
    this.diseaseForUpdate.title = post.title;
    this.diseaseForUpdate.diseaseType = post.type;
    if(this.diseaseForUpdate.diseaseType == "Bolest prve grupe"){
      this.diseaseForUpdate.type = DISEASETYPE.FIRST_GROUP;
    }else if(this.diseaseForUpdate.diseaseType == "Bolest druge grupe"){
      this.diseaseForUpdate.type = DISEASETYPE.FIRST_GROUP;
    }else{
      this.diseaseForUpdate.type = DISEASETYPE.THIRD_GROUP;
    }
    let data = post.optionsModel;
    let symptoms : Symptom[] = [];
    this.symptomService.getSymptoms().subscribe(
      res => {
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              symptoms.push(j);
            }
          }
        }
        this.diseaseForUpdate.symptoms = symptoms;
        this.diseaseService.updateDisease(this.diseaseForUpdate).subscribe(
          rl =>{

          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
    $('#updateDisease').modal("toggle");
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
