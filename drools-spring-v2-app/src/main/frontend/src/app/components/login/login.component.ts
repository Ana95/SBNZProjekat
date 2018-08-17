import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { ROLE } from '../../model/role.enum';
import { Router } from '@angular/router';
declare var $ : any;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : FormGroup;
  error:string ="Polje je obavezno!";
  user : User;

  constructor(private fb:FormBuilder, private loginService : LoginService, private router : Router) {
    this.loginForm = fb.group({
      'username':[null, Validators.required],
      'password': [null, Validators.required]
    });
  }

  ngOnInit() {
    
  }

  login(post){
    this.loginService.login(post.username, post.password).subscribe(
      (res) =>{
        this.user = res;
        localStorage.setItem("currentUser", JSON.stringify(this.user));
        console.log(this.user.role);
        if(this.user.role.toString() == ROLE[ROLE.ADMIN]){
          this.router.navigate(['../administrator']);
        }else{
          this.router.navigate(['../doctor']);
        }
      },(err) => this.errorHandle(err));
  }

  errorHandle(err: HttpErrorResponse){
    if(err.error instanceof Error){
      console.log("Client-side Error occured!");
    }else{
      console.log("Server-side Error occured!");
      console.log(err.message);
    }
    $('#error').modal("show");
  }

}
