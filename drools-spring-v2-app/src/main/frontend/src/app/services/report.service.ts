import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Report } from '../model/report';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { 

  }

  getReports(){
    return this.http.get<Report[]>("/api/reports");
  }
}
