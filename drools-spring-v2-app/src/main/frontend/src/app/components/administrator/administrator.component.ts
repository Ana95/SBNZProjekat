import { Component, OnInit } from '@angular/core';
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
  
  constructor() { }

  ngOnInit() {
    $('#menu_id').on('click', function () {
      $('#sidebar-wrapper').toggleClass('active');
    });
  }

}
