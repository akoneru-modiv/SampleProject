package com.avinash.sample.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.avinash.sample.resource.reponse.ResponseBase;
import com.avinash.sample.service.PersonService;
import com.avinash.sample.vo.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

	private static final Logger logger = Logger.getLogger(PersonController.class.getName());
	
	@Autowired
	PersonService personService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<Person> getPersonList() {
		Person person = new Person();
		ResponseBase response = personService.getListOfPersons();
		List<Person> personList = response.getPerson();
		return personList;
	}
	
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public @ResponseBody ResponseEntity<Person> createPerson(@Valid @RequestBody Person person){
		ResponseBase response = personService.addPerson(person);
		if(response.isSuccess())
			person = response.getPerson().get(0);
		else{
			return new ResponseEntity<Person>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/update/{id}", method= RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Person> updatePerson(@PathVariable long id, @Valid @RequestBody Person person){
		person.setId(id);
		logger.info("person to update " + person.toString());
		ResponseBase response = personService.updatePerson(person);
		if(response.isSuccess())
			person = response.getPerson().get(0);
		else{
			return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Person>(person, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/remove/{id}", method = RequestMethod.DELETE )
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void removePerson(@PathVariable long id ){
		personService.removePerson(id);
	}
}
