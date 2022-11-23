package com.example.controllers;

import java.io.IOException;

import com.example.implementations.EmployeeServiceImpl;
import com.example.models.Employee;
import com.example.services.EmployeeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(name = "com.example.controllers.EmployeeServlet", value = "/employees")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Hola EmployeeServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name =  request.getParameter("name");
		String nif = request.getParameter("nif");
		int age = Integer.valueOf(request.getParameter("age"));

		Employee employee = new Employee(null, name, nif, age);
		this.employeeService.create(employee);
		
		System.out.println(name + " - " + nif + " - " + age);
	}

}
