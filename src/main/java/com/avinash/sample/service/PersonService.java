package com.avinash.sample.service;

import java.io.IOException;
import java.util.List;

import com.avinash.sample.resource.reponse.ResponseBase;
import com.avinash.sample.vo.Person;


public interface PersonService {
	
	ResponseBase getListOfPersons();
	ResponseBase removePerson(long id);
	ResponseBase addPerson(Person person);
	ResponseBase updatePerson(Person person) ;
}
