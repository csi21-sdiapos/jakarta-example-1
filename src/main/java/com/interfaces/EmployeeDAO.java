package com.interfaces;

import java.util.List;

import com.models.Employee;

public interface EmployeeDAO {

	List<Employee> findAll();
	
	Employee findOne(Long id);
	
	boolean create(Employee employee);
	
	boolean update(Employee employee);
	
	boolean delete(Long id);
}
