package drools.spring.example.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
import drools.spring.example.services.IllnessService;
import drools.spring.example.services.SymptomService;

@RestController
@RequestMapping("/api")
public class IllnessController {
	
	@Autowired
	private IllnessService illnessService;
	
	@Autowired
	private SymptomService symptomService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Illness> createIllness(@RequestBody Illness illness) throws Exception{
		Illness saved = new Illness();
		saved.setIllness_type(illness.getIllness_type());
		saved.setName(illness.getName());
		saved.setSymptoms(illness.getSymptoms());
		saved = illnessService.save(saved);
		return new ResponseEntity<Illness>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses",
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Illness> updateIllness(@RequestBody Illness illness) throws Exception{
		Illness saved = illnessService.findOne(illness.getId());
		if(saved == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		saved.setIllness_type(illness.getIllness_type());
		saved.setName(illness.getName());
		saved.setSymptoms(illness.getSymptoms());
		saved = illnessService.save(saved);
		return new ResponseEntity<Illness>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Illness>> getIllnesses(){
		ArrayList<Illness> illnesses = (ArrayList<Illness>) illnessService.findAll();
		return new ResponseEntity<ArrayList<Illness>>(illnesses, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Illness> getIllness(@PathVariable Long id){
		Illness illness = illnessService.findOne(id);
		if(illness == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Illness>(illness, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteIllness(@PathVariable Long id) throws Exception{
		Illness illness = illnessService.findOne(id);
		if(illness == null){
			return new ResponseEntity<String>("Illness not deleted!", HttpStatus.BAD_REQUEST);
		}
		ArrayList<Symptom> symptoms = (ArrayList<Symptom>) symptomService.findByIllness(illness);
		if(!symptoms.isEmpty()){
			symptomService.delete(symptoms);
		}
		illnessService.delete(id);
		return new ResponseEntity<String>("Illness deleted!", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses/symptoms",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Symptom>> getIllnessSymptoms(@RequestBody Illness illness, HttpServletRequest request){
		illness = illnessService.findByName(illness.getName());
		if(illness == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<Symptom> symptoms = illnessService.getIllnessSymptoms(illness, request);
		if(symptoms.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ArrayList<Symptom>>(symptoms, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses/getOneIllness",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Illness>> getOneIllness(@RequestBody ArrayList<Symptom> symptoms, @RequestParam Long patientId, HttpServletRequest request){
		ArrayList<Illness> illnesses = illnessService.getOneIllness(symptoms, patientId, request);
		if(illnesses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		for (Illness illness : illnesses) {
			illness.getSymptoms().clear();
			illness.getSymptomsTerms().clear();
		}
		return new ResponseEntity<ArrayList<Illness>>(illnesses, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/illnesses/getAllIllness",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Illness>> getAllIllness(@RequestBody ArrayList<Symptom> symptoms, @RequestParam Long patientId, HttpServletRequest request){
		ArrayList<Illness> illnesses = illnessService.getAllIllness(symptoms, patientId, request);
		if(illnesses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		for (Illness illness : illnesses) {
			illness.getSymptoms().clear();
			illness.getSymptomsTerms().clear();
		}
		return new ResponseEntity<ArrayList<Illness>>(illnesses, HttpStatus.OK);
	}

}
