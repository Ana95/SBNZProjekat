import { User } from '../model/user';
import { ROLE } from '../model/role.enum';

export class Doctor extends User{
    institution : string;
    constructor(username : string, password : string, name : string, surname : string, email : string, institution : string){
        super(username, password, name, surname, email, ROLE.DOCTOR);
        this.institution = institution;
    }
}
