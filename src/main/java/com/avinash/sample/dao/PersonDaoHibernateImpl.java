package com.avinash.sample.dao;

import java.util.ArrayList;
import java.util.List;






import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.avinash.sample.vo.Address;
import com.avinash.sample.vo.Person;
import com.avinash.sample.vo.Telephone;

@Repository("hibernateDao")
public class PersonDaoHibernateImpl implements PersonDaoInterface {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Person addPerson(Person person) throws Exception {
		setupPerson(person);
		Long personId = (Long) sessionFactory.getCurrentSession()
													.save(person);
		person.setId(personId);
		return person;
	}
	

	@Override
	@Transactional
	public Person updatePerson(Person person) throws Exception {
		setupPerson(person);
		sessionFactory.getCurrentSession().update(person);
		return person;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Person> listPersons() {
		List<Person> personList = new ArrayList<Person>();
		personList = sessionFactory.getCurrentSession()
							.createQuery("from Person p")
								.list();
		return personList;
	}

	@Override
	@Transactional
	public boolean removePerson(Person person) throws Exception {
		sessionFactory.getCurrentSession().delete(person);
		return true;
	}

	private Person setupPerson(Person person){
		for(Telephone tel : person.getTelPhoneNumber()){
			person.addTelPhoneNumber(tel);
		}
		for(Address add : person.getAddress()){
			person.addAddress(add);
		}
		return person;
	}
}
