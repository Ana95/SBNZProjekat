import { ROLE } from '../model/role.enum';
import { User } from '../model/user';

export class Administrator extends User{
    constructor(username : string, passsword : string, name : string, surname : string, email : string){
        super(username, passsword, name, surname, email, ROLE.ADMIN);
    }
}
