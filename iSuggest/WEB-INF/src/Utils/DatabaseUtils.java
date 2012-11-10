package Utils;
import java.sql.*;
import java.io.*;

public class DatabaseUtils {
	public Connection connectToDatabase() {
		Connection connection = null;
		try {
			/* Create string of connection url within specified format with machine name, port number and database name. Here machine name id localhost and database name is usermaster. */ 
			String connectionURL = "jdbc:mysql://localhost:3306/isuggest"; 
			// Load JBBC driver "com.mysql.jdbc.Driver"
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			/* Create a connection by using getConnection() method that takes parameters of string type connection url, user name and password to connect to database. */ 
			connection = DriverManager.getConnection(connectionURL, "root", "master");
			if(!connection.isClosed()) {
				//System.out.println("Connected");
			}
			else {
				//System.out.println("Unable to connect to database.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Connection connectToDatabase(String ip, String port, String sid, String userName, String password) {
		Connection connection = null;
		try {
			/* Create string of connection url within specified format with machine name, port number and database name. Here machine name id localhost and database name is usermaster. */ 
			String connectionURL = "jdbc:oracle:thin:@//" + ip + ":" + port +"/" + sid; 
			// Load JBBC driver "com.mysql.jdbc.Driver"
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			/* Create a connection by using getConnection() method that takes parameters of string type connection url, user name and password to connect to database. */ 
			connection = DriverManager.getConnection(connectionURL, userName, password);
			if(!connection.isClosed()) {
				System.out.println("Connected");
			}
			else {
				System.out.println("Unable to connect to database.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}