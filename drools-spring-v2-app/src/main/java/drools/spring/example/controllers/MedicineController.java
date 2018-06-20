package drools.spring.example.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.facts.Medicine;
import drools.spring.example.services.MedicineService;

@RestController
@RequestMapping(value = "/medicines")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) throws Exception{
		Medicine saved = new Medicine();
		saved.setTitle(medicine.getTitle());
		saved.setComponents(medicine.getComponents());
		saved.setType(medicine.getType());
		saved = medicineService.save(saved);
		return new ResponseEntity<Medicine>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Medicine> updateMedicine(@RequestBody Medicine medicine) throws Exception{
		Medicine saved = medicineService.findOne(medicine.getId());
		if(saved == null){
			return new ResponseEntity<Medicine>(HttpStatus.BAD_REQUEST);
		}
		saved.setTitle(medicine.getTitle());
		saved.setComponents(medicine.getComponents());
		saved.setType(medicine.getType());
		saved = medicineService.save(saved);
		return new ResponseEntity<Medicine>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Medicine> getMedicine(@PathVariable Long id){
		Medicine medicine = medicineService.findOne(id);
		if(medicine == null){
			return new ResponseEntity<Medicine>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Medicine>(medicine, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Medicine>> getMedicines(){
		ArrayList<Medicine> medicines = (ArrayList<Medicine>) medicineService.findAll();
		return new ResponseEntity<ArrayList<Medicine>>(medicines, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Medicine> deleteMedicineById(@PathVariable Long id) throws Exception{
		Medicine medicine = medicineService.findOne(id);
		if(medicine != null){
			medicineService.delete(id);
			return new ResponseEntity<Medicine>(HttpStatus.OK);
		}
		return new ResponseEntity<Medicine>(HttpStatus.NOT_FOUND);
	}
	
}
