import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  readonly URL : string = 'http://localhost:8080/users';

  constructor(private http : HttpClient) { 

  }

  getUsers(){
    return this.http.get<User[]>(this.URL);
  }
}
