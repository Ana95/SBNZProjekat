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
import drools.spring.example.services.IngredientAllergyService;
import drools.spring.example.services.IngredientService;
import drools.spring.example.services.MedicamentService;

@RestController
@RequestMapping(value = "/api")
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private IngredientAllergyService ingredientAllergyService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) throws Exception{
		Ingredient saved = new Ingredient();
		saved.setName(ingredient.getName());
		saved.setMedicament(ingredient.getMedicament());
		saved = ingredientService.save(saved);
		return new ResponseEntity<Ingredient>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) throws Exception{
		Ingredient saved = ingredientService.findOne(ingredient.getId());
		if(saved == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		saved.setName(ingredient.getName());
		saved = ingredientService.save(saved);
		
		ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findByIngredientId(ingredient.getId());
		for (IngredientAllergy ingredientAllergy : ingredientAllergies) {
			ingredientAllergy.setIngredientName(ingredient.getName());
			ingredientAllergyService.save(ingredientAllergy);
		}
		return new ResponseEntity<Ingredient>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Ingredient>> getIngredients(){
		ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) ingredientService.findAll();
		return new ResponseEntity<ArrayList<Ingredient>>(ingredients, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients",
		params = "medicamentId",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Ingredient>> getIngredientsByMedicament(@RequestParam Long medicamentId){
		Medicament medicament = medicamentService.findOne(medicamentId);
		if(medicament == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) ingredientService.findByMedicament(medicament);
		return new ResponseEntity<ArrayList<Ingredient>>(ingredients, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id){
		Ingredient ingredient = ingredientService.findOne(id);
		if(ingredient == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/ingredients/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteIngredient(@PathVariable Long id) throws Exception{
		Ingredient ingredient = ingredientService.findOne(id);
		if(ingredient != null){
			ArrayList<IngredientAllergy> ingredientAllergies = (ArrayList<IngredientAllergy>) ingredientAllergyService.findByIngredientId(id);
			if(!ingredientAllergies.isEmpty()){
				ingredientAllergyService.delete(ingredientAllergies);
			}
			ingredientService.delete(id);
			return new ResponseEntity<String>("Ingredient deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Ingredient not deleted!", HttpStatus.NOT_FOUND);
	}
	
}