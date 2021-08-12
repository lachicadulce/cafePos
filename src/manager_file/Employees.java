package manager_file;

import java.awt.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import baseSettings.*;
// employees_info 정보를 Arraylist에 담을려다 못함..
public class Employees {
	
	private ArrayList<EmployeesInfo> list;
	private String sql = "SELECT * FROM employees_info";
	
	public Employees() {
		super();

		list = new ArrayList<>();
		try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				list.add(new EmployeesInfo(rs.getInt("emp_no"), rs.getString("name"), rs.getString("tel"), 
						rs.getDate("start_date"), rs.getString("emp_degree")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
