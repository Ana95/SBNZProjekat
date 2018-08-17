package drools.spring.example.controllers;

import java.util.ArrayList;
import java.util.List;

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

import drools.spring.example.facts.Ingredient;
import drools.spring.example.facts.Medicament;
import drools.spring.example.facts.MedicamentAllergy;
import drools.spring.example.services.IngredientService;
import drools.spring.example.services.MedicamentAllergyService;
import drools.spring.example.services.MedicamentService;

@RestController
@RequestMapping(value = "/api")
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private MedicamentAllergyService medicamentAllergyService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicaments",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Medicament> addMedicament(@RequestBody Medicament medicament) throws Exception{
		Medicament saved = new Medicament();
		saved.setName(medicament.getName());
		saved.setCategory(medicament.getCategory());
		saved.setIngredients(medicament.getIngredients());
		saved = medicamentService.save(saved);
		return new ResponseEntity<Medicament>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicaments",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Medicament> updateMedicament(@RequestBody Medicament medicament) throws Exception{
		Medicament saved = medicamentService.findOne(medicament.getId());
		if(saved == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		saved.setName(medicament.getName());
		saved.setCategory(medicament.getCategory());
		saved.setIngredients(medicament.getIngredients());
		saved = medicamentService.save(saved);
		
		ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findByMedicamentId(medicament.getId());
		if(!medicamentAllergies.isEmpty()){
			for (MedicamentAllergy medicamentAllergy : medicamentAllergies) {
				medicamentAllergy.setMedicamentName(medicament.getName());
				medicamentAllergyService.save(medicamentAllergy);
			}
		}
		return new ResponseEntity<Medicament>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicaments/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	private ResponseEntity<Medicament> getMedicament(@PathVariable Long id){
		Medicament medicament = medicamentService.findOne(id);
		if(medicament == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Medicament>(medicament, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicaments",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Medicament>> getMedicaments(){
		ArrayList<Medicament> medicaments = (ArrayList<Medicament>) medicamentService.findAll();
		return new ResponseEntity<ArrayList<Medicament>>(medicaments, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/medicaments/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteMedicament(@PathVariable Long id) throws Exception{
		Medicament medicament = medicamentService.findOne(id);
		if(medicament != null){
			ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) ingredientService.findByMedicament(medicament);
			if(!ingredients.isEmpty()){
				ingredientService.delete(ingredients);
			}
			
			ArrayList<MedicamentAllergy> medicamentAllergies = (ArrayList<MedicamentAllergy>) medicamentAllergyService.findByMedicamentId(id);
			if(!medicamentAllergies.isEmpty()){
				medicamentAllergyService.delete(medicamentAllergies);
			}
			medicamentService.delete(id);
			return new ResponseEntity<String>("Medicament deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Medicament not deleted!", HttpStatus.BAD_REQUEST);
	}

}
