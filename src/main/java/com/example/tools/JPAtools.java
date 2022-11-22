package com.example.tools;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAtools {

	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
	// EntityManager em = emf.createEntityManager();
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
