package manager_file;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.jdatepicker.impl.*;

import baseSettings.*;
// 직원 리스트
public class EmployeeList extends PosFrame {

	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	private String sql = "SELECT * FROM employees_info";
	private JTable tb;
	private DefaultTableModel model;
	private String header[] = {"사번", "이름", "연락처", "입사일자", "직급"};
	
	public EmployeeList() {
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
				String start_date = rs.getString("start_date");
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
		
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		
		//===========================================================
		JPanel p3 = new JPanel();
		p3.setPreferredSize(new Dimension(0, 110));
		p3.setBackground(new Color(0xD7E7F7));
		
		JButton change1 = new JButton();
		JButton change2 = new JButton();
		change1.setPreferredSize(new Dimension(700, 100));
		change2.setPreferredSize(new Dimension(120, 100));
		change1.setBackground(new Color(0xF2F5A9));
		p3.add(change1);
		p3.add(change2);
		
		//==============================================================
		
		p1.add(p3, BorderLayout.SOUTH);
		p1.add(scrollpane, BorderLayout.CENTER);

		// 왼쪽 구성요소 추가
		jsp.setLeftComponent(p1);

		Manager_Btns mb = new Manager_Btns();
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
		
	}
	
	public static void main(String[] args) {
		EmployeeList el = new EmployeeList();
		el.setDefaultOptions();
	}
}
