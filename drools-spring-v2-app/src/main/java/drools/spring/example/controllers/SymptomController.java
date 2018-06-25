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

import drools.spring.example.facts.Symptom;
import drools.spring.example.services.SymptomService;

@RestController
@RequestMapping(value = "/symptoms")
public class SymptomController {
	
	@Autowired
	private SymptomService symptomService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) throws Exception{
		System.out.println(symptom.getIsSpecific());
		Symptom saved = new Symptom();
		saved.setTitle(symptom.getTitle());
		saved.setIsSpecific(symptom.getIsSpecific());
		saved = symptomService.save(saved);
		return new ResponseEntity<Symptom>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Symptom> updateSymptom(@RequestBody Symptom sympotm) throws Exception{
		Symptom saved = symptomService.findOne(sympotm.getId());
		if(saved == null){
			return new ResponseEntity<Symptom>(HttpStatus.BAD_REQUEST);
		}
		saved.setTitle(sympotm.getTitle());
		saved.setIsSpecific(sympotm.getIsSpecific());
		saved = symptomService.save(saved);
		return new ResponseEntity<Symptom>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
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
		method = RequestMethod.GET,
		produces =MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Symptom>> getSymptoms(){
		ArrayList<Symptom> symptoms = (ArrayList<Symptom>) symptomService.findAll();
		return new ResponseEntity<ArrayList<Symptom>>(symptoms, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Symptom> deleteSymptomById(@PathVariable Long id) throws Exception{
		Symptom symptom = symptomService.findOne(id);
		if(symptom != null){
			symptomService.delete(id);
			return new ResponseEntity<Symptom>(HttpStatus.OK);
		}
		return new ResponseEntity<Symptom>(HttpStatus.NOT_FOUND);
	}

}
