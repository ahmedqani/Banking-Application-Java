package dao;

import dao.impl.UserDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Class used to retrieve DAO Implementations. Serves as a factory.
 * 
 * @author anon
 *
 */
public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "root";
	private static final String CONNECTION_PASSWORD = "root";
	private static final String URL ="jdbc:mysql://localhost:3307/ezoo?serverTimezone=UTC";
	
	private static UserDaoImpl userDaoImpl;
	private static Connection connection;

	public static synchronized UserDao getAnimalDao() {

		if (userDaoImpl == null) {
			userDaoImpl = new UserDaoImpl();
		}
		return userDaoImpl;
	}

	static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("getting new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}

}
