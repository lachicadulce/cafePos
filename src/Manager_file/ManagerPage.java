package Manager_file;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import baseSettings.DBConnector;
import baseSettings.PosFrame;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class ManagerPage extends PosFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	
	public ManagerPage() {
		super();

		setTB();
		init();
	}
	
	// table 생성 및 컬럼 사이즈 조정
	private void setTB() {
		String sql = "SELECT a_no, emp_no, name, emp_degree, TO_CHAR(start_work, 'YYYY/MM/DD HH24:MI:SS') AS stime, TO_CHAR(fin_work, 'YYYY/MM/DD HH24:MI:SS') AS ftime, round((fin_work - start_work) * 24) AS wtime, TO_CHAR(start_date, 'YYYY/MM/DD') AS swork FROM absent_info INNER JOIN employees_info  USING (emp_no)";
		
		String header[] = {"No", "사번", "이름", "직위", "출근시간", "퇴근시간", "근무시간", "근무시작일"};
		DefaultTableModel model = new DefaultTableModel(header, 0);
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
		
		JTable tb = new JTable(model);
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
	}
	
	// 화면 구성
	private void init() {
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		// 달력 출력
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel);

		// p3에 달력 ~ 달력 조회버튼 추가
		p3.add(datePicker);
		p3.add(new JLabel("~"));
		p3.add(datePicker2);
		p3.add(new JButton("조회"));
		
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
