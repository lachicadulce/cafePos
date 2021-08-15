package manager_file;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import baseSettings.DBConnector;
import baseSettings.PosFrame;
// 매출현황
public class ManagerSales extends PosFrame {
	// JSplitPane : 컴포넌트 분할
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	
	private String sql = "SELECT receipt_no, "
			+ "to_char(datetime, 'YYYY/MM/DD HH24:MI:SS') AS dtime, "
			+ "total, credit, cash, cus_no, "
			+ "point_used, point_saved, state, receipt_chk "
			+ "FROM history_payment "
			+ "WHERE state = 'complete' ORDER BY receipt_no " ;
	private JTable tb;
	private DefaultTableModel model;
	private JButton selBtn = new JButton("조회");
	private String header[] = {"No", "결제일자", "총 금액", "현금결제","카드결제", "멤버쉽번호", 
			"차감포인트", "적립포인트", "결제상태", "현금영수증(Y/N)"};
	private String date_s, date_e;
	public ManagerSales() {
		super();
		init();
		setTB();
	}
	
	// table 생성 및 컬럼 사이즈 조정
	private void setTB() {		
		while(model.getRowCount() > 0) {
			model.removeRow(0);
		}
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	if(rs.next()) {
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
			} else {
				JOptionPane.showMessageDialog(this, "조회 결과가 없습니다.");
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

		//우측버튼 너비
		jsp.setResizeWeight(0.9);
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		// 매출 합계 구성패널 추가
		JPanel p4 = new JPanel(new GridLayout(3, 2));
		p4.setPreferredSize(new Dimension(0, 140));
		p4.setBackground(new Color(0xD7E7F7));
		
		// 매출 합계 라벨로 추가
		TotalLabel tl = new TotalLabel();
		for (JLabel labels : tl.getLabels()) {
			p4.add(labels);
		}    
	    // 매출합계 패널 아래쪽에 붙이기
	 	p1.add(p4, BorderLayout.SOUTH);
		
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
		
		// 달력에서 '조회' 버튼 눌렀을 때의 동작 설정
		selBtn.addActionListener(new ActionListener() {
			// 선택한 날짜가 입력되어 데이터 검색 값을 불러오도록 설정
			@Override
			public void actionPerformed(ActionEvent e) {
				date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
				date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
				sql = "SELECT receipt_no, "
						+ "to_char(datetime, 'YYYY/MM/DD HH24:MI:SS') AS dtime, "
						+ "total, credit, cash, cus_no, "
						+ "point_used, point_saved, state, receipt_chk "
						+ "FROM history_payment "
						+ "WHERE state = 'complete' AND datetime BETWEEN "
						+ date_s + " AND " + date_e + " + 1 "
						+ "ORDER BY receipt_no ";
				setTB();
				// 매출 합계를 선택한 기간에 맞게 데이터를 조회하여 노출
				tl.updateDB(date_s, date_e);
			}
		});
 		
		// p3: 달력 조회 기능 부분을 왼쪽 상단에 추가, scrollpane: 테이블 리스트에 스크롤되도록 설정
		p1.add(p3, BorderLayout.NORTH);
		p1.add(scrollpane, BorderLayout.CENTER);
		//p1.setBorder(null);
		
		// 왼쪽 구성요소 추가 (달력, 테이블 리스트, 매출합계)
		jsp.setLeftComponent(p1);
		
		// Manager_Btns 으로 버튼 추가
		Manager_Btns mb = new Manager_Btns(this);
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가 (관리자버튼 5종)
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
//	public static void main(String[] args) {
//		ManagerSales frame = new ManagerSales();
//		frame.setDefaultOptions();
//	}
}

