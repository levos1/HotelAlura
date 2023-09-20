package factorys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public Connection resolverConeccion() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/jugos?userTimeZone=true&serverTimeZone=UTC","root","Quinjoa_123");
		//System.out.println("Cerrando la conexión a BD");
		return con;
	}
}
