package manager_file;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import baseSettings.DBConnector;
import baseSettings.PosFrame;

public class ManagerPage extends PosFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	private String sql = "SELECT a_no, emp_no, name, emp_degree, "
			+ "TO_CHAR(start_work, 'YYYY/MM/DD HH24:MI:SS') AS stime, "
			+ "TO_CHAR(fin_work, 'YYYY/MM/DD HH24:MI:SS') AS ftime, "
			+ "round((fin_work - start_work) * 24) AS wtime, "
			+ "TO_CHAR(start_date, 'YYYY/MM/DD') AS swork "
			+ "FROM absent_info INNER JOIN employees_info  USING (emp_no)";
	private JTable tb;
	private DefaultTableModel model;
	private JButton selBtn = new JButton("조회");
	private String header[] = {"No", "사번", "이름", "직위", "출근시간", "퇴근시간", "근무시간", "근무시작일"};
	private String date_s, date_e;
	
	public ManagerPage() {
		super();

		init();
		setTB();
	}
	
	// db에서 table에 띄울 데이타 가져오기.
	private void setTB() {
		// delete model
		while(model.getRowCount() > 0) {
			model.removeRow(0);
		}
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				int a_no = rs.getInt("a_no");
				int emp_no = rs.getInt("emp_no");
				String name = rs.getString("name");
				String emp_degree = rs.getString("emp_degree");
				String start_work = rs.getString("stime");
				String fin_work = rs.getString("ftime");
				int worked_time = rs.getInt("wtime");
				String start_date = rs.getString("swork");
				Object data[] = {a_no, emp_no, name, emp_degree, start_work, fin_work, worked_time, start_date};
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
		colModel.getColumn(0).setPreferredWidth(40);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel.getColumn(3).setPreferredWidth(50);
		colModel.getColumn(4).setPreferredWidth(150);
		colModel.getColumn(5).setPreferredWidth(150);
		colModel.getColumn(6).setPreferredWidth(100);
		colModel.getColumn(7).setPreferredWidth(100);
	
		scrollpane = new JScrollPane(tb);
		
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		// 달력 출력
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model1 = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model1, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		UtilDateModel model2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		
		// p3에 달력 ~ 달력 조회버튼 추가
		p3.add(datePicker);
		p3.add(new JLabel("~"));
		p3.add(datePicker2);
		p3.add(selBtn);
		
		selBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
				date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
				sql = "SELECT a_no, emp_no, name, emp_degree, "
						+ "TO_CHAR(start_work, 'YYYY/MM/DD HH24:MI:SS') AS stime, "
						+ "TO_CHAR(fin_work, 'YYYY/MM/DD HH24:MI:SS') AS ftime, "
						+ "round((fin_work - start_work) * 24) AS wtime, "
						+ "TO_CHAR(start_date, 'YYYY/MM/DD') AS swork "
						+ "FROM absent_info INNER JOIN employees_info  USING (emp_no) WHERE start_work BETWEEN " + date_s + " AND " + date_e + " + 1";
				setTB();
			}
		});
		
		p1.add(p3, BorderLayout.NORTH);
		p1.add(scrollpane, BorderLayout.CENTER);
		p1.setBorder(null);
		
		// 왼쪽 구성요소 추가
		jsp.setLeftComponent(p1);
		
			// Mac에서도 똑같이 작용하기 위한 버튼 색상 변경을 위한 설정
//			btn.setOpaque(true);
//		    btn.setBorderPainted(false);
			
//		    btn.setBackground(new Color(0x66CCFF));

		Manager_Btns mb = new Manager_Btns();
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
	public static void main(String[] args) {
		ManagerPage mp = new ManagerPage();
		mp.setDefaultOptions();
	}
}
