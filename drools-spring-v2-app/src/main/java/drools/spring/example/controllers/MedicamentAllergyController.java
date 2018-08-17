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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.IngredientAllergy;
import drools.spring.example.facts.Medicament;
import drools.spring.example.facts.MedicamentAllergy;
import drools.spring.example.facts.Patient;
import drools.spring.example.services.IngredientAllergyService;
import drools.spring.example.services.IngredientService;
import drools.spring.example.services.MedicamentAllergyService;
import drools.spring.example.services.MedicamentService;
import drools.spring.example.services.PatientService;

@RestController
@RequestMapping("/api")
public class MedicamentAllergyController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicamentAllergyService medicamentAllergyService;
	
	@Autowired
	private IngredientAllergyService ingredientAllergyService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicamentAllergies",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<MedicamentAllergy> addMedicamentAllergy(@RequestBody MedicamentAllergy medicamentAllergy) throws Exception{
		Medicament medicament = medicamentService.findOne(medicamentAllergy.getMedicamentId());
		if(medicament == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Patient patient = patientService.findOne(medicamentAllergy.getPatientId());
		if(patient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		MedicamentAllergy saved = new MedicamentAllergy();
		saved.setMedicamentId(medicament.getId());
		saved.setMedicamentName(medicament.getName());
		saved.setPatientId(patient.getId());
		saved = medicamentAllergyService.save(saved);
		return new ResponseEntity<MedicamentAllergy>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicamentAllergies",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<MedicamentAllergy>> getMedicamentAllergies(){
		ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findAll();
		return new ResponseEntity<ArrayList<MedicamentAllergy>>(medicamentAllergies, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicamentAllergies",
		params = "patientId",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
		
	)
	public ResponseEntity<ArrayList<MedicamentAllergy>> getMedicamentAllergyByPatientId(@RequestParam Long patientId){
		ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findByPatientId(patientId);
		return new ResponseEntity<ArrayList<MedicamentAllergy>>(medicamentAllergies, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicamentAllergies/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<MedicamentAllergy> getMedicamentAllergy(@PathVariable Long id){
		MedicamentAllergy medicamentAllergy = medicamentAllergyService.findOne(id);
		if(medicamentAllergy == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MedicamentAllergy>(medicamentAllergy, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicamentAllergies/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteMedicamentAllergy(@PathVariable Long id) throws Exception{
		MedicamentAllergy medicamentAllergy = medicamentAllergyService.findOne(id);
		if(medicamentAllergy != null){
			Medicament medicament = medicamentService.findOne(medicamentAllergy.getMedicamentId());
			if(!medicament.getIngredients().isEmpty()){
				for (Ingredient ingredient : medicament.getIngredients()) {
					System.out.println(ingredient.getId());
					IngredientAllergy ingredientAllergy = ingredientAllergyService.findByPatientIdAndIngredientId(medicamentAllergy.getPatientId(), ingredient.getId());
					if(ingredientAllergy != null){
						ingredientAllergyService.delete(ingredientAllergy.getId());
					}
				}
			}
			medicamentAllergyService.delete(id);
			
			return new ResponseEntity<String>("Medicament allergy deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Medicament allergy not deleted!", HttpStatus.BAD_REQUEST);
	}
	
}
