import { ROLE } from '../model/role.enum'

export class User {
    id : number;
    username : string;
    password : string;
    name : string;
    surname : string;
    email : string;
    role : ROLE;
    constructor(username : string, password : string, name : string, surname : string, email : string, role : ROLE){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}
