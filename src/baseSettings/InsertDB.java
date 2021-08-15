package baseSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDB {
	
	public void dbinsert(String sql) {
		try (
		    	Connection conn = DBConnector.getConnection();
		    	PreparedStatement pstmt = conn.prepareStatement(sql);
		    	){
	    		pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
