package com.avinash.sample.person.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avinash.sample.vo.Person;

/**
 * 
 * @author akoneru
 *
 */
public class PersonCache {

	static List<Person> personList = new ArrayList<Person>();

	public static List<Person> getPersonList() {
		return personList;
	}

	public static void setPersonList(List<Person> personList) {
		PersonCache.personList = personList;
	}
}
