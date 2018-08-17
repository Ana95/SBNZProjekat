import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Medicament } from '../../model/medicament';
import { Patient } from '../../model/patient';
import { Ingredient } from '../../model/ingredient';
import { MedicamentAllergy } from '../../model/medicament-allergy';
import { IngredientAllergy } from '../../model/ingredient-allergy';
import { MEDICAMENTTYPE } from '../../model/medicamenttype.enum';
import { PatientService } from '../../services/patient.service';
import { MedicamentService } from '../../services/medicament.service';
import { IngredientService } from '../../services/ingredient.service';
import { MedicamentAllergyService } from '../../services/medicament-allergy.service';
import { IngredientAllergyService } from '../../services/ingredient-allergy.service';
declare var $ : any;

@Component({
  selector: 'app-cure-allergy',
  templateUrl: './cure-allergy.component.html',
  styleUrls: ['./cure-allergy.component.css']
})
export class CureAllergyComponent implements OnInit {

  image_patient : string = "/assets/img/patientImg1.png";
  image_medicine : string = "/assets/img/pillImage.png";
  setMedicamentAsMedicamentAllergy : Medicament;
  setMedicament : Medicament;
  setIngredientAsIngredientAllergy : Ingredient;
  medicaments : Medicament[];
  ingredientsByMedicament : Ingredient[];
  patients : Patient[];
  selectedPatientId : number;
  medicamentAllergiesByPatientId : MedicamentAllergy[];
  ingredientAllergiesByPatientId : IngredientAllergy[];
  visibleMedicamentAllergies : boolean = true;
  visibleIngredientAllergies : boolean = false;
  addMedicamentOrIngredientAllergy : boolean = false;
  errorMessage : string;

  constructor(private medicamentService : MedicamentService, private patientService : PatientService, 
    private ingredientService : IngredientService, private medicamentAllergyService : MedicamentAllergyService,
    private ingredientAllergyService : IngredientAllergyService) {
  }

  ngOnInit() {
    let patientId : number;
    this.patientService.getPatients().subscribe(
      res => {
        this.patients = res;
        if(this.patients.length != 0){
          this.medicamentAllergyService.getMedicamentAllergiesByPatientId(this.patients[0].id).subscribe(
            res => this.medicamentAllergiesByPatientId = res,
            err => this.errorHandle(err)
          );
          this.ingredientAllergyService.getIngredientAllergiesByPatientId(this.patients[0].id).subscribe(
            res => {
              this.ingredientAllergiesByPatientId = res;
              for(let ia of this.ingredientAllergiesByPatientId){
                this.ingredientService.getIngredient(ia.ingredientId).subscribe(
                  res => {
                    let ingredient = res;
                    this.medicamentService.getMedicaments().subscribe(
                      res => {
                        for(let m of res){
                          for(let i of m.ingredients){
                            if(i.id = ingredient.id){
                              ia.medicament = m;
                            }
                          }
                        }
                      },err => this.errorHandle(err)
                    );
                  }, err => this.errorHandle(err)
                );
              }
            },err => this.errorHandle(err)
          );
        }else{
          this.ingredientAllergiesByPatientId = [];
          this.medicamentAllergiesByPatientId = [];
        }
      },err => this.errorHandle(err)
    );
  }

  onChanged(){
    this.selectedPatientId = $("#patientwithAllergyByMedicament").find(':selected').attr('value');
    this.medicamentAllergyService.getMedicamentAllergiesByPatientId(this.selectedPatientId).subscribe(
      res => this.medicamentAllergiesByPatientId = res,
      err => this.errorHandle(err)
    );
  }

  onChangedPatient(){
    let patientId = $("#patientWithAllergyByIngredient").find(':selected').attr('value');
    this.ingredientAllergyService.getIngredientAllergiesByPatientId(patientId).subscribe(
      res => {
        this.ingredientAllergiesByPatientId = res;
        for(let ia of this.ingredientAllergiesByPatientId){
          this.ingredientService.getIngredient(ia.ingredientId).subscribe(
            res => {
              let ingredient = res;
              this.medicamentService.getMedicaments().subscribe(
                res =>{
                  for(let m of res){
                    for(let i of m.ingredients){
                      if(i.id == ingredient.id){
                        ia.medicament = m;
                      }
                    }
                  }
                }, err => this.errorHandle(err)
              );
            }, err => this.errorHandle(err)
          );
        }
      },
      err => this.errorHandle(err)
    );
  }

  next(){
    if(this.visibleIngredientAllergies){
      this.visibleIngredientAllergies = false;
      this.visibleMedicamentAllergies = true;
      this.medicamentAllergyService.getMedicamentAllergiesByPatientId(this.patients[0].id).subscribe(
        res => this.medicamentAllergiesByPatientId = res,
        err => this.errorHandle(err)
      );
    }else{
      this.visibleMedicamentAllergies = false;
      this.visibleIngredientAllergies = true;
      this.ingredientAllergyService.getIngredientAllergiesByPatientId(this.patients[0].id).subscribe(
        res => {
          this.ingredientAllergiesByPatientId = res;
          for(let ia of this.ingredientAllergiesByPatientId){
            this.ingredientService.getIngredient(ia.ingredientId).subscribe(
              res =>{
                let ingredient = res;
                this.medicamentService.getMedicaments().subscribe(
                  res =>{
                    for(let m of res){
                      for(let i of m.ingredients){
                        if(i.id == ingredient.id){
                          ia.medicament = m;
                        }
                      }
                    }
                  }, err => this.errorHandle(err)
                );
              }, err => this.errorHandle(err)
            );
          }
        }, err => this.errorHandle(err)
      );
    }
  }

  previous(){
    if(this.visibleIngredientAllergies){
      this.visibleIngredientAllergies = false;
      this.visibleMedicamentAllergies = true;
    }else{
      this.visibleMedicamentAllergies = false;
      this.visibleIngredientAllergies = true;
    }
  }

  redirectToAdding(){
    if(this.visibleIngredientAllergies || this.visibleMedicamentAllergies){
      this.visibleIngredientAllergies = false;
      this.visibleMedicamentAllergies = false;
      this.addMedicamentOrIngredientAllergy = true;
      this.medicamentService.getMedicaments().subscribe(
        res => {
          this.medicaments = res;
          for(let medicament of this.medicaments){
            if(medicament.category.toString() == MEDICAMENTTYPE[MEDICAMENTTYPE.ANTIBIOTICS]){
              medicament.helper = "Antibiotik";
            }else if(medicament.category.toString() == MEDICAMENTTYPE[MEDICAMENTTYPE.ANALGESICS]){
              medicament.helper = "Analgetik";
            }else{
              medicament.helper = "Drugi";
            }
          }
        }, err => this.errorHandle(err)
      );
    }
  }

  medicamentAllergy(medicament : Medicament){
    this.setMedicamentAsMedicamentAllergy = medicament;
  }

  medicamentIngredients(medicament : Medicament){
    this.setMedicament = medicament;
    console.log(this.setMedicament);
    this.ingredientService.getIngredientsByMedicament(medicament.id).subscribe(
      res => this.ingredientsByMedicament = res,
      err => this.errorHandle(err)
    );
  }

  patientMedicamentAllergy(){
    let alreadyExist : boolean = false;
    let patientId = $("#choosePatientForMedicamentAllergy").find(':selected').attr('value');
    let medicamentId = this.setMedicamentAsMedicamentAllergy.id;
    let medicamentName = this.setMedicamentAsMedicamentAllergy.name;
    let medicamentAllergy = new MedicamentAllergy(medicamentId, medicamentName, patientId);
    this.medicamentAllergyService.getMedicamentAllergiesByPatientId(patientId).subscribe(
      res => {
        $('#patientwithAllergyByMedicament option[value=' + patientId + ']').attr('selected','selected');
        this.medicamentAllergiesByPatientId = res;
        for(let ma of this.medicamentAllergiesByPatientId){
          if(ma.medicamentName == medicamentAllergy.medicamentName){
            alreadyExist = true;
          }
        }
        if(alreadyExist){
          this.errorMessage = "Pacijent je već alergičan na lijek " + medicamentName + "!";
          $("#error").modal("show");
        }else{
          this.medicamentAllergyService.addMedicamentAllergy(medicamentAllergy).subscribe(
            res => {
              this.medicamentAllergiesByPatientId.push(res);
            }, err => this.errorHandle(err)
          );
        }
      }, err => this.errorHandle(err)
    );
    $("#showPatients").modal("toggle");
    this.addMedicamentOrIngredientAllergy = false;
    this.visibleIngredientAllergies = false;
    this.visibleMedicamentAllergies = true;
  }

  deleteMedicamentAllergy(medicamentAllergy){
    this.medicamentAllergyService.deleteMedicamentAllergy(medicamentAllergy.id).subscribe(
      res => {
        for(let i in this.medicamentAllergiesByPatientId){
          if(this.medicamentAllergiesByPatientId[i].id == medicamentAllergy.id){
            this.medicamentAllergiesByPatientId.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err)
    );
  }

  allergyIngredient(ingredient : Ingredient){
    this.setIngredientAsIngredientAllergy = ingredient;
  }

  patientIngredientAllergy(){
    let alreadyExist : boolean = false;
    let patientId = $("#choosePatientForIngredientAllergy").find(':selected').attr('value');
    let ingredientId = this.setIngredientAsIngredientAllergy.id;
    let ingredientName = this.setIngredientAsIngredientAllergy.name;
    let ingredientAllergy = new IngredientAllergy(ingredientId, ingredientName, patientId);
    this.ingredientAllergyService.getIngredientAllergiesByPatientId(patientId).subscribe(
      res => {
        $('#patientWithAllergyByIngredient option[value=' + patientId + ']').attr('selected','selected');
        this.ingredientAllergiesByPatientId = res;
        for(let ia of this.ingredientAllergiesByPatientId){
          this.ingredientService.getIngredient(ia.ingredientId).subscribe(
            res => {
              let ingredient = res;
              this.medicamentService.getMedicaments().subscribe(
                res => {
                  for(let m of res){
                    for(let i of m.ingredients){
                      if(i.id == ingredient.id){
                        ia.medicament = m;
                      }
                    }
                  }
                }, err => this.errorHandle(err)
              );
            }, err => this.errorHandle(err)
          );
        }
        for(let ia of this.ingredientAllergiesByPatientId){
          if(ia.ingredientName == ingredientName){
            alreadyExist = true;
          }
        }
        if(alreadyExist){
          this.errorMessage = "Pacijent je već alergičan na sastojak " + ingredientName + " lijeka " + this.setMedicament.name + "!";
          $("#error").modal("show");
        }else{
          this.ingredientAllergyService.addIngredientAllergy(ingredientAllergy).subscribe(
            res => {
              res.medicament = this.setMedicament;
              this.ingredientAllergiesByPatientId.push(res);
            },err => this.errorHandle(err)
          );
        }
      }, err => this.errorHandle(err)
    );
    $("#patientsWithAllergyByingredients").modal("toggle");
    $("#showIngredients").modal("toggle");
    this.addMedicamentOrIngredientAllergy = false;
    this.visibleMedicamentAllergies = false;
    this.visibleIngredientAllergies = true;
  }

  deleteIngredientAllergy(ingredientAllergy : IngredientAllergy){
    this.ingredientAllergyService.deleteIngredientAllergy(ingredientAllergy.id).subscribe(
      res => {
        for(let i in this.ingredientAllergiesByPatientId){
          if(this.ingredientAllergiesByPatientId[i].id == ingredientAllergy.id){
            this.ingredientAllergiesByPatientId.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err)
    );
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
