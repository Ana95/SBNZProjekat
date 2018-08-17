import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http : HttpClient) { 

  }

  login(username, password){
    let param = new HttpParams().set('username', username).set('password', password);
    return this.http.get<any>("/api/login", { params : param })
  }

  logout(){
    localStorage.removeItem("currentUser");
    return this.http.post("/api/logout", {}, {responseType: 'text'});
  }

}
