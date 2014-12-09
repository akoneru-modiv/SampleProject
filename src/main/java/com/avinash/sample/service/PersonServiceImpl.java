package com.avinash.sample.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.avinash.sample.dao.PersonDaoInterface;
import com.avinash.sample.dao.exception.DataAccessException;
import com.avinash.sample.person.cache.PersonCache;
import com.avinash.sample.resource.reponse.ResponseBase;
import com.avinash.sample.vo.Person;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.ArraysCompat;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PersonServiceImpl implements PersonService{
	
	Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());
	
	@Autowired
	@Qualifier("hibernateDao")
	PersonDaoInterface personDaoHibernateImpl;
	
	
	public void reload(){
		List<Person> personList = personDaoHibernateImpl.listPersons();
		logger.log(Level.INFO, "retrieved person list with size " + personList.size());
		PersonCache.setPersonList(personList);
	}

	@Override
	public ResponseBase getListOfPersons() {
		if(PersonCache.getPersonList().size() == 0){
			reload();
		}
		ResponseBase response = new ResponseBase();
		response.setSuccess(true);
		response.setPerson(PersonCache.getPersonList());
		return response;
	}

	@Override
	public ResponseBase addPerson(Person person) {
		ResponseBase response = new ResponseBase();
		try {
			if(!personExists(person.getId(), person.getEmail())){
				person = personDaoHibernateImpl.addPerson(person);
				reload();
				response.setSuccess(true);
				response.setPerson(Arrays.asList(person));
			}else{
				response.setDuplicate(true);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return response;
	}

	@Override
	public ResponseBase updatePerson(Person person)  {
		ResponseBase response = new ResponseBase();
		try {
			if(personExists(person.getId(), person.getEmail())){
				logger.log(Level.INFO, "Person to update {0}", new Object[]{person.toString()});
				person = personDaoHibernateImpl.updatePerson(person);
				reload();
				response.setSuccess(true);
				response.setPerson(Arrays.asList(person));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());			
		}
		return response;
	}

	@Override
	public ResponseBase removePerson(long id) {
		ResponseBase response = new ResponseBase();
		Optional<Person> person = PersonCache.getPersonList().stream()
										.filter(vo -> {return vo.getId() == id;}).findFirst();
		try {
			if(person.isPresent())
				response.setSuccess(personDaoHibernateImpl.removePerson(person.get()));
			else{
				throw new NoSuchElementException("Person not found with id " + id);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unable to remove person with id " + id , e);
		}
		reload();
		return response;
	}
	
	private boolean personExists(long id, String email){
		Optional<Person> person = null ;
		if(id > 0){
			person = PersonCache.getPersonList().stream().filter(vo -> {return vo.getId() == id;}).findFirst();
		}else if(email != null){
			person = PersonCache.getPersonList().stream().filter(vo -> {return vo.getEmail().equalsIgnoreCase(email);}).findFirst();
		}
		return person.isPresent();
	}

	
}
