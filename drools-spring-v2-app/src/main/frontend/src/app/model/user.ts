import { ROLE } from '../model/role.enum'

export class User {
    id : number;
    username : string;
    password : string;
    name : string;
    surname : string;
    role : ROLE;
    constructor(username : string, password : string, name : string, surname : string, role : ROLE){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
