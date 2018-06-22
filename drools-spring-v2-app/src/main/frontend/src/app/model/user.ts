export class User {
    id : number;
    username : string;
    password : string;
    name : string;
    surname : string;
    constructor(username : string, password : string, name : string, surname : string){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
