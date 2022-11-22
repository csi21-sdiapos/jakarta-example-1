package com.example;

import java.io.IOException;

import com.models.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "HelloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
		EntityManager em = emf.createEntityManager();
		
		/********************************** persist (insert) ********************************/
		em.getTransaction().begin();
        Employee employeeJPA = new Employee(null, "EmployeeJPA", "hibernate", 30);
        em.persist(employeeJPA);
        em.getTransaction().commit();

		/********************************** find (search) ********************************/
		Employee employee1 = em.find(Employee.class, 1L);
		System.out.println(employee1);
		
		/********************************** merge (update) ********************************/
		em.getTransaction().begin();
		employee1.setName(employee1.getName() + "_editado");
		em.merge(employeeJPA);
		em.getTransaction().commit();

		// delete
		em.getTransaction().begin();
		em.remove(employeeJPA);
		em.getTransaction().commit();
		
		// EmployeeDAO employeeDAO = new EmployeeImpl(); // polimorfismo
		// Employee testEmployee = new Employee(null, "testEmployee", "44444444D", 18);
		// employeeDAO.create(testEmployee);
		
		// response.getWriter().append(employeeDAO.findAll().toString());
		response.getWriter().append("Hola Mundo");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
