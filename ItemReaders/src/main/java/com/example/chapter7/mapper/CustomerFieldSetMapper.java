package com.example.chapter7.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.chapter7.domain.CustomerCSV;

public class CustomerFieldSetMapper implements FieldSetMapper<CustomerCSV> {

	@Override
	public CustomerCSV mapFieldSet(FieldSet fieldSet) throws BindException {
		
		CustomerCSV customer = new CustomerCSV();
		customer.setAddress(fieldSet.readString("addressNumber") + " " + fieldSet.readString("street"));
		customer.setCity(fieldSet.readString("city"));
		customer.setFirstName(fieldSet.readString("firstName"));
		customer.setLastName(fieldSet.readString("lastName"));
		customer.setMiddleInitial(fieldSet.readString("middleInitial"));
		customer.setState(fieldSet.readString("state"));
		customer.setZip(fieldSet.readString("zipCode"));
		
		return customer;
	}

}
