package com.avinash.sample.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="person")
public class Person {
	
	private long id;
	@NotEmpty
	private String name;
	@NotEmpty
	@Email
	private String email;
	/*private Map<String, String> telPhoneNumbers = new HashMap<String, String>();
	private Map<String, Address> adress1 = new HashMap<String, Address>();*/
	private Set<Address> address = new HashSet<Address>();
	private Set<Telephone> telPhoneNumber = new HashSet<Telephone>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(fetch=FetchType.EAGER, mappedBy="person",cascade = {CascadeType.ALL}, orphanRemoval=true)
	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}
	
	public void addAddress(Address add){
		add.setPerson(this);
		this.address.add(add);
	}

	@OneToMany(fetch=FetchType.EAGER, mappedBy="person", cascade = {CascadeType.ALL}, orphanRemoval=true)
	public Set<Telephone> getTelPhoneNumber() {
		return telPhoneNumber;
	}
	
	public void addTelPhoneNumber(Telephone phone){
		phone.setPerson(this);
		this.telPhoneNumber.add(phone);
	}

	public void setTelPhoneNumber(Set<Telephone> telPhoneNumber) {
		this.telPhoneNumber = telPhoneNumber;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", email=" + email
				+ ", address=" + address + ", telPhoneNumber=" + telPhoneNumber
				+ "]";
	}
	
}
