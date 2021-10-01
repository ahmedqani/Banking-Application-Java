package dao;

import dao.impl.TransactionDaoImpl;
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
	private static final String URL ="jdbc:mysql://localhost:3307/bank_console?serverTimezone=UTC";
	
	private static UserDaoImpl userDaoImpl;
	private static Connection connection;
	private static TransactionDaoImpl transactionDaoImpl;

	public static synchronized UserDao getUserDao() {

		if (userDaoImpl == null) {
			userDaoImpl = new UserDaoImpl();
		}
		return userDaoImpl;
	}

	public static synchronized TransactionDao getTransactionDao() {

		if (transactionDaoImpl == null) {
			transactionDaoImpl = new TransactionDaoImpl();
		}
		return transactionDaoImpl;
	}

	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
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
