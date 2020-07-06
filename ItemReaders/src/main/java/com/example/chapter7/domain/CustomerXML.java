package com.example.chapter7.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlRootElement
public class CustomerXML {

	private String firstName;
	private String middleInitial;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private List<TransactionXML> transactions;
	
	public CustomerXML() {
	
	}

	public CustomerXML(String firstName, String middleInitial, String lastName, String address, String city,
			String state, String zipCode, List<TransactionXML> transactions) {
		super();
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.transactions = transactions;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<TransactionXML> getTransactions() {
		return transactions;
	}

	@XmlElementWrapper(name = "transactions")
	@javax.xml.bind.annotation.XmlElement(name = "transaction")
	public void setTransactions(List<TransactionXML> transactions) {
		this.transactions = transactions;
	}

//	@Override
//	public String toString() {
//		return "CustomerXML [firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName=" + lastName
//				+ ", address=" + address + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
//				+ ", transactions=" + transactions + "]";
//	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();

		output.append(firstName);
		output.append(" ");
		output.append(middleInitial);
		output.append(". ");
		output.append(lastName);

		if(transactions != null&& transactions.size() > 0) {
			output.append(" has ");
			output.append(transactions.size());
			output.append(" transactions.");
		} else {
			output.append(" has no transactions.");
		}

		return output.toString();
	}
	
	
}
