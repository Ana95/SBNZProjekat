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

import drools.spring.example.facts.Administrator;
import drools.spring.example.services.AdministratorService;

@RestController
@RequestMapping(value = "/administrators")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Administrator> createAdministrator(@RequestBody Administrator administrator) throws Exception{
		Administrator saved = new Administrator();
		saved.setUsername(administrator.getUsername());
		saved.setPassword(administrator.getPassword());
		saved.setName(administrator.getName());
		saved.setSurname(administrator.getSurname());
		saved.setRole(administrator.getRole());
		saved = administratorService.save(saved);
		return new ResponseEntity<Administrator>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Administrator> updateAdministrator(@RequestBody Administrator administrator) throws Exception{
		Administrator saved = administratorService.findOne(administrator.getId());
		if(saved == null){
			return new ResponseEntity<Administrator>(HttpStatus.BAD_REQUEST);
		}
		saved.setUsername(administrator.getUsername());
		saved.setPassword(administrator.getPassword());
		saved.setName(administrator.getName());
		saved.setSurname(administrator.getSurname());
		saved.setRole(administrator.getRole());
		saved = administratorService.save(saved);
		return new ResponseEntity<Administrator>(saved, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Administrator> getAdministrator(@PathVariable Long id){
		Administrator saved = administratorService.findOne(id);
		if(saved == null){
			return new ResponseEntity<Administrator>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Administrator>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Administrator>> getAdministrators(){
		ArrayList<Administrator> administrators = (ArrayList<Administrator>) administratorService.findAll();
		return new ResponseEntity<ArrayList<Administrator>>(administrators, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Administrator> deleteAdministratorById(@PathVariable Long id) throws Exception{
		Administrator saved = administratorService.findOne(id);
		if(saved != null){
			administratorService.delete(id);
			return new ResponseEntity<Administrator>(HttpStatus.OK);
		}
		return new ResponseEntity<Administrator>(HttpStatus.NOT_FOUND);
	}
	
}
