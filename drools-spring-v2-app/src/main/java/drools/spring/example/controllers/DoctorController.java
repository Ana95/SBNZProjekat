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

import drools.spring.example.facts.Doctor;
import drools.spring.example.services.DoctorService;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) throws Exception{
		Doctor saved = new Doctor();
		saved.setUsername(doctor.getUsername());
		saved.setPassword(doctor.getPassword());
		saved.setName(doctor.getName());
		saved.setSurname(doctor.getSurname());
		saved.setRole(doctor.getRole());
		saved.setInstitution(doctor.getInstitution());
		saved = doctorService.save(saved);
		return new ResponseEntity<Doctor>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) throws Exception{
		Doctor saved = doctorService.findOne(doctor.getId());
		if(saved == null){
			return new ResponseEntity<Doctor>(HttpStatus.BAD_REQUEST);
		}
		saved.setUsername(doctor.getUsername());
		saved.setPassword(doctor.getPassword());
		saved.setName(doctor.getName());
		saved.setSurname(doctor.getSurname());
		saved.setRole(doctor.getRole());
		saved.setInstitution(doctor.getInstitution());
		saved = doctorService.save(saved);
		return new ResponseEntity<Doctor>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Doctor> getDoctor(@PathVariable Long id){
		Doctor doctor = doctorService.findOne(id);
		if(doctor == null){
			return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Doctor>> getDoctors(){
		ArrayList<Doctor> doctors = (ArrayList<Doctor>) doctorService.findAll();
		return new ResponseEntity<ArrayList<Doctor>>(doctors, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Doctor> deleteDoctorById(@PathVariable Long id) throws Exception{
		Doctor doctor = doctorService.findOne(id);
		if(doctor != null){
			doctorService.delete(id);
			return new ResponseEntity<Doctor>(HttpStatus.OK);
		}
		return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
	}
	
}