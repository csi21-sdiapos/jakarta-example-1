package com.implementations;

import java.util.ArrayList;
import java.util.List;

import com.interfaces.EmployeeDAO;
import com.models.Employee;
import com.queries.ConsultasPostgreSql;

public class EmployeeImpl implements EmployeeDAO{
	
	@Override
	public List<Employee> findAll() {

		List<Employee> employeesList = new ArrayList<>();
		
		try {
			employeesList = ConsultasPostgreSql.ConsultaSelectEmpleados();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return employeesList;
	}

	@Override
	public Employee findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Employee employee) {

		boolean result = false;
		
		result = ConsultasPostgreSql.ConsultaInsertEmpleado(employee);
		
		return result;
	}

	@Override
	public boolean update(Employee employee) {
		
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
