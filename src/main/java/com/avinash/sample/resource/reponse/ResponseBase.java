package com.avinash.sample.resource.reponse;

import java.util.List;

import com.avinash.sample.vo.Person;

public class ResponseBase {

	private boolean duplicate;
	private List<Person> person;
	private boolean success;

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public List<Person> getPerson() {
		return person;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}

	
}
