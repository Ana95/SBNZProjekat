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

import drools.spring.example.facts.Diagnose;
import drools.spring.example.facts.DiagnoseMedicament;
import drools.spring.example.facts.DiagnoseSymptom;
import drools.spring.example.facts.Record;
import drools.spring.example.services.DiagnoseMedicamentService;
import drools.spring.example.services.DiagnoseService;
import drools.spring.example.services.DiagnoseSymptomService;
import drools.spring.example.services.IllnessService;

@RestController
@RequestMapping( value = "/api")
public class DiagnoseController {
	
	@Autowired
	private IllnessService illnessService;
	
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;
	
	@Autowired
	private DiagnoseSymptomService diagnoseSymptomService;
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses",
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<String> setDiagnose(@RequestBody Record record, HttpServletRequest request) throws Exception{
		String allergies = illnessService.setDiagnose(record, request);
		
		if(allergies.equals("Pacijent je alergičan na: ")){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<String>(allergies, HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Diagnose>> getDiagnoses(){
		ArrayList<Diagnose> diagnoses = (ArrayList<Diagnose>) diagnoseService.findAll();
		return new ResponseEntity<ArrayList<Diagnose>>(diagnoses, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses",
		params = "patientId",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Diagnose>> getDiagnosesByPatientId(@RequestParam Long patientId){
		ArrayList<Diagnose> diagnoses = (ArrayList<Diagnose>) diagnoseService.findByPatientId(patientId);
		return new ResponseEntity<ArrayList<Diagnose>>(diagnoses, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<String> deleteDiagnose(@PathVariable Long id) throws Exception{
		Diagnose diagnose = diagnoseService.findOne(id);
		if(diagnose == null){
			return new ResponseEntity<String>("Diagnose not deleted!", HttpStatus.BAD_REQUEST);
		}
		diagnoseService.delete(id);
		return new ResponseEntity<String>("Diagnose deleted!", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses/{id}/medicaments",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<DiagnoseMedicament>> getDiagnoseMedicaments(@PathVariable Long id){
		Diagnose diagnose = diagnoseService.findOne(id);
		if(diagnose == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<DiagnoseMedicament> diagnoseMedicaments = (ArrayList<DiagnoseMedicament>) diagnoseMedicamentService.findByDiagnoseId(id);
		return new ResponseEntity<ArrayList<DiagnoseMedicament>>(diagnoseMedicaments, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/diagnoses/{id}/symptoms",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<DiagnoseSymptom>> getDiagnoseSymptoms(@PathVariable Long id){
		Diagnose diagnose = diagnoseService.findOne(id);
		if(diagnose == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ArrayList<DiagnoseSymptom> diagnoseSymptoms = (ArrayList<DiagnoseSymptom>) diagnoseSymptomService.findByDiagnoseId(id);
		return new ResponseEntity<ArrayList<DiagnoseSymptom>>(diagnoseSymptoms, HttpStatus.OK);
	}

}
