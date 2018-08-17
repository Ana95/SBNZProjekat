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

import drools.spring.example.facts.IngredientAllergy;
import drools.spring.example.facts.MedicamentAllergy;
import drools.spring.example.facts.Patient;
import drools.spring.example.services.IngredientAllergyService;
import drools.spring.example.services.MedicamentAllergyService;
import drools.spring.example.services.PatientService;

@RestController
@RequestMapping(value = "/api")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicamentAllergyService medicamentAllergyService;
	
	@Autowired
	private IngredientAllergyService ingredientAllergyService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/patients",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) throws Exception{
		Patient saved = new Patient();
		saved.setPatientId(patient.getPatientId());
		saved.setName(patient.getName());
		saved.setSurname(patient.getSurname());
		saved.setAge(patient.getAge());
		saved = patientService.save(saved);
		return new ResponseEntity<Patient>(saved, HttpStatus.CREATED);
		
	}

	@CrossOrigin
	@RequestMapping(
		value = "/patients",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) throws Exception{
		Patient saved = patientService.findOne(patient.getId());
		if(saved == null){
			return new ResponseEntity<Patient>(HttpStatus.BAD_REQUEST);
		}
		saved.setPatientId(patient.getPatientId());
		saved.setName(patient.getName());
		saved.setSurname(patient.getSurname());
		saved.setAge(patient.getAge());
		saved = patientService.save(saved);
		return new ResponseEntity<Patient>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/patients/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Patient> getPatient(@PathVariable Long id){
		Patient patient = patientService.findOne(id);
		if(patient == null){
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/patients",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Patient>> getPatients(){
		ArrayList<Patient> patients = (ArrayList<Patient>) patientService.findAll();
		return new ResponseEntity<ArrayList<Patient>>(patients, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/patients/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deletePatientById(@PathVariable Long id) throws Exception{
		Patient patient = patientService.findOne(id);
		if(patient == null){
			return new ResponseEntity<String>("Patient not deleted!", HttpStatus.BAD_REQUEST);
		}	
		ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findByPatientId(id);
		if(!medicamentAllergies.isEmpty()){
			medicamentAllergyService.delete(medicamentAllergies);
		}
		
		ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findByPatientId(id);
		if(!ingredientAllergies.isEmpty()){
			ingredientAllergyService.delete(ingredientAllergies);
		}
		patientService.delete(id);
		return new ResponseEntity<String>("Patient deleted!", HttpStatus.OK);
	}
	
}