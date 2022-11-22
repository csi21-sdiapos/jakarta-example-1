package com.example.interfaces;

import java.util.List;

import com.example.models.Employee;

public interface EmployeeDAO {

	List<Employee> findAll();
	
	Employee findOne(Long id);
	
	boolean create(Employee employee);
	
	boolean update(Employee employee);
	
	boolean delete(Long id);
}
