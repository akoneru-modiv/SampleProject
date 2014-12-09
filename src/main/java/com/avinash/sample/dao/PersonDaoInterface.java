package com.avinash.sample.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.avinash.sample.vo.Person;

public interface PersonDaoInterface {

	Person addPerson(Person person) throws Exception;
	Person updatePerson(Person person) throws Exception;
	List<Person> listPersons() ;
	boolean removePerson(Person person) throws Exception;
}
