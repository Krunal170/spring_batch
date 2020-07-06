package com.example.chapter7.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.chapter7.domain.CustomerDBDAO;

public class CustomerRowMapper implements RowMapper<CustomerDBDAO> {

	@Override
	public CustomerDBDAO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		customerDBDAO.setId(resultSet.getLong("id"));
		customerDBDAO.setAddress(resultSet.getString("address"));
		customerDBDAO.setCity(resultSet.getString("city"));
		customerDBDAO.setFirstName(resultSet.getString("firstName"));
		customerDBDAO.setLastName(resultSet.getString("lastName"));
		customerDBDAO.setMiddleInitial(resultSet.getString("middleInitial"));
		customerDBDAO.setState(resultSet.getString("state"));
		customerDBDAO.setZipCode(resultSet.getString("zipCode"));
		
		return customerDBDAO;
	}
	

}
