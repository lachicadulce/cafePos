import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PrjctDBConnector {

	private static String driverName = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static String user = "cafe";
	private static String password = "!!22Qorthdud";
	
	static {
		
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}//static
	
	public static Connection getConnection() {
		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
