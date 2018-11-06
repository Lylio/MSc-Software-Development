import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private Connection connection = null;

	
	public void connect() {
		String dbname = "m_17_0912407c";
		String username = "0912407c";
		String password = "0912407c";
		
		try {
			connection =
			DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" +
			dbname,username, password);
			}
			catch (SQLException e) {
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
			}
			if (connection != null) {
			System.out.println("Connection successful");
			}
			else {
			System.err.println("Failed to make connection!");
			}

	}
	
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Connection closed");
			}
			catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be closed – SQL exception");
			}
	}
}
