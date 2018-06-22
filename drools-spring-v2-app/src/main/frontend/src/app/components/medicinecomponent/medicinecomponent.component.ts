import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MedicineComponent } from '../../model/medicine-component';
import { ComponentService } from '../../services/component.service';
declare var $ : any;

@Component({
  selector: 'app-medicinecomponent',
  templateUrl: './medicinecomponent.component.html',
  styleUrls: ['./medicinecomponent.component.css']
})
export class MedicinecomponentComponent implements OnInit {

  rForm : FormGroup;
  updateForm : FormGroup;
  post:any;
  error:string ="Polje je obavezno!";
  content : string;
  medicineComponent : MedicineComponent;
  componentForUpdate : MedicineComponent;
  medicineComponents : MedicineComponent[];

  constructor(private fb:FormBuilder, private componentService : ComponentService) {
    this.rForm = fb.group({
      'content':[null, Validators.required]
    });
    this.updateForm = fb.group({
      'content':[null, Validators.required]
    });
   }

  ngOnInit() {
    this.componentService.getComponents().subscribe(
      res => this.medicineComponents = res
    ), err => this.errorHandle(err);
  }

  addComponent(post){
    this.content = post.content; 
    let mc = new MedicineComponent(this.content);
    this.componentService.addComponent(mc).subscribe(
      res => {
        this.medicineComponent = res;
        this.medicineComponents.push(this.medicineComponent);
      }
    ), err => this.errorHandle(err);   
  }

  deleteComponent(medicineComponent : MedicineComponent){
    this.componentService.deleteComponent(medicineComponent.id).subscribe(
      res => {
        for(let i in this.medicineComponents){
          if(this.medicineComponents[i].id == medicineComponent.id){
            this.medicineComponents.splice(Number(i), 1);
          }
        }
      }
    ), err => this.errorHandle(err); 
  }

  updateComponent(mc : MedicineComponent){
    this.componentForUpdate = mc;
    this.updateForm.controls['content'].setValue(mc.title);
  }

  updateComponentValue(post){
    this.componentForUpdate.title = post.content;
    this.componentService.updateComponent(this.componentForUpdate).subscribe(
      res => console.log(res)
    ), err => this.errorHandle(err);
    $('#updateComponent').modal("toggle");
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
