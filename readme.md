# Jakarta Ejemplo 1

![](./img/1.png)

![](./img/2.png)

![](./img/3.png)

![](./img/4.png)

![](./img/5.png)

![](./img/6.png)

# 0. Crear el proyecto

Creamos un artefacto con el arquetipo de webapps, le añadimos las dependencias al *pom.xml*, añadimos el JDK-19 al *build path*, y hacemos el *maven install* sobre el *pom.xml*, y actualizamos el proyecto con *maven --> update project*

![](./img/7.png)

**Nota**: también comprobamos la versión del *dynamic web module*.

![](./img/9.png)

# 1. Creamos un servlet

en *src/main/java* creamos un nuevo paquete llamado *com.example* y le añadimos un nuevo archivo de tipo servlet, y en él, cambiamos las importaciones automáticas de *javax* por *jakarta*.

![](./img/8.png)

# 2. Añadimos su enrutado

```java
/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "HelloServlet", value = "/hello")
public class HelloServlet extends HttpServlet { 
    
    ... 

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: Hello World");
	}

    ...
}
```

Añadimos el servidor de Tomcat 10 y lo iniciamos. Vamos a nuestro navegador y escribimos la ruta.

![](./img/9.png)

![](./img/10.png)

# 3. Conexión con la BBDD

![](./img/12.png)

![](./img/13.png)

![](./img/19.png)

![](./img/20.png)

## 3.1. com.tools --> VariablesConexionPostgreSql.java

```java
package com.tools;

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
```

## 3.2. com.connections --> ConexionPostgreSql.java

```java
package com.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionPostgreSql {

	public Connection generaConexion(final String host, final String port, final String db, final String user, final String pass) {

		System.out.println("\n[INFORMACIÓN-conexionPostgresql-generaConexion] Entra en generaConexion");
		
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
```

# 4. Operaciones CRUD

![](./img/14.png)

![](./img/15.png)

![](./img/16.png)

![](./img/17.png)

![](./img/18.png)

## 4.1. com.queries --> ConsultasPostgreSql.java

```java
package com.queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.connections.ConexionPostgreSql;
import com.tools.VariablesConexionPostgreSql;

public class ConsultasPostgreSql {

/**************************************** CONSULTAS SELECT *********************************************/
	
    public static void ConsultaSelectEmpleados(Connection conexionGenerada)
    {
        Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		
		ConexionPostgreSql conexionPostgreSql = new ConexionPostgreSql();		
		conexionGenerada = conexionPostgreSql.generaConexion(VariablesConexionPostgreSql.getHost(), VariablesConexionPostgreSql.getPort(), VariablesConexionPostgreSql.getDb(), VariablesConexionPostgreSql.getUser(), VariablesConexionPostgreSql.getPass());
				
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
				}
				
			    resultadoConsulta.close();
			    declaracionSQL.close();
			    conexionGenerada.close();
				
			} catch (SQLException e) {
				System.out.println("\n[ERROR-Consultas-ConsultasPostgreSQL.java] Error generando la declaracionSQL: " + e);
			}
		}		
    }    
}
```

## 4.2. com.example --> HelloServlet.java

```java
/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: Hello World");
		
		Connection conexionGenerada = null;
		
		try {
			ConsultasPostgreSql.ConsultaSelectEmpleados(conexionGenerada);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
```

![](./img/21.png)

# 5. Capa DAO

![](./img/22.png)

![](./img/23.png)

![](./img/24.png)

## 5.1. com.models --> Employee.java

```java
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
```

## 5.2. com.interfaces --> EmployeeDAO.java

```java
package com.interfaces;

import java.util.List;

import com.models.Employee;

public interface EmployeeDAO {

	List<Employee> findAll();
	Employee findOne(Long id);
	boolean create(Employee employee);
	boolean update(Employee employee);	
	boolean delete(Long id);
}
```

## 5.3. Actualizamos el método de la consulta select

```java
package com.queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.connections.ConexionPostgreSql;
import com.models.Employee;
import com.tools.VariablesConexionPostgreSql;

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
}
```

# 5.4. com.implementations --> EmployeeImpl.java

```java
package com.implementations;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.interfaces.EmployeeDAO;
import com.models.Employee;
import com.queries.ConsultasPostgreSql;

public class EmployeeImpl implements EmployeeDAO{

	@Override
	public List<Employee> findAll() {

		List<Employee> employeesList = new ArrayList<>();
		Connection conexionGenerada = null;
		
		try {
			employeesList = ConsultasPostgreSql.ConsultaSelectEmpleados(conexionGenerada);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return employeesList;
	}

	@Override
	public Employee findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}	
}
```

## 5.5. Volvemos al HelloServlet.java

```java
/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EmployeeDAO employeeDAO = new EmployeeImpl(); // polimorfismo
		
		response.getWriter().append(employeeDAO.findAll().toString());
	}
```

![](./img/25.png)

![](./img/26.png)

# 5.6. Hacemos la consulta insert

### ConsultasPostgreSql.java

```java
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
```

### EmployeeImpl.java

```java
@Override
	public boolean create(Employee employee) {

		boolean result = false;
		
		result = ConsultasPostgreSql.ConsultaInsertEmpleado(employee);
		
		return result;
	}
```

### HelloServlet.java

```java
/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EmployeeDAO employeeDAO = new EmployeeImpl(); // polimorfismo
		
		Employee testEmployee = new Employee(null, "testEmployee", "44444444D", 18);
		
		employeeDAO.create(testEmployee);
		
		response.getWriter().append(employeeDAO.findAll().toString());
	}
```

![](./img/27.png)

![](./img/28.png)

