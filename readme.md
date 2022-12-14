# Jakarta Ejemplo 1

![](./img/1.png)

![](./img/2.png)

![](./img/3.png)

![](./img/4.png)

![](./img/5.png)

![](./img/6.png)

# 0. Crear el proyecto

Creamos un artefacto con el arquetipo de webapps, le añadimos las dependencias al *pom.xml*, añadimos el JDK-19 al *build path*, y hacemos el *maven install* sobre el *pom.xml*, y actualizamos el proyecto con *maven --> update project*

```xml
<!-- ********************************* PostgreSQL ************************************* -->
	<dependency>
	    <groupId>org.postgresql</groupId>
	    <artifactId>postgresql</artifactId>
	    <version>42.5.0</version>
	</dependency>
	
	<!-- ********************************* Jakarta ************************************* -->
	<dependency>
	    <groupId>jakarta.servlet</groupId>
	    <artifactId>jakarta.servlet-api</artifactId>
	    <version>6.0.0</version>
	    <scope>provided</scope>
	</dependency>

	<dependency>
	    <groupId>jakarta.servlet.jsp</groupId>
	    <artifactId>jakarta.servlet.jsp-api</artifactId>
	    <version>3.1.0</version>
	    <scope>provided</scope>
	</dependency>

	<dependency>
	    <groupId>jakarta.servlet.jsp.jstl</groupId>
	    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
	    <version>3.0.0</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.annotation</groupId>
	    <artifactId>jakarta.annotation-api</artifactId>
	    <version>2.1.1</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.xml.bind</groupId>
	    <artifactId>jakarta.xml.bind-api</artifactId>
	    <version>4.0.0</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.inject</groupId>
	    <artifactId>jakarta.inject-api</artifactId>
	    <version>2.0.1</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.ws.rs</groupId>
	    <artifactId>jakarta.ws.rs-api</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.validation</groupId>
	    <artifactId>jakarta.validation-api</artifactId>
	    <version>3.0.2</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.persistence</groupId>
	    <artifactId>jakarta.persistence-api</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>jakarta.activation</groupId>
	    <artifactId>jakarta.activation-api</artifactId>
	    <version>2.1.0</version>
	</dependency>
	
	<!-- ********************************* Hibernate ************************************* -->
	<dependency>
	    <groupId>org.hibernate.validator</groupId>
	    <artifactId>hibernate-validator</artifactId>
	    <version>8.0.0.Final</version>
	</dependency>

	<dependency>
	    <groupId>org.hibernate.orm</groupId>
	    <artifactId>hibernate-core</artifactId>
	    <version>6.1.5.Final</version>
	</dependency>
	
	<dependency>
	    <groupId>org.hibernate</groupId>
	    <artifactId>hibernate-core-jakarta</artifactId>
	    <version>5.6.14.Final</version>
	</dependency>
<!--
	<dependency>
	    <groupId>org.hibernate</groupId>
	    <artifactId>hibernate-annotations</artifactId>
	    <version>3.5.6-Final</version>
	</dependency>
-->
	<dependency>
	    <groupId>org.hibernate.javax.persistence</groupId>
	    <artifactId>hibernate-jpa-2.1-api</artifactId>
	    <version>1.0.2.Final</version>
	</dependency>

	<dependency>
	    <groupId>org.eclipse.persistence</groupId>
	    <artifactId>org.eclipse.persistence.jpa</artifactId>
	    <version>4.0.0</version>
	</dependency>
```

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

# 6. Conexión del proyecto con Hibernate

![](./img/29.png)

![](./img/30.png)

![](./img/31.png)

## 6.1. Cambiar el tipo de proyecto a JPA

**Atención**: No hacer este paso, no es necesario, y de hacerlo, me da error el *persistence.xml*

![](./img/32.png)

![](./img/33.png)

## 6.2. persistance.xml

La acción de Project Face --> JPA nos ha creado una carpeta *META-INF*, y dentro de ella un archivo llamado *persistance.xml*...

**Atención**: creamos nosotros mismos la carpeta META-INF dentro del paquete *src/main/java*. Y dentro de META-INF creamos un archivo xml llamado *persistence.xml*

![](./img/34.png)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- <?xml version="1.0" encoding="UTF-8" standalone="yes"?> ............... el standalone hace que EntityManagerFactory no pueda reconocer el name de mi <persistence-unit> -->

<persistence 
	xmlns="https://jakarta.ee/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
    version="3.0"
>
    <persistence-unit name="default">

        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <properties>
            <property name="hibernate.connection.url" value="jdbc:postgresql://localhost/jakarta-example-1"/>
            <property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
            <property name="hibernate.connection.username" value="postgres"/>
            <property name="hibernate.connection.password" value="12345"/>
            <property name="hibernate.archive.autodetection" value="class"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="create"/>
        </properties>
    </persistence-unit>
</persistence>
```

# 7. Entidades JPA

![](./img/35.png)

![](./img/36.png)

![](./img/37.png)

![](./img/38.png)

![](./img/39.png)

![](./img/40.png)

```xml
<dependency>
	<groupId>org.hibernate</groupId>
	<artifactId>hibernate-core-jakarta</artifactId>
	<version>5.6.14.Final</version>
</dependency>
```

**Nota**: *run as --> maven install*

## 7.1. Añadir anotaciones al modelo Employee.java

```java
...
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String nif;
	private int age;
...
```

## 7.2. EntityManagerFactory en el controlador de HelloServlet

```java
/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
		EntityManager em = emf.createEntityManager();
		Employee employee1 = em.find(Employee.class, 1L);
		System.out.println(employee1);
		
		// EmployeeDAO employeeDAO = new EmployeeImpl(); // polimorfismo
		// Employee testEmployee = new Employee(null, "testEmployee", "44444444D", 18);
		// employeeDAO.create(testEmployee);
		
		// response.getWriter().append(employeeDAO.findAll().toString());
		response.getWriter().append("Hola Mundo");
	}
```

![](./img/41.png)

# 8. Operaciones CRUD con JPA Hibernate

![](./img/42.png)

![](./img/43.png)

![](./img/44.png)

![](./img/45.png)

![](./img/46.png)

### HelloServlet.java

```java
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
```

Si a priori, comentamos la parte del *delete* y ejecutamos el proyecto, podremos ver en pgAdmin que se ha creado un empleado. Y si después descomentamos el *delete* y lo volvemos a ejecutar, vemos que elimina tal empleado y se queda la tabla vacía.

# 9. Capa DAO con JPA Hibernate

![](./img/47.png)

![](./img/48.png)

![](./img/49.png)

Vamos a abstraer la creación del contexto de EntityManager.

Para ello, vamos a extraer las dos sentencias de código de nuestro *HelloServlet.java*, para llevárnoslas a una nueva clase (*com.example.tools --> JPAtools.java*) y al ponerlas como *static* las podremos volver a usar en el *HelloServlet*.

## 9.1. com.example.tools --> JPAtools.java

```java
package com.tools;

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
```

## EXTRA: Refactorización de la estructura del proyecto.

Para una mejor orghanización, vamos a pararnos un momento a meter todos los paquetes dentro del principal de *com.example*.

**Nota**: recuerda que nuestra vista de los archivos del proyecto está predeterminadamente en *flat*... si quieres, puedes cambiarla a *hierarchical*, pero ten en cuenta que la jerarquía parte de *com* y después de *example*.

**Nota**: no olvides actualizar el nombre del WebServlet:
```java
/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "com.example.controllers.HelloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
	...
}
```

![](./img/50.png)

## 9.2. com.example.implementations --> EmployeeJPAimpl.java

```java
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
```

![](./img/51.png)

![](./img/52.png)

![](./img/53.png)

# 10. Crear el Servlet del Employee

![](./img/54.png)

![](./img/55.png)

## 10.1. com.example.controller --> EmployeeServlet.java

![](./img/56.png)

```java
package com.example.controllers;

import java.io.IOException;

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
	
	...

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Hola EmployeeServlet");
	}

	...
}
```

![](./img/57.png)

![](./img/58.png)

# 11. Capa controlador Servlet

## 11.1. Método DoGet()

![](./img/59.png)

![](./img/60.png)

## 11.2. Método DoPost()

![](./img/61.png)

![](./img/62.png)

## 11.3. com.example.controllers --> EmployeeServlet.java

```java
package com.example.controllers;

import java.io.IOException;

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

		String email = request.getParameter("email");
		String name =  request.getParameter("name");
		
		System.out.println(email + " - " + name);
	}
}
```

## 11.4. src --> main --> webapps --> index.html

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ------------------------------------------- BOOTSTRAP ------------------------------------------- -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<title>Title Page</title>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<form action="http://localhost:8080/jakarta-example-1/employees" method="post">
				  <div class="mb-3">
				    <label for="email" class="form-label">Email</label>
				    <input name="email" type="email" class="form-control" id="email">
				  </div>
				  <div class="mb-3">
				    <label for="name" class="form-label">Name</label>
				    <input name="name" type="text" class="form-control" id="name">
				  </div>
				  <button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</div>
		</div>
	</div>
	
	<!-- --------------------------------------------- SCRIPTS ------------------------------------------ -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>   
</body>

</html>
```

# 12. Conectar el servlet con la capa DAO a través de un servicio.

![](./img/65.png)

![](./img/66.png)

## 12.1. com.example.services --> EmployeeService.java (interfaz)

```java
package com.example.services;

import com.example.models.Employee;

public interface EmployeeService {

	boolean create(Employee employee);
}
```

## 12.2. com.example.implementations --> EmployeeServiceImpl.java

```java
package com.example.implementations;

import com.example.interfaces.EmployeeDAO;
import com.example.models.Employee;
import com.example.services.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeDAO employeeDAO;
	
	public EmployeeServiceImpl() {
		this.employeeDAO = new EmployeeJPAimpl();
	}

	@Override
	public boolean create(Employee employee) {

		return this.employeeDAO.create(employee);
	}
}
```

**Nota**: comprueba que tienes hecho el método create() de la clase EmployeeJPAimpl.

## 12.3. Actualizamos el EmployeeServlet

```java
/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet(name = "com.example.controllers.EmployeeServlet", value = "/employees")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
    ...

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
```

## 12.4. Actualizamos el index.html

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ------------------------------------------- BOOTSTRAP ------------------------------------------- -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<title>Title Page</title>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<form action="http://localhost:8080/jakarta-example-1/employees" method="post">
				<div class="mb-3">
				    <label for="name" class="form-label">Name</label>
				    <input name="name" type="text" class="form-control" id="name">
				  </div>
				  <div class="mb-3">
				    <label for="nif" class="form-label">NIF</label>
				    <input name="nif" type="text" class="form-control" id="nif">
				  </div>
				  <div class="mb-3">
				    <label for="age" class="form-label">Age</label>
				    <input name="age" type="text" class="form-control" id="age">
				  </div>
				  <button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</div>
		</div>
	</div>
	
	<!-- --------------------------------------------- SCRIPTS ------------------------------------------ -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>   
</body>

</html>
```

![](./img/67.png)

![](./img/68.png)

![](./img/69.png)

# 13. API JAX-RS con Jersey

![](./img/70.png)

![](./img/71.png)

![](./img/72.png)

![](./img/73.png)

## 13.1. Añadimos las dependencias necesarias al *pom.xml*

```xml
<!-- ********************************* Jersey ************************************* -->
	<dependency>
	    <groupId>jakarta.ws.rs</groupId>
	    <artifactId>jakarta.ws.rs-api</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.containers</groupId>
	    <artifactId>jersey-container-servlet</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.inject</groupId>
	    <artifactId>jersey-hk2</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.core</groupId>
	    <artifactId>jersey-client</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.core</groupId>
	    <artifactId>jersey-server</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.media</groupId>
	    <artifactId>jersey-media-json-jackson</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.core</groupId>
	    <artifactId>jersey-common</artifactId>
	    <version>3.1.0</version>
	</dependency>

	<dependency>
	    <groupId>org.glassfish.jersey.media</groupId>
	    <artifactId>jersey-media-multipart</artifactId>
	    <version>3.1.0</version>
	</dependency>
```

## 13.2. Creamos un nuevo modelo

### com.example.models --> Student.java

```java
package com.example.models;

import java.io.Serializable;

public class Student implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;
	
	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public Student() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "\nStudent [name=" + name + ", age=" + age + "]";
	}	
}
```

## 13.3. com.example.api --> App.java

```java
package com.example.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
public class App extends Application {

}
```

## 13.4. com.example.resources --> StudentResource.java

```java
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
```

![](./img/74.png)

# 14. Capa vista con JSP/JSTL/JSF

## 14.1. Etiquetas JSP

![](./img/75.png)

![](./img/76.png)

![](./img/77.png)

![](./img/78.png)

![](./img/79.png)

![](./img/80.png)

### src --> main --> webapps --> hello.jsp

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<% out.println("Hola Mundo desde JSP"); %>	
</body>
</html>
```

![](./img/81.png)

![](./img/82.png)

# 15. Etiquetas JSTL

![](./img/83.png)

![](./img/84.png)

![](./img/85.png)

![](./img/86.png)

![](./img/87.png)

## 15.1. pom.xml

```xml
<!--
	<dependency>
	    <groupId>jakarta.servlet.jsp.jstl</groupId>
	    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
	    <version>3.0.0</version>
	</dependency>
-->
	<!-- para usar las etiquetas de jstl, tenemos que quitar la dependencia de jakarta.servlet.jsp-api para instalar ahora la de jakarta.servlet.jsp.jstl (sin api, la de glashhfish de eclipse) -->
	<dependency>
	    <groupId>org.glassfish.web</groupId>
	    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
	    <version>3.0.1</version>
	</dependency>
```

## 15.2. Las cinco etiquetas de JSTL

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
```

