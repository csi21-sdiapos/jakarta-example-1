package com.example.resources;

import java.util.List;

import com.example.models.Student;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/students") // esta API sería un endpoint
@Produces("application/json")
@Consumes("application/json")
public class StudentResource {

	@GET
	public List<Student> findAll() {
		
		return List.of
				(
					new Student("Estudiante1", 20),
					new Student("Estudiante2", 30)
				);
	}
}
