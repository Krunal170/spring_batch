package com.example.chapter7.domain;

import java.util.List;

public class CustomerWithTran {

	private String firstName;
	private String middleInitial;
	private String lastName;
	private String addressNumber;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private List<Transaction> transactions;
	
	public CustomerWithTran() {
	
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "CustomerWithTran [firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName="
				+ lastName + ", addressNumber=" + addressNumber + ", street=" + street + ", city=" + city + ", state="
				+ state + ", zipCode=" + zipCode + ", transactions=" + transactions + "]";
	}
	
    
}
