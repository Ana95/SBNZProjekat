import { Component, OnInit } from '@angular/core';
import { Disease } from '../../model/disease';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
declare var $ : any;

@Component({
  selector: 'app-personal-diagnosis',
  templateUrl: './personal-diagnosis.component.html',
  styleUrls: ['./personal-diagnosis.component.css']
})
export class PersonalDiagnosisComponent implements OnInit {

  personal_diagnostic : Disease[];
  diseaseForUpdate : Disease;
  rForm : FormGroup;
  updateForm : FormGroup;
  error:string ="Polje je obavezno!";
  post:any;

  constructor(private fb:FormBuilder) { 
    this.rForm = fb.group({
      'name':[null, Validators.required],
    });
    this.updateForm = fb.group({
      'title':[null, Validators.required],
    });
  }

  ngOnInit() {
    this.personal_diagnostic = [];
  }

  addPersonalDiagnosis(post){
    let name = post.name;
    let disease = new Disease(name, null, null);
    this.personal_diagnostic.push(disease);
  }

  deleteDisease(disease){
    for(let i in this.personal_diagnostic){
      if(this.personal_diagnostic[i].title == disease.title){
        this.personal_diagnostic.splice(Number(i), 1);
      }
    }
  }

  updateDisease(disease){
    this.diseaseForUpdate = disease;
    this.updateForm.controls['title'].setValue(disease.title);
  }

  updateDiagnosisValue(post){
    this.diseaseForUpdate.title = post.title;
    $('#updateDisease').modal("toggle");
  }


}
