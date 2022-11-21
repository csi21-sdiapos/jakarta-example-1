package com.models;

public class Employee {

	// ATRIBUTOS
	private Long id;
	private String name;
	private String nif;
	private int age;
	
	
	// CONSTRUCTORES
	public Employee(Long id, String name, String nif, int age) {
		super();
		this.id = id;
		this.name = name;
		this.nif = nif;
		this.age = age;
	}

	public Employee() {
		super();
	}
	
	
	// GETTERS Y SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	
	// ToString
	@Override
	public String toString() {
		return "\nEmployee [id=" + id + ", name=" + name + ", nif=" + nif + ", age=" + age + "]";
	}
}
