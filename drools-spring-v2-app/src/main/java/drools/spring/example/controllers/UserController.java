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

import drools.spring.example.facts.User;
import drools.spring.example.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
		User saved = new User();
		saved.setUsername(user.getUsername());
		saved.setPassword(user.getPassword());
		saved.setName(user.getName());
		saved.setSurname(user.getSurname());
		saved = userService.save(user);
		return new ResponseEntity<User>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception{
		User saved = userService.findOne(user.getId());
		if(saved == null){
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		saved.setUsername(user.getUsername());
		saved.setPassword(user.getPassword());
		saved.setName(user.getName());
		saved.setSurname(user.getSurname());
		saved = userService.save(saved);
		return new ResponseEntity<User>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> getUser(@PathVariable Long id){
		User user = userService.findOne(id);
		if(user == null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<User>> getUsers(){
		ArrayList<User> users = (ArrayList<User>) userService.findAll();
		return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
		value = "/{id}",
		method = RequestMethod.DELETE
	)
	public ResponseEntity<User> deleteUserById(@PathVariable Long id) throws Exception{
		User user = userService.findOne(id);
		if(user != null){
			userService.delete(id);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
}
