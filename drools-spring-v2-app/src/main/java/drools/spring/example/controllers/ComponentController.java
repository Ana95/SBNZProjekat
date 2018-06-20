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

import drools.spring.example.facts.Component;
import drools.spring.example.services.ComponentService;

@RestController
@RequestMapping(value = "/components")
public class ComponentController {

	@Autowired
	private ComponentService componentService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Component> createComponent(@RequestBody Component component) throws Exception{
		Component saved = new Component();
		saved.setTitle(component.getTitle());
		saved = componentService.save(saved);
		return new ResponseEntity<Component>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Component> updateComponent(@RequestBody Component component) throws Exception{
		Component saved = componentService.findOne(component.getId());
		if(saved == null){
			return new ResponseEntity<Component>(HttpStatus.BAD_REQUEST);
		}
		saved.setTitle(component.getTitle());
		saved = componentService.save(saved);
		return new ResponseEntity<Component>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Component> getComponent(@PathVariable Long id){
		Component component = componentService.findOne(id);
		if(component == null){
			return new ResponseEntity<Component>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Component>(component, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces =MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Component>> getComponents(){
		ArrayList<Component> components = (ArrayList<Component>) componentService.findAll();
		return new ResponseEntity<ArrayList<Component>>(components, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<Component> deleteComponentById(@PathVariable Long id) throws Exception{
		Component component = componentService.findOne(id);
		if(component != null){
			componentService.delete(id);
			return new ResponseEntity<Component>(HttpStatus.OK);
		}
		return new ResponseEntity<Component>(HttpStatus.NOT_FOUND);
	}
	
}
