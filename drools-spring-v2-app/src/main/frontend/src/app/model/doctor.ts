import { User } from '../model/user';
import { Role } from '../model/role.enum';

export class Doctor extends User{
    role : Role;
    institution : string;
    constructor(username : string, password : string, name : string, surname : string, institution : string){
        super(username, password, name, surname);
        this.role = Role.DOCTOR;
        this.institution = institution;
    }
}
