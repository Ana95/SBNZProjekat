import { Component, OnInit } from '@angular/core';
import { IMultiSelectOption } from 'angular-2-dropdown-multiselect';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MedicineComponent } from '../../model/medicine-component';
import { Medicine } from '../../model/medicine';
import { MEDICINETYPE } from '../../model/medicinetype.enum';
import { ComponentService } from '../../services/component.service';
import { MedicineService } from '../../services/medicine.service';
declare var $:any;

@Component({
  selector: 'app-cure',
  templateUrl: './cure.component.html',
  styleUrls: ['./cure.component.css']
})
export class CureComponent implements OnInit {

  myOptions: IMultiSelectOption[];
  image_medicine : string = "assets/img/pillImage.png";
  addMedicineForm : FormGroup;
  updateMedicineForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  medicine : Medicine;
  medicineForUpdate : Medicine;
  medicines : Medicine[];

  constructor(private fb:FormBuilder, private componentService : ComponentService,private medicineService : MedicineService) {
    this.addMedicineForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required],
      'optionsModel' : [null, Validators.required]
    });
    this.updateMedicineForm = fb.group({
      'title':[null, Validators.required],
      'type': [null, Validators.required],
      'optionsModel' : [null, Validators.required]
    });
  }

  ngOnInit() {
    this.componentService.getComponents().subscribe(
      res =>{
        this.myOptions = [];
        for(let i of res){
          this.myOptions.push(
            {
              id : i.id,
              name : i.title
            }
          );
        }
        this.medicineService.getMedicines().subscribe(
          data =>{
            this.medicines = data;
            for(let i of this.medicines){
              if(i.type.toString() == MEDICINETYPE[MEDICINETYPE.ANTIBIOTIC]){
                i.medicineType = "Antibiotik";
              }else if(i.type.toString() == MEDICINETYPE[MEDICINETYPE.ANALGESIC]){
                i.medicineType = "Analgetik";
              }else{
                i.medicineType = "Drugi"; 
              }
            }
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
  }

  addMedicine(post){
    let title = post.title;
    let medicineType = post.type;
    let type: MEDICINETYPE;
    if(medicineType == "Antibiotik"){
      type = MEDICINETYPE.ANTIBIOTIC;
    }else if(medicineType == "Analgetik"){
      type = MEDICINETYPE.ANALGESIC;
    }else{
      type = MEDICINETYPE.OTHER;
    }
    let data = post.optionsModel;
    let components : MedicineComponent[] = [];
    this.componentService.getComponents().subscribe(
      res =>{
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              components.push(j);
            }
          }
        }
        let newMedicine = new Medicine(title, components, type);
        this.medicineService.addMedicine(newMedicine).subscribe(
          rl =>{
            this.medicine = rl;
            console.log(this.medicine.type);
            if(this.medicine.type.toString() == MEDICINETYPE[MEDICINETYPE.ANTIBIOTIC]){
              this.medicine.medicineType = "Antibiotik";
            }else if(this.medicine.type.toString() == MEDICINETYPE[MEDICINETYPE.ANALGESIC]){
              this.medicine.medicineType = "Analgetik";
            }else{
              this.medicine.medicineType = "Drugi";
            }
            this.medicines.push(this.medicine);
          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
    $('#addMedicine').modal("toggle");
  }

  deleteMedicine(medicine : Medicine){
    this.medicineService.deleteMedicineById(medicine.id).subscribe(
      res => {
        for(let i in this.medicines){
          if(this.medicines[i].id == medicine.id){
            this.medicines.splice(Number(i), 1);
          }
        }
      }
    ), err => this.errorHandle(err);
  }

  updateMedicine(medicine : Medicine){
    this.medicineForUpdate = medicine;
    let selecOptions:number[] = [];
    for(let i of medicine.components){
      selecOptions.push(i.id);
    }
    this.updateMedicineForm.controls['title'].setValue(medicine.title);
    this.updateMedicineForm.controls['optionsModel'].setValue(selecOptions);
    this.updateMedicineForm.controls['type'].setValue(medicine.medicineType);
  }

  updateMedicineValue(post){
    this.medicineForUpdate.title = post.title;
    this.medicineForUpdate.medicineType = post.type;
    if(this.medicineForUpdate.medicineType == "Antibiotik"){
      this.medicineForUpdate.type = MEDICINETYPE.ANTIBIOTIC;
    }else if(this.medicineForUpdate.medicineType == "Analgetik"){
      this.medicineForUpdate.type = MEDICINETYPE.ANALGESIC;
    }else{
      this.medicineForUpdate.type = MEDICINETYPE.OTHER;
    }
    let data = post.optionsModel;
    let components : MedicineComponent[] = [];
    this.componentService.getComponents().subscribe(
      res => {
        for(let i of data){
          for(let j of res){
            if(i == j.id){
              components.push(j);
            }
          }
        }
        this.medicineForUpdate.components = components;
        this.medicineService.updateMedicine(this.medicineForUpdate).subscribe(
          rl =>{

          }
        ), err => this.errorHandle(err);
      }
    ), err => this.errorHandle(err);
    $('#updateMedicine').modal("toggle");
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
