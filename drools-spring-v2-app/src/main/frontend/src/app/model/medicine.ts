import { MedicineComponent } from '../model/medicine-component';
import { MEDICINETYPE } from '../model/medicinetype.enum';

export class Medicine {
    id : number;
    title : string;
    components : MedicineComponent[];
    type : MEDICINETYPE;
    medicineType : string;
    constructor(title : string, components : MedicineComponent[], type : MEDICINETYPE){
        this.title = title;
        this.components = components;
        this.type = type;
    }
}
