package Manager_file;
import java.awt.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import baseSettings.*;
import net.sourceforge.jdatepicker.impl.*;

public class ManagerSales extends PosFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;

	public ManagerSales() {
		super();
		setTB();
		init();
	}
	
	// table 생성 및 컬럼 사이즈 조정
	private void setTB() {		
		String sql = "SELECT receipt_no, "
				+ "to_char(datetime, 'YYYY/MM/DD HH24:MI:SS') AS dtime, "
				+ "total, "
				+ "credit, "
				+ "cash, "
				+ "cus_no, "
				+ "point_used, "
				+ "point_saved, "
				+ "state, "
				+ "receipt_chk "
				+ "FROM history_payment "
				+ "WHERE state = 'complete' "
				+ "ORDER BY receipt_no ";
		
		String header[] = {"No", "결제일자", "총 금액", "현금결제","카드결제", "멤버쉽번호", 
						"차감포인트", "적립포인트", "결제상태", "현금영수증(Y/N)"};
		DefaultTableModel model = new DefaultTableModel(header, 0);
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				int receipt_no = rs.getInt("receipt_no");
				String dtime = rs.getString("dtime");
				int total = rs.getInt("total");
				int credit = rs.getInt("credit");
				int cash = rs.getInt("cash");
				int cus_no = rs.getInt("cus_no");
				int point_used = rs.getInt("point_used");
				int point_saved = rs.getInt("point_saved");
				String state = rs.getString("state");
				String receipt_chk = rs.getString("receipt_chk");
				
				Object data[] = {receipt_no, dtime, total, credit, cash, cus_no, 
							point_used, point_saved, state, receipt_chk};
				model.addRow(data);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JTable tb = new JTable(model);
		tb.setFont(new Font("", Font.PLAIN, 14));
		JTableHeader tbheader = tb.getTableHeader();
		tbheader.setFont(new Font("", Font.BOLD, 15));
		TableColumnModel colModel = tb.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(30);
		colModel.getColumn(1).setPreferredWidth(150);
		colModel.getColumn(2).setPreferredWidth(80);
		colModel.getColumn(3).setPreferredWidth(60);
		colModel.getColumn(4).setPreferredWidth(60);
		colModel.getColumn(5).setPreferredWidth(60);
		colModel.getColumn(6).setPreferredWidth(60);
		colModel.getColumn(7).setPreferredWidth(60);
		colModel.getColumn(8).setPreferredWidth(60);
		colModel.getColumn(9).setPreferredWidth(100);
	
		scrollpane = new JScrollPane(tb);
		
		// JTable Column 가운데 정렬
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		cr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel cm = tb.getColumnModel();
		for (int i = 0; i < cm.getColumnCount(); i++) {
			cm.getColumn(i).setCellRenderer(cr);
		}
	}
	
	// 화면 구성
	private void init() {
		//우측버튼 너비
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		// 매출 합계 구성패널 추가
		JPanel p4 = new JPanel();
		p4.setPreferredSize(new Dimension(0, 140));
		p4.setBackground(new Color(0xD7E7F7));
		
		// 매출 합계 라벨 추가 ※ 수정한 부분
		TotalLabel tl = new TotalLabel();
		for (JLabel labels : tl.getLabels()) {
			p4.add(labels);
		}    
	    // 매출합계 패널 아래쪽에 붙이기
	 	p1.add(p4, BorderLayout.SOUTH);
		
		// 달력 출력
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel);

		// p3에 달력 ~ 달력 조회버튼 추가
		p3.add(datePicker1);
		p3.add(new JLabel("~"));
		p3.add(datePicker2);
		p3.add(new JButton("조회"));
		
		p1.add(p3, BorderLayout.NORTH);
		p1.add(scrollpane, BorderLayout.CENTER);
		p1.setBorder(null);
		
		// 왼쪽 구성요소 추가
		jsp.setLeftComponent(p1);
		
		// Manager_Btns 으로 버튼 추가
		Manager_Btns mb = new Manager_Btns();
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
	public static void main(String[] args) {
		ManagerSales frame = new ManagerSales();
		frame.setDefaultOptions();
	}
}

