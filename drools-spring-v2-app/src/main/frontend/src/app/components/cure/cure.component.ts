import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Ingredient } from '../../model/ingredient';
import { Medicament } from '../../model/medicament';
import { User } from '../../model/user';
import { MEDICAMENTTYPE } from '../../model/medicamenttype.enum';
import {  IngredientService } from '../../services/ingredient.service';
import { MedicamentService } from '../../services/medicament.service';
declare var $:any;

@Component({
  selector: 'app-cure',
  templateUrl: './cure.component.html',
  styleUrls: ['./cure.component.css']
})
export class CureComponent implements OnInit {

  image_medicine : string = "assets/img/pillImage.png";
  addMedicamentForm : FormGroup;
  updateMedicamentForm : FormGroup;
  addIngredientForm : FormGroup;
  updateIngredientForm : FormGroup;
  error:string ="Polje je obavezno!";
  medicament : Medicament;
  currentUser: User = JSON.parse(localStorage.getItem("currentUser"));
  medicamentForUpdate : Medicament;
  medicaments : Medicament[];
  medicamentIngredients : Ingredient[];
  ingredientForUpdate : Ingredient;

  constructor(private fb:FormBuilder, private ingredientService : IngredientService, private medicamentService : MedicamentService) {
    this.addMedicamentForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required]
    });
    this.updateMedicamentForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required]
    });
    this.updateIngredientForm = fb.group({
      'title':[null, Validators.required]
    });
    this.addIngredientForm = fb.group({
      'title':[null, Validators.required]
    });
  }

  ngOnInit() {
    this.medicamentService.getMedicaments().subscribe(
      res => {
        this.medicaments = res;
        for(let medicament of this.medicaments){
          if(medicament.category.toString() == "ANTIBIOTICS"){
            medicament.helper = "Antibiotik";
          }else if(medicament.category.toString() == "ANALGESICS"){
            medicament.helper = "Analgetik";
          }else{
            medicament.helper = "Drugi";
          }
        }
      },
      err => this.errorHandle(err));
  }

  addMedicament(data){
    let title = data.title;
    let medicineType = data.type;
    let type: MEDICAMENTTYPE;
    if(medicineType == "Antibiotik"){
      type = MEDICAMENTTYPE.ANTIBIOTICS;
    }else if(medicineType == "Analgetik"){
      type = MEDICAMENTTYPE.ANALGESICS;
    }else{
      type = MEDICAMENTTYPE.OTHER;
    }
    let medicament = new Medicament(title, type, null);
    let foundMedicament : boolean = false;

    for(let m of this.medicaments){
      if(m.name.toLocaleLowerCase() == data.title.toLocaleLowerCase()){
        foundMedicament = true;
      }
    }

    if(!foundMedicament){
      this.medicamentService.addMedicament(medicament).subscribe(
        res => {
          this.medicament = res;
          this.medicament.helper = medicineType;
          this.medicaments.push(this.medicament);
          $('#addMedicament').modal("toggle");
        }, err => this.errorHandle(err)
      );
    }else{
      $("#error").modal("show");
    }
  }

  deleteMedicament(medicament : Medicament){
    this.medicamentService.deleteMedicamentById(medicament.id).subscribe(
      res => {
        for(let i in this.medicaments){
          if(this.medicaments[i].id == medicament.id){
            this.medicaments.splice(Number(i), 1);
          }
        }
      }, err => this.errorHandle(err));
  }

  updateMedicament(medicament : Medicament){
    this.medicamentForUpdate = medicament;
    this.updateMedicamentForm.controls['title'].setValue(medicament.name);
    this.updateMedicamentForm.controls['type'].setValue(medicament.helper);
  }

  updateMedicamentValue(data){
    this.medicamentForUpdate.name = data.title;
    this.medicamentForUpdate.helper = data.type;
    if(this.medicamentForUpdate.category.toString() == "Antibiotik"){
      this.medicamentForUpdate.category = MEDICAMENTTYPE.ANTIBIOTICS;
    }else if(this.medicamentForUpdate.category.toString() == "Analgetik"){
      this.medicamentForUpdate.category = MEDICAMENTTYPE.ANALGESICS;
    }else{
      this.medicamentForUpdate.category = MEDICAMENTTYPE.OTHER;
    }
    this.medicamentService.updateMedicament(this.medicamentForUpdate).subscribe(
      res => {},
      err => this.errorHandle(err));
    $('#updateMedicament').modal("toggle");
  }

  showIngredients(medicament){
    $('#ingredients').modal("show");
    this.medicament = medicament;
    this.ingredientService.getIngredientsByMedicament(medicament.id).subscribe(
      res =>{
        this.medicament.ingredients = res;
        this.medicamentIngredients = this.medicament.ingredients;
      },
      err => this.errorHandle(err));
  }

  addIngredient(data){
    let ingredient = new Ingredient(data.title, this.medicament);
    let foundIngredient : boolean = false;

    for(let mi of this.medicamentIngredients){
      if(mi.name.toLocaleLowerCase() == data.title.toLocaleLowerCase()){
        foundIngredient = true;
      }
    }

    if(!foundIngredient){
      this.ingredientService.addIngredient(ingredient).subscribe(
        res => {
          this.medicamentIngredients.push(res);
          $('#addIngredient').modal("toggle");
        },
        err => this.errorHandle(err)
      );
    }else{
      $("#error").modal("show");
    }
      
  }

  updateIngredient(ingredient : Ingredient){
    this.ingredientForUpdate = ingredient;
    this.updateIngredientForm.controls['title'].setValue(ingredient.name);
  }

  updateIngredientValue(data){
    console.log(this.ingredientForUpdate.id);
    this.ingredientForUpdate.name = data.title;
    this.ingredientService.updateIngredient(this.ingredientForUpdate).subscribe(
      res => {},
      err => this.errorHandle(err));
      $('#updateIngredient').modal("toggle");
  }

  deleteIngredient(ingredientId : number){
    console.log(ingredientId);
    this.ingredientService.deleteIngredient(ingredientId).subscribe(
      res =>{
        console.log(res);
        for(let i in this.medicamentIngredients){
          if(this.medicamentIngredients[i].id == ingredientId){
            this.medicamentIngredients.splice(Number(i), 1);
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
