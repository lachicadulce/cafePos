package manager_file;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import baseSettings.DBConnector;

public class Employees_List extends JPanel {

	private JScrollPane scrollpane;
	private String sql = "SELECT emp_no, name, tel, TO_CHAR(start_date, 'YYYY-MM-DD') AS sdate, emp_degree FROM employees_info";
	private JTable tb;
	private DefaultTableModel model;
	private String header[] = {"사번", "이름", "연락처", "입사일자", "직급"};
	
	public Employees_List() {
		super();

		init();
		setTB();
	}
	
	// db에서 table에 띄울 직원목록 가져오기
	private void setTB() {
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				int emp_no = rs.getInt("emp_no");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String start_date = rs.getString("sdate");
				String emp_degree = rs.getString("emp_degree");
				Object data[] = {emp_no, name, tel, start_date, emp_degree};
				model.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	}
	
	// 화면 구성
	private void init() {
		model = new DefaultTableModel(header, 0);

		tb = new JTable(model);
		tb.setFont(new Font("", Font.PLAIN, 14));
		JTableHeader tbheader = tb.getTableHeader();
		tbheader.setFont(new Font("", Font.PLAIN, 15));
		TableColumnModel colModel = tb.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(30);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(100);
		colModel.getColumn(3).setPreferredWidth(100);
		colModel.getColumn(4).setPreferredWidth(50);

		scrollpane = new JScrollPane(tb);

		setPreferredSize(new Dimension(0, 110));
		setBackground(new Color(0xD7E7F7));
		
		add(scrollpane);
	}
}
