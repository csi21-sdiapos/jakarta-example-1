package com.example.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionPostgreSql {

	public Connection generaConexion(final String host, final String port, final String db, final String user, final String pass) {
		
        Connection conexion = null;
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;            
		
        try {
        	
            try {
                Class.forName("org.postgresql.Driver");
            
            } catch (ClassNotFoundException cnfe) {
                System.out.println("\n[ERROR-conexionPostgresql-generaConexion] Error en registro driver PostgreSQL: " + cnfe);
            }
      
            conexion = DriverManager.getConnection(url, user, pass);           
            
            boolean esValida = conexion.isValid(50000);
            
            if(esValida == false) {
            	conexion = null;
            }
            
            System.out.println(esValida ? "\n[INFORMACIÓN-conexionPostgresql-generaConexion] Conexión a PostgreSQL válida" : "[ERROR-conexionPostgresql-generaConexion] Conexión a PostgreSQL no válida");
            
            return conexion;
            
        } catch (java.sql.SQLException jsqle) {
            System.out.println("\n[ERROR-conexionPostgresql-generaConexion] Error en conexión a PostgreSQL (" + url + "): " + jsqle);
            
            return conexion;
        }
	
		
	}
}