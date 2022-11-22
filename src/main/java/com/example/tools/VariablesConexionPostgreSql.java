package com.example.tools;

public class VariablesConexionPostgreSql {

	//Datos de conexión a PostgreSQL
	static final String USER = "postgres";
	static final String PASS = "12345";
	static final String PORT = "5432";
	static final String HOST = "localhost";
	static final String DB = "jakarta-example-1";	
		
	public static String getUser() {
		return USER;
	}
	public static String getPass() {
		return PASS;
	}
	public static String getPort() {
		return PORT;
	}
	public static String getHost() {
		return HOST;
	}
	public static String getDb() {
		return DB;
	}
}