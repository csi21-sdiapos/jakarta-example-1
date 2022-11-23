package com.example.implementations;

import com.example.interfaces.EmployeeDAO;
import com.example.models.Employee;
import com.example.services.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDAO employeeDAO;
	
	public EmployeeServiceImpl() {
		this.employeeDAO = new EmployeeJPAimpl();
	}

	@Override
	public boolean create(Employee employee) {

		return this.employeeDAO.create(employee);
	}

}
