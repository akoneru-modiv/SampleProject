package com.avinash.sample.serviceTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import com.avinash.sample.dao.PersonDaoInterface;
import com.avinash.sample.service.PersonServiceImpl;
import com.avinash.sample.resource.reponse.ResponseBase;
import com.avinash.sample.vo.Address;
import com.avinash.sample.vo.Person;
import com.avinash.sample.vo.Telephone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonServiceTest {
	
	public final List<Person> personList = new ArrayList<>();
	
	public final Person person = new Person();
	
	public final Person person1 = new Person();
	
	public String personJson = "";
	
	@InjectMocks
	PersonServiceImpl personService;
	
	@Mock
	private PersonDaoInterface personDao;
	
	@Before
	public void initPersonList(){
		MockitoAnnotations.initMocks(this);
		person.setId(1);
		person.setName("Avinash");
		person.setEmail("avinash11@gmail.com");
		
		Address address = new Address();
		address.setAddId(1);
		address.setAddress("Suite 501");
		address.setStreet("24 Farnsworth St");
		address.setState("MA");
		address.setZip("02110");
		address.setType("Home");
		
		person.getAddress().add(address);
		
		Telephone tel = new Telephone();
		tel.setTelId(1);
		tel.setTelNumber("1234567890");
		tel.setType("Home");
		
		person.getTelPhoneNumber().add(tel);
		
		personList.add(person);
		
		person1.setId(2);
		person.setName("Avinash");
		person.setEmail("avinash1@gmail.com");
		
		Address address1 = new Address();
		address.setAddId(1);
		address.setAddress("Suite 502");
		address.setStreet("28 Farnsworth St");
		address.setState("MA");
		address.setZip("02110");
		address.setType("Home");
		
		person1.getAddress().add(address1);
		
		Telephone tel1 = new Telephone();
		tel.setTelId(1);
		tel.setTelNumber("9999999999");
		tel.setType("Home");
		
		person.getTelPhoneNumber().add(tel1);
	}
	
	@Test
	public void testCreate() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {
			personJson = mapper.writeValueAsString(person);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(personDao.addPerson(any(Person.class))).thenReturn(person);
		when(personDao.listPersons()).thenReturn(personList);
		ResponseBase response = personService.addPerson(person);
		Assert.assertNull(response.getPerson());
	}
	
	@Test
	public void testCreateFail() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {
			personJson = mapper.writeValueAsString(person1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(personDao.addPerson(any(Person.class))).thenReturn(person1);
		when(personDao.listPersons()).thenReturn(personList);
		ResponseBase response = personService.addPerson(person1);
		Assert.assertNotNull(response.getPerson().get(0));
	}
	
	
	@Test
	public void testList() throws Exception{
		when(personDao.listPersons()).thenReturn(personList);
		ResponseBase response = personService.getListOfPersons();
		List<Person> list = response.getPerson(); 
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testUpdate() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {
			personJson = mapper.writeValueAsString(person);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(personDao.updatePerson(any(Person.class))).thenReturn(person);
		when(personDao.listPersons()).thenReturn(personList);
		ResponseBase response = personService.updatePerson(person);
		Assert.assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUpdateFail() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		try {
			personJson = mapper.writeValueAsString(person1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(personDao.updatePerson(any(Person.class))).thenReturn(person1);
		when(personDao.listPersons()).thenReturn(personList);
		ResponseBase response = personService.updatePerson(person1);
		Assert.assertFalse(response.isSuccess());
	}

	@Test
	public void testRemove() throws Exception{
		when(personDao.listPersons()).thenReturn(personList);
		when(personDao.removePerson(any(Person.class))).thenReturn(true);
		ResponseBase response = personService.removePerson(1);
		Assert.assertTrue(response.isSuccess());
	}
	
	@Test
	public void testRemoveFail() throws Exception{
		when(personDao.listPersons()).thenReturn(personList);
		when(personDao.removePerson(any(Person.class))).thenReturn(true);
		ResponseBase response = personService.removePerson(10);
		Assert.assertFalse(response.isSuccess());
	}
}
