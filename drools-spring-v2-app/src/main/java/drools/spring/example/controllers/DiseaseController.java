package drools.spring.example.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.Media;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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

import drools.spring.example.facts.Disease;
import drools.spring.example.facts.Symptom;
import drools.spring.example.services.DiseaseService;

@RestController
@RequestMapping(value = "/diseases")
public class DiseaseController {

	@Autowired
	private DiseaseService diseaseService;
	
	private final KieContainer kieContainer;
	
	@Autowired
    public DiseaseController(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) throws Exception{
		Disease saved = new Disease();
		saved.setTitle(disease.getTitle());
		saved.setSymptoms(disease.getSymptoms());
		saved.setType(disease.getType());
		saved = diseaseService.save(saved);
		return new ResponseEntity<Disease>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Disease> updateDisease(@RequestBody Disease disease) throws Exception{
		Disease saved = diseaseService.findOne(disease.getId());
		if(saved == null){
			return new ResponseEntity<Disease>(HttpStatus.BAD_REQUEST);
		}
		saved.setTitle(disease.getTitle());
		saved.setSymptoms(disease.getSymptoms());
		saved.setType(disease.getType());
		saved = diseaseService.save(saved);
		return new ResponseEntity<Disease>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Disease> getDisease(@PathVariable Long id){
		Disease disease = diseaseService.findOne(id);
		if(disease == null){
			return new ResponseEntity<Disease>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Disease>(disease, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Disease>> getDiseases(){
		ArrayList<Disease> diseases = (ArrayList<Disease>) diseaseService.findAll();
		return new ResponseEntity<ArrayList<Disease>>(diseases, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Disease> deleteDiseaseById(@PathVariable Long id) throws Exception{
		Disease disease = diseaseService.findOne(id);
		if(disease != null){
			diseaseService.delete(id);
			return new ResponseEntity<Disease>(HttpStatus.OK);
		}
		return new ResponseEntity<Disease>(HttpStatus.NOT_FOUND);
	}
	@CrossOrigin
	@RequestMapping(
		value = "/rules",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Disease> getDiseaseByRules(@RequestBody Disease disease){
		ArrayList<Disease> diseases = (ArrayList<Disease>) diseaseService.findAll();
		Set<Symptom> symptoms = disease.getSymptoms();
 		KieSession kieSession = kieContainer.newKieSession();
		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		for (Symptom symptom : symptoms) {
			kieSession.insert(symptom);
		}
		for (Disease d : diseases) {
			kieSession.insert(d);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		Collections.sort(diseases);
		return new ResponseEntity<Disease>(diseases.get(0), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/newRules",
		method = RequestMethod.POST,
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Disease>> getRDisease(@RequestBody Disease disease){
		ArrayList<Disease> diseases = (ArrayList<Disease>) diseaseService.findAll();
		Set<Symptom> symptoms = disease.getSymptoms();
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		for (Symptom symptom : symptoms) {
			kieSession.insert(symptom);
		}
		for (Disease d : diseases) {
			kieSession.insert(d);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		Collections.sort(diseases);
		return new ResponseEntity<ArrayList<Disease>>(diseases, HttpStatus.OK);
	}
	
}