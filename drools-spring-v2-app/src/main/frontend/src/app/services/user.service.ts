import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { 

  }

  getUsers(){
    return this.http.get<User[]>("/api/users");
  }

  addUser(user : User){
    return this.http.post<User>("/api/users", user);
  }

  updateUser(user : User){
    return this.http.put<User>("/api/users", {
      'id' : user.id,
      'username' : user.username,
      'password' : user.password,
      'name' : user.name,
      'surname' : user.surname,
      'email' : user.email
    })
  }

  deleteUser(userId : number){
    return this.http.delete("/api/users/" + userId, {responseType : 'text'});
  }
}
