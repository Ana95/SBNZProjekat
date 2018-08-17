import { Component, OnInit } from '@angular/core';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { FormBuilder, FormGroup, Validators , FormControl} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Symptom } from '../../model/symptom';
import { Illness } from '../../model/illness';
import { User } from '../../model/user';
import { ILLNESSTYPE } from '../../model/illnesstype.enum';
import { Term } from '../../model/term.enum';
import { SymptomService } from '../../services/symptom.service';
import { IllnessService } from '../../services/illness.service';
declare var $ : any;

@Component({
  selector: 'app-illness',
  templateUrl: './illness.component.html',
  styleUrls: ['./illness.component.css']
})
export class IllnessComponent implements OnInit {
  image_disease : string = "assets/img/disease.png";
  rForm : FormGroup;
  updateForm : FormGroup;
  addSymptomForm : FormGroup;
  updateSymptomForm : FormGroup;
  error:string ="Polje je obavezno!";
  illnessForUpdate : Illness;
  illnessBySymptoms : Illness;
  symptomForUpdate : Symptom;
  currentUser:User = JSON.parse(localStorage.getItem("currentUser"));
  illnesses : Illness[];
  symptoms : Symptom[];
  isSelected : boolean;
  isSetTemperature : boolean;

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private illnessService : IllnessService) {
    this.rForm = fb.group({
      'title': [null, Validators.required],
      'type': [null, Validators.required]
    });
    this.updateForm = fb.group({
      'title': [null, Validators.required],
      'type': [null, Validators.required]
    });
    this.addSymptomForm = fb.group({
      'symptom': [],
      'temp' : []
    },{
      validator:(formgroup:FormGroup)=>{
        return this.validateInput(formgroup);
      }
    });
    this.updateSymptomForm = fb.group({
      'title': [],
      'temperature' : []
    },{
      validator:(formgroup:FormGroup)=>{
        return this.validateNumber(formgroup);
      }
    });
  }

  ngOnInit() {  
    this.illnessService.getIllnesses().subscribe(
      res => {
        this.illnesses = res;
        for(let illness of this.illnesses){
          if(illness.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.FIRST]){
            illness.illnessTypeHelper = "Bolest prve grupe";
          }else if(illness.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.SECOND]){
            illness.illnessTypeHelper = "Bolest druge grupe";
          }else{
            illness.illnessTypeHelper = "Bolest treće grupe";
          }
        }
      }, err => this.errorHandle(err));
  }

  validateNumber(formgroup: FormGroup){
    if(this.isSetTemperature){
      if (formgroup.controls["title"].value && formgroup.controls["temperature"].value && 
        !isNaN(formgroup.controls["temperature"].value) && (formgroup.controls["temperature"].value >= 35 &&
        formgroup.controls["temperature"].value <=45)) {
          return null;
      }else{
        if(!formgroup.controls["title"].value){
          return {requiredTitle : true};
        }
        if(formgroup.controls["title"].value && !formgroup.controls["temperature"].value){
          console.log("polje je obavezno!")
          return {requiredTemperature : true};
        }
        if(formgroup.controls["title"].value && formgroup.controls["temperature"].value && isNaN(formgroup.controls["temperature"].value)){
          console.log("nije broj")
          return {numberInvalid : true};
        }
        if(formgroup.controls["title"].value && formgroup.controls["temperature"].value && !isNaN(formgroup.controls["temperature"].value)
          && !(formgroup.controls["temperature"].value >= 35 && formgroup.controls["temperature"].value <=45)){
            console.log("opseg ne valja")
            return {numberScopeInvalid : true};
        }
      }
    }else{
      if(!(formgroup.controls["title"].value)){
        return {requiredTitle : true};
      }
    }
  }

  validateInput(formgroup : FormGroup){
    if((formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && formgroup.controls["temp"].value 
        && !isNaN(formgroup.controls["temp"].value) && (formgroup.controls["temp"].value >= 35 && formgroup.controls["temp"].value <= 45)) || 
      (formgroup.controls["symptom"].value != Term[Term.TEMPERATURE] && formgroup.controls["symptom"].value)) {
      return null;
    }
    else {
      if(!formgroup.controls["symptom"].value){
        return {requiredSymptom : true};
      }
      if(formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && !formgroup.controls["temp"].value){
        return {requiredNumber : true};
      }
      if(formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && formgroup.controls["temp"].value 
        && isNaN(formgroup.controls["temp"].value)){
        return {validateNumberData : true};
      }
      if(formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && formgroup.controls["temp"].value 
        && !isNaN(formgroup.controls["temp"].value) && !(formgroup.controls["temp"].value >= 35 && formgroup.controls["temp"].value <= 45)){
        return {validateScopeNumberData : true};
      }
    }
  }

  onChanged(){
    if(this.addSymptomForm.get('symptom').value == Term[Term.TEMPERATURE]){
      this.isSelected = true;
    }else{
      this.isSelected = false;
    }
  }

  addIllness(data : any){
    let illness_type : ILLNESSTYPE;
    if(data.type == "Bolest prve grupe"){
      illness_type = ILLNESSTYPE.FIRST;
    }else if(data.type == "Bolest druge grupe"){
      illness_type = ILLNESSTYPE.SECOND;
    }else{
      illness_type = ILLNESSTYPE.THIRD;
    }

    let newIllness = new Illness(data.title, null, illness_type);
    let foundIllness : boolean = false;

    for(let i of this.illnesses){
      if(i.name.toLocaleLowerCase() == newIllness.name.toLocaleLowerCase()){
        foundIllness = true;
      }
    }

    if(!foundIllness){
      this.illnessService.addIllness(newIllness).subscribe(
        res => {
          res.illnessTypeHelper = data.type;
          this.illnesses.push(res);
          $('#addIllness').modal("toggle");
        }, err => this.errorHandle(err)
      );
    }else{
      $("#error").modal("show");
    }
  }

  deleteIllness(illness : Illness){
    this.illnessService.deleteIllnessById(illness.id).subscribe(
      res=>{
        for(let i in this.illnesses){
          if(this.illnesses[i].id == illness.id){
            this.illnesses.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err));
  }

  updateIllness(illness : Illness){
    this.illnessForUpdate = illness;
    this.updateForm.controls['title'].setValue(illness.name);
    this.updateForm.controls['type'].setValue(illness.illnessTypeHelper);
  }

  updateIllnessValue(data : any){
    this.illnessForUpdate.name = data.title;
    this.illnessForUpdate.illnessTypeHelper = data.type;
    if(data.type == "Bolest prve grupe"){
      this.illnessForUpdate.illness_type = ILLNESSTYPE.FIRST;
    }else if(data.type == "Bolest druge grupe"){
      this.illnessForUpdate.illness_type = ILLNESSTYPE.SECOND;
    }else{
      this.illnessForUpdate.illness_type = ILLNESSTYPE.THIRD;
    }
    this.illnessService.updateIllness(this.illnessForUpdate).subscribe(
      res => {},
      err => this.errorHandle(err));
      $('#updateIllness').modal("toggle");
  }

  showSymptoms(illness : Illness){
    this.illnessBySymptoms = illness;
    this.symptomService.getSymptomsByIllness(illness.id).subscribe(
      res => {
        this.symptoms = res;
        for(let symptom of this.symptoms){
          if(symptom.isSpecific){
            symptom.isSpecificHelper = "Da";
          }else{
            symptom.isSpecificHelper = "Ne";
          }
        }
      }, err => this.errorHandle(err));  
  }

  addSymptom(data : any){
    console.log(this.illnessBySymptoms);
    let name : string = $("#selectedTerm option:selected").text();
    let term : Term = $("#selectedTerm").find(':selected').attr('value');
    let isSpecific : boolean = $('#isSpecific').is(':checked');
    let temperature : number = this.addSymptomForm.controls["temp"].value;
    
    let newSymptom = new Symptom(term, name, temperature, isSpecific, this.illnessBySymptoms);
    let foundSymptom : boolean = false;

    for(let s of this.symptoms){
      if(s.helper == newSymptom.helper){
        foundSymptom = true;
      }
    }

    if(!foundSymptom){
      this.symptomService.addSymptom(newSymptom).subscribe(
        res => {
          if(res.isSpecific){
            res.isSpecificHelper = "Da";
          }else{
            res.isSpecificHelper = "Ne";
          }
          this.symptoms.push(res);
          $("#addSymptom").modal("toggle");
        },
        err => this.errorHandle(err)
      );
    }else{
      $("#error").modal("show");
    }
  }

  updateSymptom(symptom : Symptom){
    this.symptomForUpdate = symptom;
    if(symptom.temperature != null){
      this.isSetTemperature = true;
      this.updateSymptomForm.controls['temperature'].setValue(symptom.temperature);
    }else{
      this.isSetTemperature = false;
    }
    this.updateSymptomForm.controls['title'].setValue(symptom.helper);
    $("#isSpecificUpdate").attr("checked", symptom.isSpecific);
  }

  updateSymptomValue(data : any){
    this.symptomForUpdate.helper = data.title;
    if(this.symptomForUpdate.temperature != null){
      this.symptomForUpdate.temperature = data.temperature;
    }
    this.symptomForUpdate.isSpecific = $('#isSpecificUpdate').is(':checked');
    if(this.symptomForUpdate.isSpecific){
      this.symptomForUpdate.isSpecificHelper = "Da";
    }else{
      this.symptomForUpdate.isSpecificHelper = "Ne";
    }
    this.symptomService.updateSymptom(this.symptomForUpdate).subscribe(
      res => {},
      err => this.errorHandle(err));
    $("#updateSymptom").modal("toggle");
  }

  deleteSymptom(symptom : Symptom){
    this.symptomService.deleteSymptomById(symptom.id).subscribe(
      res => {
        for(let i in this.symptoms){
          if(this.symptoms[i].id == symptom.id){
            this.symptoms.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err));
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
