package com.example.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.connections.ConexionPostgreSql;
import com.example.models.Employee;
import com.example.tools.VariablesConexionPostgreSql;

public class ConsultasPostgreSql {

	/**************************************** CONSULTAS SELECT *********************************************/
	
    public static List<Employee> ConsultaSelectEmpleados()
    {
    	List<Employee> employeesList = new ArrayList<>();
        Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		
		ConexionPostgreSql conexionPostgreSql = new ConexionPostgreSql();		
		Connection conexionGenerada = conexionPostgreSql.generaConexion(VariablesConexionPostgreSql.getHost(), VariablesConexionPostgreSql.getPort(), VariablesConexionPostgreSql.getDb(), VariablesConexionPostgreSql.getUser(), VariablesConexionPostgreSql.getPass());
				
		if(conexionGenerada != null) {
			
			try {
				declaracionSQL = conexionGenerada.createStatement();
				resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM \"public\".\"employees\"");
				
				while(resultadoConsulta.next()) {
					Long id = resultadoConsulta.getLong("id");
					String name = resultadoConsulta.getString("name");
					String nif = resultadoConsulta.getString("nif");
					int age = resultadoConsulta.getInt("age");
					
					System.out.println(id + " - " + name + " - " + nif + " - " + age);
					
					Employee employee = new Employee(id, name, nif, age);
					employeesList.add(employee);
				}
				
			    resultadoConsulta.close();
			    declaracionSQL.close();
			    conexionGenerada.close();
				
			} catch (SQLException e) {
				System.out.println("\n[ERROR-Consultas-ConsultasPostgreSQL.java] Error generando la declaracionSQL: " + e);
			}
		}	
		
		return employeesList;
    }
    
    /*************************************** CONSULTAS INSERTS ******************************************/
    public static boolean ConsultaInsertEmpleado(Employee employee)
    {
    	boolean result = false;
        PreparedStatement declaracionSQL = null;
		ConexionPostgreSql conexionPostgreSql = new ConexionPostgreSql();
		
		Connection conexionGenerada = conexionPostgreSql.generaConexion(VariablesConexionPostgreSql.getHost(), VariablesConexionPostgreSql.getPort(), VariablesConexionPostgreSql.getDb(), VariablesConexionPostgreSql.getUser(), VariablesConexionPostgreSql.getPass());
				
		if(conexionGenerada != null) {
			
			try {
				declaracionSQL = conexionGenerada.prepareStatement("INSERT INTO \"public\".\"employees\" (name, nif, age) VALUES (?, ?, ?)");
				
				declaracionSQL.setString(1, employee.getName());
				declaracionSQL.setString(2, employee.getNif());
				declaracionSQL.setInt(3, employee.getAge());
				
				declaracionSQL.executeUpdate();
				result = true;
				
			    declaracionSQL.close();
			    conexionGenerada.close();
				
			} catch (SQLException e) {
				System.out.println("\n[ERROR-Consultas-ConsultasPostgreSQL.java] Error generando la declaracionSQL: " + e);
			}
		}
		
		return result;
    }
    
}
