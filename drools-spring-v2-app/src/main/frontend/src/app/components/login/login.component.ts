import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { ROLE } from '../../model/role.enum';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : FormGroup;
  error:string ="Polje je obavezno!";
  user : User;

  constructor(private fb:FormBuilder, private loginService : LoginService, private router : Router, private userService : UserService) {
    this.loginForm = fb.group({
      'username':[null, Validators.required],
      'password': [null, Validators.required]
    });
  }

  ngOnInit() {
  }

  login(data : any){
    let username = data.username;
    let password = data.password;
    this.userService.getUsers().subscribe(
      res => {
        let user:User;
        for(let i of res){
          if(i.username == username && i.password == password){
            user = i;
          }
        }
        if(user.role.toString() == ROLE[ROLE.ADMIN]){
          this.router.navigate(['/administrator']);
        }else{
          console.log(user.role);
          this.router.navigate(['/doctor']);
        }
      }
    ), err => this.errorHandle(err);
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
