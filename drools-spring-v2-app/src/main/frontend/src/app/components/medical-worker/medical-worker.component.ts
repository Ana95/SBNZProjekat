import { Component, OnInit } from '@angular/core';
declare var $ : any;

@Component({
  selector: 'app-medical-worker',
  templateUrl: './medical-worker.component.html',
  styleUrls: ['./medical-worker.component.css']
})
export class MedicalWorkerComponent implements OnInit {

  menu: string = "default";
  image_patient : string = "assets/img/patient.png";
  image_diagnosis : string = "assets/img/diagnosis1.png";
  image_cure : string = "assets/img/cure.png";
  image_report : string = "assets/img/report1.png";

  constructor() { }

  ngOnInit() {
    $('#menu_id').on('click', function () {
      $('#sidebar-wrapper').toggleClass('active');
    });
  }

}
