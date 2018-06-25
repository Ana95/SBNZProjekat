import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  readonly URL : string = 'http://localhost:8080/login';

  constructor(private http : HttpClient) { 

  }

  getRole(username, password){
    let param = new HttpParams().set('username', username).set('password', password);
    return this.http.get<any>(this.URL, { params : param })

  }
}
