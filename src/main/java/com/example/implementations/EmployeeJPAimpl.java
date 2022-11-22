package com.example.implementations;

import java.util.List;

import com.example.interfaces.EmployeeDAO;
import com.example.models.Employee;
import com.example.tools.JPAtools;

import jakarta.persistence.EntityManager;

public class EmployeeJPAimpl implements EmployeeDAO {

	@Override
	public List<Employee> findAll() {

		EntityManager em = JPAtools.getEntityManager();
		List<Employee> employeesList = em.createQuery("SELECT e FROM employees e", Employee.class).getResultList();
		em.close();
		
		return employeesList;
	}

	@Override
	public Employee findOne(Long id) {
		
		EntityManager em = JPAtools.getEntityManager();
		Employee employee = em.find(Employee.class, id);
		em.close();
		System.out.println(employee);
		
		return employee;
	}

	@Override
	public boolean create(Employee employee) {

		EntityManager em = JPAtools.getEntityManager();
		
		em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
	
        return true;
	}

	@Override
	public boolean update(Employee employee) {
/*		
		EntityManager em = JPAtools.getEntityManager();
		Employee employee1 = null;
		
		em.getTransaction().begin();
		employee1.setName(employee1.getName() + "_editado");
		em.merge(employee);
		em.getTransaction().commit();
*/	
		return false;
	}

	@Override
	public boolean delete(Long id) {
/*		
		EntityManager em = JPAtools.getEntityManager();
		
		em.getTransaction().begin();
		em.remove(employeeJPA);
		em.getTransaction().commit();
*/	
		return false;
	}

	
}
