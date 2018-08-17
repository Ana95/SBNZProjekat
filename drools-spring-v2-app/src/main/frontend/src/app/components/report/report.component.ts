import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Report } from '../../model/report';
import { ReportService } from '../../services/report.service'; 

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  image_report: string = "/assets/img/report1.png";
  reports: Report[];

  constructor(private reportService: ReportService) { 

  }

  ngOnInit() {
    this.reportService.getReports().subscribe(
      res => this.reports = res,
      err => this.errorHandle(err)
    )
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
