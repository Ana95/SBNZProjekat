import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../model/user'
import { Symptom } from '../../model/symptom';
import { Patient } from '../../model/patient';
import { Illness } from '../../model/illness';
import { Diagnose } from '../../model/diagnose';
import { Record } from '../../model/record';
import { Medicament } from '../../model/medicament';
import { DiagnoseMedicament } from '../../model/diagnose-medicament';
import { ILLNESSTYPE } from '../../model/illnesstype.enum';
import { MEDICAMENTTYPE } from '../../model/medicamenttype.enum';
import { Term } from '../../model/term.enum';
import { PatientService } from '../../services/patient.service';
import { SymptomService } from '../../services/symptom.service';
import { IllnessService } from '../../services/illness.service';
import { MedicamentService } from '../../services/medicament.service';
import { DiagnoseService } from '../../services/diagnose.service';
declare var $ : any;

@Component({
  selector: 'app-system-diagnosis',
  templateUrl: './system-diagnosis.component.html',
  styleUrls: ['./system-diagnosis.component.css']
})
export class SystemDiagnosisComponent implements OnInit {

  diagnosisTable : boolean = true;
  setPatientDiagnosis : boolean = false;
  rForm : FormGroup;
  image_patient : string = "assets/img/patientImg.png";
  image_illness : string = "assets/img/illness1.png";
  image_disease : string = "assets/img/disease.png";
  image_medicine : string = "assets/img/pillImage.png";
  error:string ="Polje je obavezno!";
  radioSelectedPatient : string;
  radioSelectedIllness : string;
  symptoms : string[] = [];
  choosenIllness: Illness;
  currentUser: User = JSON.parse(localStorage.getItem("currentUser"));
  terms : Symptom[] = [];
  patients : Patient[];
  illnesses : Illness[];
  getOneIllness : Illness[];
  getAllIllness : Illness[]; 
  medicaments : Medicament[];
  chooseMedicaments : Medicament[] = [];
  symptomsByIllness : Symptom[];
  diagnosesByPatientId: Diagnose[];
  diagnoseMedicaments: DiagnoseMedicament[];
  isChecked : boolean;
  isSelectedTerm : boolean;
  errorMessage : string;
  showPersonalDiagnosisDialog: boolean = false;
  showSystemDiagnosisDialog: boolean = false;
  showSimilarIllnessesDialog: boolean = false

  constructor(private fb:FormBuilder, private symptomService : SymptomService, private patientService : PatientService,
  private illnessService : IllnessService, private medicamentService : MedicamentService, private diagnoseService: DiagnoseService) {
    this.rForm = fb.group({
      'patient' : [],
      'symptom' : [],
      'temp' : []
    },{
      validator:(formgroup:FormGroup)=>{
        return this.validateInput(formgroup);
      }
    });
   }

  ngOnInit() {
    this.patientService.getPatients().subscribe(
      res => {
        this.patients = res;
        this.diagnoseService.getDiagnosesByPatientId(this.patients[0].id).subscribe(
          res => this.diagnosesByPatientId = res,
          err => this.errorHandle(err)
        );
      }, err => this.errorHandle(err)
    );
  }

  validateInput(formgroup : FormGroup){
    if((formgroup.controls["patient"].value && formgroup.controls["symptom"].value && formgroup.controls["symptom"].value != Term[Term.TEMPERATURE]) ||
        (formgroup.controls["patient"].value && formgroup.controls["symptom"].value && formgroup.controls["symptom"].value == Term[Term.TEMPERATURE]
        &&  !isNaN(formgroup.controls["temp"].value) && (formgroup.controls["temp"].value >= 35 && formgroup.controls["temp"].value <= 45))){
      return null;
    }else{
      if(!formgroup.controls["patient"].value){
        return {requiredPatient : true};
      }
      if(formgroup.controls["patient"].value && !formgroup.controls["symptom"].value){
        return {requiredSymptom : true};
      }
      if(formgroup.controls["patient"].value && formgroup.controls["symptom"].value && 
        formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && !formgroup.controls["temp"].value){
          return {requiredTemp : true};
      }
      if(formgroup.controls["patient"].value && formgroup.controls["symptom"].value && 
        formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && formgroup.controls["temp"].value && isNaN(formgroup.controls["temp"].value)){
          return {tempInvalid : true};
      }
      if(formgroup.controls["patient"].value && formgroup.controls["symptom"].value && 
        formgroup.controls["symptom"].value == Term[Term.TEMPERATURE] && formgroup.controls["temp"].value 
        && !isNaN(formgroup.controls["temp"].value) && !(formgroup.controls["temp"].value >= 35 && formgroup.controls["temp"].value <= 45)){
          return {tempScopeInvalid : true};
      }
    }
  }

  onChangedTerm(){
    if(this.rForm.controls["symptom"].value == Term[Term.TEMPERATURE]){
      this.isSelectedTerm = true;
    }else{
      this.isSelectedTerm = false;
    }
  }

  onChanged(value:boolean){
    this.isChecked = value;
  }

  systemReturnedIllness(illness: Illness){
    this.choosenIllness = illness;
    this.choosenIllness.symptoms = this.terms;
  }

  onChangedPatientWithDiagnosis(){
    let pId = $("#patientWithDiagnosis").find(':selected').attr('value');
    this.diagnoseService.getDiagnosesByPatientId(pId).subscribe(
      res => this.diagnosesByPatientId  = res,
      err => this.errorHandle(err)
    );
  }

  listOfPatients(){
    this.patientService.getPatients().subscribe(
      res => {
        this.patients = res;
        this.radioSelectedPatient = this.patients[0].id.toString();
      }, err => this.errorHandle(err)
    );
  }

  choosePatient(){
    let patient : Patient;
    for(let i in this.patients){
      if(this.patients[i].id.toString() == this.radioSelectedPatient){
        patient = this.patients[i];
      }
    }
    this.rForm.controls['patient'].setValue(patient.name + " " + patient.surname + " " + patient.patientId);
    $("#choosePatient").modal("toggle");
  }

  chooseSymptom(){
    let symptomTerm = new Symptom(null, null, null, null, null)
    let temperature = this.rForm.get('temp').value;
    let term : Term = $("#chooseSymptom").find(':selected').attr('value');
    let helper : string = $('#chooseSymptom option:selected').text();
    symptomTerm.term = term;
    if(this.isSelectedTerm){
      symptomTerm.temperature = temperature;
    }
    symptomTerm.helper = helper;
    this.terms.push(symptomTerm);
    this.symptoms.push(helper);

  }

  deleteSymptom(symptom : string){
    const index: number = this.symptoms.indexOf(symptom);
    if (index !== -1) {
        this.symptoms.splice(index, 1);
    }
    for(let i in this.terms){
      if(this.terms[i].helper == symptom){
        this.terms.splice(Number(i), 1);
      }
    }
  }

  systemDiagnosis(){
    this.showSystemDiagnosisDialog = true;
    this.showPersonalDiagnosisDialog = false;
    this.showSimilarIllnessesDialog = false;
    this.getOneIllness = [];
    this.illnessService.getOneIllness(this.terms, Number(this.radioSelectedPatient)).subscribe(
      res => {
        this.getOneIllness = res;
        for(let i of this.getOneIllness){
          if(i.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.FIRST]){
            i.illnessTypeHelper = "Bolest prve grupe";
          }else if(i.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.SECOND]){
            i.illnessTypeHelper = "Bolest druge grupe";
          }else{
            i.illnessTypeHelper = "Bolest treće grupe";
          }
        }
      }, err => {
        this.errorHandle(err);
        this.errorMessage = "U sistemu ne postoji bolest sa zadatim simptomima!";
        $("#error").modal("show");
      }
    );
  }

  personalDiagnosis(){
    this.showPersonalDiagnosisDialog = true;
    this.showSystemDiagnosisDialog = false;
    this.showSystemDiagnosisDialog = false;
    this.illnessService.getIllnesses().subscribe(
      res => this.illnesses = res,
      err => this.errorHandle(err)
    );
  }

  illnessesBySymptoms(){
    this.showSimilarIllnessesDialog = true;
    this.showPersonalDiagnosisDialog = false;
    this.showSystemDiagnosisDialog = false;
    this.getAllIllness = [];
    this.illnessService.getSimilarIllnesses(this.terms, Number(this.radioSelectedPatient)).subscribe(
      res => {
        this.getAllIllness = res;
        this.radioSelectedIllness = this.getAllIllness[0].id.toString();
        for(let i of this.getAllIllness){
          if(i.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.FIRST]){
            i.illnessTypeHelper = "Bolest prve grupe";
          }else if(i.illness_type.toString() == ILLNESSTYPE[ILLNESSTYPE.SECOND]){
            i.illnessTypeHelper = "Bolest druge grupe";
          }else{
            i.illnessTypeHelper = "Bolest treće grupe";
          }
        }
      }, err =>{
        this.errorHandle(err);
      }
    );
  }

  sortedSymptomsByIllness(){
    let illnessId : number = $("#selectIllness").find(':selected').attr('value');
    this.illnessService.getIllness(illnessId).subscribe(
      data => {
        this.illnessService.getIllnessSymptoms(data).subscribe(
          res => {
            this.symptomsByIllness = res;
            for(let sym of this.symptomsByIllness){
              if(sym.isSpecific){
                sym.isSpecificHelper = "Da";
              }else{
                sym.isSpecificHelper = "Ne";
              }
            }
          }, err => this.errorHandle(err));
      }, err => this.errorHandle(err)
    );
  }

  prescribeMedicamentPersonalDiagnosis(){
    this.chooseMedicaments = [];
    this.medicamentService.getMedicaments().subscribe(
      res => this.medicaments = res,
      err => this.errorHandle(err)
    );
  }

  prescribeMedicamentSimilarIllness(){
    this.chooseMedicaments = [];
    this.medicamentService.getMedicaments().subscribe(
      res => this.medicaments = res,
      err => this.errorHandle(err)
    );
  }

  prescribeMedicament(){
    this.chooseMedicaments = [];
    this.medicamentService.getMedicaments().subscribe(
      res => this.medicaments = res,
      err => this.errorHandle(err)
    );
  }

  addMedicament(){
    let medicamentId : number = $("#selectMedicament").find(':selected').attr('value');
    this.medicamentService.getMedicament(medicamentId).subscribe(
      res => {
        if(res.category.toString() == MEDICAMENTTYPE[MEDICAMENTTYPE.ANTIBIOTICS]){
          res.helper = "Antibiotik";
        }else if(res.category.toString() == MedicamentService[MEDICAMENTTYPE.ANALGESICS]){
          res.helper = "Analgetik";
        }else{
          res.helper = "Drugi";
        }
        this.chooseMedicaments.push(res)
      },err => this.errorHandle(err));
  }

  deleteMedicament(medicament : Medicament){
    for(let i in this.chooseMedicaments){
      if(this.chooseMedicaments[i].id == medicament.id){
        this.chooseMedicaments.splice(Number(i), 1);
      }
    }
  }

  addDiagnosis(){
    this.diagnosisTable = false;
    this.setPatientDiagnosis = true;
  }

  diagnosisSeted(){
    if(this.showSystemDiagnosisDialog){
      this.setDiagnoseBySystem();    
    }else if(this.showPersonalDiagnosisDialog){
      this.setDiagnosePersonal();
    }else{
      this.setDiagnoseBySimilarIllness();
    }
  }

  setDiagnosePersonal(){
    let allergy: boolean = false;
    this.setPatientDiagnosis = false;
    this.diagnosisTable = true;
    if(allergy){
      this.diagnosisTable = false;
      this.setPatientDiagnosis = true;
    }
    let pId: number = Number(this.radioSelectedPatient);
    let illnessId: number = $("#selectIllness").find(':selected').attr('value');
    this.patientService.getPatient(pId).subscribe(
      res =>{
        let patient: Patient = res;
        this.illnessService.getIllness(illnessId).subscribe(
          res =>{
            let illness: Illness = res;
            illness.symptoms = this.terms;
            let record: Record = new Record(illness, patient, null, this.currentUser, this.chooseMedicaments);
            this.diagnoseService.setDiagnose(record).subscribe(
              res =>{
                this.diagnoseService.getDiagnosesByPatientId(patient.id).subscribe(
                  res => {
                    this.diagnosesByPatientId = res;
                    $('.modal').modal('hide');
                    $('#patientWithDiagnosis option[value=' + pId + ']').attr('selected','selected');
                    this.rForm.controls['patient'].setValue("");
                    this.rForm.controls['symptom'].setValue("");
                    this.isSelectedTerm = false;
                    this.terms = [];
                    this.symptoms = [];
                  },err => this.errorHandle(err)
                );
              }, err => {
                this.errorMessage = err.error;
                this.diagnosisTable = false;
                this.setPatientDiagnosis = true;
                $("#error").modal("show");
              }
            );
          }, err => this.errorHandle(err)
        );
      }, err => this.errorHandle(err)
    );
  }

  setDiagnoseBySystem(){
    this.setPatientDiagnosis = false;
    this.diagnosisTable = true;
    let pId = this.radioSelectedPatient;
    this.patientService.getPatient(Number(pId)).subscribe(
      res => {
        let patient: Patient = res;
        let newRecord = new Record(this.choosenIllness, patient, null, this.currentUser, this.chooseMedicaments);
        this.diagnoseService.setDiagnose(newRecord).subscribe(
          res => {
            this.diagnoseService.getDiagnosesByPatientId(patient.id).subscribe(
              res => {
                this.diagnosesByPatientId = res;
                $('#patientWithDiagnosis option[value=' + pId + ']').attr('selected','selected');
                $('.modal').modal('hide');
                this.rForm.controls['patient'].setValue("");
                this.rForm.controls['symptom'].setValue("");
                this.isSelectedTerm = false;
                this.terms = [];
                this.symptoms = [];
              }, err => this.errorHandle(err)
            );
          }, err =>{
            this.errorMessage = err.error;
            this.setPatientDiagnosis = true;
            this.diagnosisTable = false;
            $("#error").modal("show");
          }
        );
      }, err => this.errorHandle(err)
    );
  }

  setDiagnoseBySimilarIllness(){
    this.setPatientDiagnosis = false;
    this.diagnosisTable = true;
    let illnessId: number = Number(this.radioSelectedIllness);
    let patientId: number = Number(this.radioSelectedPatient);
    this.illnessService.getIllness(illnessId).subscribe(
      res => {
        let illness: Illness = res;
        this.patientService.getPatient(patientId).subscribe(
          res => {
            let patient: Patient = res;
            let record: Record = new Record(illness, patient, null, this.currentUser, this.chooseMedicaments);
            this.diagnoseService.setDiagnose(record).subscribe(
              res => {
                this.diagnoseService.getDiagnosesByPatientId(patient.id).subscribe(
                  res => {
                    this.diagnosesByPatientId = res;
                    $('#patientWithDiagnosis option[value=' + patientId + ']').attr('selected','selected');
                    $('.modal').modal('hide');
                    this.rForm.controls['patient'].setValue("");
                    this.rForm.controls['symptom'].setValue("");
                    this.isSelectedTerm = false;
                    this.terms = [];
                    this.symptoms = [];
                  }, err => this.errorHandle(err)
                );
              }, err => {
                this.errorMessage = err.error;
                this.setPatientDiagnosis = true;
                this.diagnosisTable = false;
                $("#error").modal("show");
              }
            );
          }, err => this.errorHandle(err)
        );
      }, err => this.errorHandle(err)
    );
  }

  showMedicaments(diagnose: Diagnose){
    this.diagnoseService.getMedicamentsByDiagnose(diagnose.id).subscribe(
      res => {
        this.diagnoseMedicaments = res;
        for(let dm of this.diagnoseMedicaments){
          if(dm.medicamentCategory == "ANTIBIOTICS"){
            dm.medicamentCategory = "Antibiotik";
          }else if(dm.medicamentCategory == "ANALGESICS"){
            dm.medicamentCategory = "Analgetik";
          }else{
            dm.medicamentCategory = "Drugi";
          }
        }
      }, err => this.errorHandle(err)
    );
  }

  deleteDiagnosis(diagnose: Diagnose){
    this.diagnoseService.deleteDiagnose(diagnose.id).subscribe(
      res => {
        console.log(res);
        for(let i in this.diagnosesByPatientId){
          if(this.diagnosesByPatientId[i].id == diagnose.id){
            this.diagnosesByPatientId.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err)
    );
  }

  setDiagnosis(data : any){
    
  }

  errorHandle(err: HttpErrorResponse){
    if(err.error instanceof Error){
      console.log("Client-side Error occured!");
    }else{
      console.log("Server-side Error occured!");
      console.log(err.message);
      console.log(err.error);
    }
  }

}
