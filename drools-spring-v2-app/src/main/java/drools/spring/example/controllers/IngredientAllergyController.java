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
import drools.spring.example.facts.Patient;
import drools.spring.example.services.IngredientAllergyService;
import drools.spring.example.services.IngredientService;
import drools.spring.example.services.PatientService;

@RestController
@RequestMapping("/api")
public class IngredientAllergyController {

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private IngredientAllergyService ingredientAllergyService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredientAllergies",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<IngredientAllergy> addIngredientAllergy(@RequestBody IngredientAllergy ingredientAllergy) throws Exception{
		Ingredient ingredient = ingredientService.findOne(ingredientAllergy.getIngredientId());
		if(ingredient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Patient patient = patientService.findOne(ingredientAllergy.getPatientId());
		if(patient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		IngredientAllergy saved = new IngredientAllergy();
		saved.setIngredientId(ingredient.getId());
		saved.setIngredientName(ingredient.getName());
		saved.setPatientId(patient.getId());
		saved = ingredientAllergyService.save(saved);
		return new ResponseEntity<IngredientAllergy>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredientAllergies",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<IngredientAllergy>> getIngredientAllergies(){
		ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findAll();
		return new ResponseEntity<ArrayList<IngredientAllergy>>(ingredientAllergies, HttpStatus.OK);
		
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredientAllergies",
		params = "patientId",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
		
	)
	public ResponseEntity<ArrayList<IngredientAllergy>> getIngredientAllergyByPatientId(@RequestParam Long patientId){
		ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findByPatientId(patientId);
		return new ResponseEntity<ArrayList<IngredientAllergy>>(ingredientAllergies, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredientAllergies/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<IngredientAllergy> getIngredientAllergy(@PathVariable Long id){
		IngredientAllergy ingredientAllergy = ingredientAllergyService.findOne(id);
		if(ingredientAllergy == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<IngredientAllergy>(ingredientAllergy, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredientAllergies/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteIngredientAllergy(@PathVariable Long id) throws Exception{
		IngredientAllergy ingredientAllergy = ingredientAllergyService.findOne(id);
		if(ingredientAllergy != null){
			ingredientAllergyService.delete(id);
			return new ResponseEntity<String>("Ingredient allergy deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Ingredient allergy dont deleted!", HttpStatus.BAD_REQUEST);
	}
	
}
