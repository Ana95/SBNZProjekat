import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { HttpErrorResponse } from '@angular/common/http';
declare var $:any;

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {

  menu: string = "default";
  image_path : string = "assets/img/pillImage.png";
  image_doctor : string = "assets/img/doctor.png";
  image_disease : string = "assets/img/disease.png";
  image_report : string = "assets/img/report1.png";
  image_patient : string = "assets/img/patientImg1.png";
  image_diagnosis : string = "assets/img/diagnosis1.png";
  
  constructor(private router: Router, private loginService : LoginService) {

   }

  ngOnInit() {
    $('#menu_id').on('click', function () {
      $('#sidebar-wrapper').toggleClass('active');
    });
  }

  logOut(){
    this.loginService.logout().subscribe(
      res => {
        $("#logoutModal").modal("toggle");
        this.router.navigate(['/login']);
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
