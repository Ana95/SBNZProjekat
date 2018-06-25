import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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
  
  constructor(private route: ActivatedRoute, private router: Router) {

   }

  ngOnInit() {
    $('#menu_id').on('click', function () {
      $('#sidebar-wrapper').toggleClass('active');
    });
  }

  logOut(){
    this.router.navigate(['/login']);
  }

}
