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

import drools.spring.example.facts.Illness;
import drools.spring.example.facts.Symptom;
import drools.spring.example.facts.Symptom.Term;
import drools.spring.example.services.IllnessService;
import drools.spring.example.services.SymptomService;

@RestController
@RequestMapping(value = "/api")
public class SymptomController {
	
	@Autowired
	private SymptomService symptomService;
	
	@Autowired
	private IllnessService illnessService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) throws Exception{
		System.out.println(symptom.getId());
		Symptom saved = new Symptom();
		if(symptom.getTemperature() != null){
			if(symptom.getTemperature() >= 38 && symptom.getTemperature() < 40){
				symptom.setTerm(Term.TEMP_OVER_38);
			}
			if(symptom.getTemperature() >= 40 && symptom.getTemperature() <= 41){
				symptom.setTerm(Term.TEMP_BETWEEN_40_AND_41);
			}
		}
		saved.setTerm(symptom.getTerm());
		saved.setTemperature(symptom.getTemperature());
		saved.setHelper(symptom.getHelper());
		saved.setIsSpecific(symptom.getIsSpecific());
		saved.setIllness(symptom.getIllness());
		saved = symptomService.save(saved);
		return new ResponseEntity<Symptom>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Symptom> updateSymptom(@RequestBody Symptom symptom) throws Exception{
		Symptom saved = symptomService.findOne(symptom.getId());
		if(saved == null){
			return new ResponseEntity<Symptom>(HttpStatus.BAD_REQUEST);
		}
		if(symptom.getTemperature() != null){
			if(symptom.getTemperature() >= 38 && symptom.getTemperature() < 40){
				symptom.setTerm(Term.TEMP_OVER_38);
			}
			if(symptom.getTemperature() >= 40 && symptom.getTemperature() <= 41){
				symptom.setTerm(Term.TEMP_BETWEEN_40_AND_41);
			}
		}
		saved.setTerm(symptom.getTerm());
		saved.setTemperature(symptom.getTemperature());
		saved.setHelper(symptom.getHelper());
		saved.setIsSpecific(symptom.getIsSpecific());
		saved = symptomService.save(saved);
		return new ResponseEntity<Symptom>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Symptom> getSymptom(@PathVariable Long id){
		Symptom symptom = symptomService.findOne(id);
		if(symptom == null){
			return new ResponseEntity<Symptom>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Symptom>(symptom, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Symptom>> getSymptoms(){
		ArrayList<Symptom> symptoms = (ArrayList<Symptom>) symptomService.findAll();
		return new ResponseEntity<ArrayList<Symptom>>(symptoms, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms",
		params = "illnessId",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Symptom>> getSymptomsByIllness(@RequestParam Long illnessId){
		Illness illness = illnessService.findOne(illnessId);
		ArrayList<Symptom> symptoms = (ArrayList<Symptom>) symptomService.findByIllness(illness);
		return new ResponseEntity<ArrayList<Symptom>>(symptoms, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms",
		params = "illness_id",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Symptom>> getSortedSymptomsByIllness(@RequestParam Long illness_id){
		Illness illness = illnessService.findOne(illness_id);
		ArrayList<Symptom> symptoms = (ArrayList<Symptom>) symptomService.findByIllnessOrderByIsSpecificDesc(illness);
		return new ResponseEntity<ArrayList<Symptom>>(symptoms, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/symptoms/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteSymptomById(@PathVariable Long id) throws Exception{
		Symptom symptom = symptomService.findOne(id);
		if(symptom != null){
			symptomService.delete(id);
			return new ResponseEntity<String>("Symptom deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Symptom not deleted!", HttpStatus.NOT_FOUND);
	}

}
