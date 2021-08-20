package receipt;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import baseSettings.PosFrame;
import main_component.main_test;

public class Receipt extends PosFrame {

	static Container main;
	static String RECEIPT_NO = "1";
	static HashMap<String, ArrayList<Integer>> drink;
	static JPanel frame = new JPanel();

	static JPanel receipt_panel;
	static JLabel receipt;
	static String receipt_string;// 판매 영수증
	static String return_string;// 환불 영수증 
	static String info = "";
	static int pay;

	static int total;
	static int vat;
	static int cash;
	static int point_used;
	static int point;
	static int credit;
	static String receipt_chk;
	static Date transaction_date;
	static String state;
	static DecimalFormat formatter = new DecimalFormat("##,###,###"); // 금액 출력 포멧터

	// 달력 버튼 feat. 소영
	private JButton selBtn = new JButton("조회");
	private static String date_s = "TO_DATE(sysdate, 'YYYY/MM/DD')";
	private static String date_e = "TO_DATE(sysdate, 'YYYY/MM/DD')";
	private static String date_s_e = "TO_DATE(DATETIME, 'YYYY/MM/DD') between " + date_s + " and " + date_e; 

//  ----------------------------------------------------------------------------------------------
	static ArrayList<ArrayList<String>> list_data_default = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> list_data_change = new ArrayList<ArrayList<String>>();

	static String[] columnNames = null;

	static String Receipt_list = "select * from payment_view_2";
	static String Receipt_list_cash = Receipt_list + " where cash > 0";
	static String Receipt_list_credit = Receipt_list + " where credit > 0";

	static String refund_sql = "UPDATE history_payment SET state = 'cancel' WHERE receipt_no = ";
	
	static String cash_receipt = "UPDATE history_payment SET receipt_chk = 'Y' WHERE receipt_no = ";
	static String cash_receipt_cancel = "UPDATE history_payment SET receipt_chk = 'N' WHERE receipt_no = ";

	static String[][] data_default = null;
	static String[][] data_change = null;
	static String[][] data_cash = null;
	static String[][] data_credit = null;

	static int select_receipt_no = -1;
	static String select_receipt_no_string = "";
	
	static String db_sysdate = "";
	static int[] default_date;

	static int w_size, h_size;
	static int cash_w_size, cash_h_size;
	static int credit_w_size, credit_h_size;

	static int no; //
	static String state_chk; // 현재의 결제상태

	static String[] cash_receipt_result;

	private DefaultTableModel model;

	private String sql = "SELECT receipt_no, " + "to_char(datetime, 'YYYY/MM/DD HH24:MI:SS') AS dtime, "
			+ "total, credit, cash, cus_no, " + "point_used, point_saved, state, receipt_chk " + "FROM history_payment "
			+ "WHERE state = 'complete' ORDER BY receipt_no ";

	public void total() {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================

            	
            	Receipt_list += " where " +  date_s_e + " + 1 order by datetime asc";

            	
            	// 기본 디폴트 리스트 
            	PreparedStatement pstmt_Receipt_list = conn.prepareStatement(Receipt_list);
            	
            	ResultSet rs_list = pstmt_Receipt_list.executeQuery();
            	
            	// 컬럼 명을 불러오기 위한 데이터 처리
            	ResultSetMetaData md = rs_list.getMetaData();
            	
            	// 이후 테이블 사이즈를 구성하기 위한 가져온 컬럼의 숫자 저장
            	int column_size = md.getColumnCount();
            	
            	// 구한 컬럼 숫자+1의 이유는 컬럼 이외에 첫번째 col 에는 데이터의 수를 나타내기위한 No 을 추가하기 위함
            	columnNames = new String[column_size+1];
            	
            	// 컬럼명을 배열에 문자열로 저장
            	for(int i = 0; i < columnNames.length; i++) {
            		if (i == 0) {
            			columnNames[i] = "No";
            		} else {
            			columnNames[i] = md.getColumnName(i);
            		}
                }
            	
            	// 기존에 담겨있던 데이터를 비움
            	list_data_default.clear();
            	
            	// 가져온 데이터를 list_data_default(ArrayList) 에 저장
            	while (rs_list.next()) {
            		ArrayList<String> data_total = new ArrayList<>();
            		data_total.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data_total.add("" + i);
            			} else {
            				data_total.add(rs_list.getString(columnNames[i]));
            			}
            		}
            		list_data_default.add(data_total);
            	}
            	
            	// 오픈 했던 각 리소스들 종료 
            	rs_list.close();
            	pstmt_Receipt_list.close();
            	conn.close();
            	
            
            // ================================================================================================
        	// ================================================================================================
            	if (list_data_default.size() > 0) {
	            	// JTable에 담길 데이터의 사이즈를 설정하기 위한 각 데이터의 사이즈 구하기
	            	w_size = list_data_default.size();
	            	h_size = list_data_default.get(0).size();
	            	
	            	// 구한 각 데이터의 사이즈를 가지고 JTable의 크기 설정
	             	data_default = new String[w_size][h_size];
	             	
	             	// 전체(total) 데이터를 JTable에 적용할 배열에 저장
	             	for (int i = 0; i < w_size; i++) {
	            		for (int x = 0; x < h_size; x++) {
	            			if (x == 0) {
	            				data_default[i][x] = "" + (i+1);
	            			}else {
	            				data_default[i][x] = list_data_default.get(i).get(x);
	            			}
	            		}
	            	}
            	} else if (list_data_default.size() == 0) {
            		data_default = new String[0][12];
            	}
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	
	public String[][] table_change(String date) {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================
//            	Receipt_list += " where datetime > TO_DATE('" + where_date + "')";
            	//	date_s, date_e;
         
            	String Receipt_list = "select * from payment_view_2 where " +  date + "+1 order by datetime asc";
            	 
            	// 기본 디폴트 리스트 
            	PreparedStatement pstmt_Receipt_list = conn.prepareStatement(Receipt_list);
            	
            	ResultSet rs_list = pstmt_Receipt_list.executeQuery();
            	
            	// 컬럼 명을 불러오기 위한 데이터 처리
            	ResultSetMetaData md = rs_list.getMetaData();
            	
            	// 이후 테이블 사이즈를 구성하기 위한 가져온 컬럼의 숫자 저장
            	int column_size = md.getColumnCount();
            	
            	// 기존에 담겨있던 데이터를 비움 
            	list_data_change.clear();
            	
            	// 가져온 데이터를 list_data_default(ArrayList) 에 저장
            	
            	while (rs_list.next()) {
            		ArrayList<String> data_total = new ArrayList<>();
            		data_total.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data_total.add("" + i);
            			} else {
            				data_total.add(rs_list.getString(columnNames[i]));
            			}
            		}
            		list_data_change.add(data_total);
            	}
            	
            	// 오픈 했던 각 리소스들 종료 
            	rs_list.close();
            	pstmt_Receipt_list.close();
            	conn.close();
            	
            
            // ================================================================================================
        	// ================================================================================================
            	if (list_data_change.size() > 0) {
            	
	            	// JTable에 담길 데이터의 사이즈를 설정하기 위한 각 데이터의 사이즈 구하기
	            	w_size = list_data_change.size();
	            	h_size = list_data_change.get(0).size();
	            	
	            	// 구한 각 데이터의 사이즈를 가지고 JTable의 크기 설정
	            	data_change = new String[w_size][h_size];
	             	
	             	// 전체(total) 데이터를 JTable에 적용할 배열에 저장
	             	for (int i = 0; i < w_size; i++) {
	            		for (int x = 0; x < h_size; x++) {
	            			if (x == 0) {
	            				data_change[i][x] = "" + (i+1);
	            			}else {
	            				data_change[i][x] = list_data_change.get(i).get(x);
	            			}
	            		}
	            	}
	             	return data_change;
            	}
            	
            	
            	
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		return data_change = new String[0][12];
		
	}

	
	// 반품 처리
	public void refund(int receipt_no) {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
            String sql = "select cus_no, point_saved, point_used from history_payment where receipt_no = " + receipt_no;
            
            String[] temp = new String[3]; 
            
            PreparedStatement select_data = conn.prepareStatement(sql);
            ResultSet selected_data_ = select_data.executeQuery();
            
            while (selected_data_.next()) {
            	temp[0] = selected_data_.getString("cus_no");		//	고객번호
            	temp[1] = selected_data_.getString("point_saved");	//	적립포인트
            	temp[2] = selected_data_.getString("point_used");	//	사용포인트
        	}
            
            int saved_point = Integer.parseInt(temp[1]); 
            int used_point = Integer.parseInt(temp[2]);
            int set_point = 0;
            
            if (saved_point > 0 && used_point > 0) {
            	set_point = (-saved_point) + used_point;
            } else if (saved_point > 0 && !(used_point > 0)) {
            	set_point = -(saved_point);
            } else if (!(saved_point > 0) && used_point > 0) {
            	set_point = used_point;
            }
            
            String update_sql = "update customer_info set point = (select point from customer_info where cus_no = " + temp[0] + ") + (" + set_point + ") where cus_no = " + temp[0];
            String update_sql1 = "update history_payment set state = 'cancel' where receipt_no = " + receipt_no;
            
            PreparedStatement update_customer_info = conn.prepareStatement(update_sql);
            PreparedStatement update_history_payment = conn.prepareStatement(update_sql1);

            update_history_payment.executeUpdate();
			int row = update_customer_info.executeUpdate();
			
			update_customer_info.close();
            select_data.close();
			conn.close();

            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
	}

	// 현금영수증 현재의 유무 확인
	public String[] cash_receipt(int receipt_no) {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            String cash_receipt_chk = "select cash, credit, receipt_chk from history_payment WHERE receipt_no = ";
            cash_receipt_chk += ("" + receipt_no);
            
            PreparedStatement cash_receipt_yn = conn.prepareStatement(cash_receipt_chk);
            ResultSet rs_cash_receipt_yn = cash_receipt_yn.executeQuery();
            
            cash_receipt_result = new String[3];
            
            while (rs_cash_receipt_yn.next()) {
            	cash_receipt_result[0] = rs_cash_receipt_yn.getString("cash");
            	cash_receipt_result[1] = rs_cash_receipt_yn.getString("credit");
            	cash_receipt_result[2] = rs_cash_receipt_yn.getString("receipt_chk");
        	}
            
			cash_receipt_yn.close();
			conn.close();
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
		return cash_receipt_result;
	}

	// 현금영수증 처리 실행
	public void cash_receipt_executive(int receipt_no, String receipt_chk) {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
            if (receipt_chk.equals("N")) {
            	
            	cash_receipt += ("" + receipt_no);
            	PreparedStatement cash_receipt_executive = conn.prepareStatement(cash_receipt);
            	cash_receipt_executive.executeUpdate();
            	cash_receipt_executive.close();
            	
            } else if (receipt_chk.equals("Y")) {
            	
            	cash_receipt_cancel += ("" + receipt_no);
            	PreparedStatement cash_receipt_executive = conn.prepareStatement(cash_receipt_cancel);
            	cash_receipt_executive.executeUpdate();
            	cash_receipt_executive.close();
            	
            }
            
			conn.close();
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	
	// 반품 처리
		public void DBsysdate() {
			try {
	            Connection conn = DriverManager.getConnection(
	            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
	            		"cafe",
	            		"!!22Qorthdud");
	            
	            String sql = "select sysdate from dual";
	            
	            String[] temp = new String[3]; 
	            
	            PreparedStatement select_data = conn.prepareStatement(sql);
	            ResultSet selected_data = select_data.executeQuery();
	            
	            while (selected_data.next()) {
	            	
	            	Date dbsys = selected_data.getDate("sysdate");
	            	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
	            	String to = transFormat.format(dbsys);

	            	db_sysdate = to;

	        	}
	            String[] temp_db_sysdate = db_sysdate.split("-"); 
	            default_date = new int[temp_db_sysdate.length];
	            
//	            default_date = db_sysdate.split("-");
	            for (int i = 0; i < temp_db_sysdate.length; i++) {
	            	default_date[i] = Integer.parseInt(temp_db_sysdate[i]);
	            }
	            
				
	            select_data.close();
				conn.close();

	            
			} catch (SQLException e) {
	            System.out.println("getConnection 하다가 문제 생김");
	        }
		}

	public Receipt() {
		super();
		super.setTitle("영수증 관리");
		
		setLayout(null);
		main = getContentPane(); // 메인 컨테이너 선언

		connection(); // 영수증 정보 커넥션

		receipt_print(); // 영수증 출력 함수
		
		payBox(); // 영수증 아래 박스 출력 함수
		
		

		// 메인으로 가는 버튼
		

		String message = "Main";

		JButton  tomain = new JButton(message);

		JPanel frame = new JPanel();

		// 달력 출력
		JPanel p1 = new JPanel();

		JPanel p3 = new JPanel(new FlowLayout());

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
		
		DBsysdate();
		model1.setDate(default_date[0], default_date[1]-1, default_date[2]);
		model1.setSelected(true);
		
		model2.setDate(default_date[0], default_date[1]-1, default_date[2]);
		model2.setSelected(true);


		p3.add(datePicker);
		p3.add(new JLabel("~"));
		p3.add(datePicker2);
		p3.add(selBtn);

		p1.add(p3);
		

		total();
		

		if (state.equals("complete")) { // 영수증 종류 확인
			receipt = new JLabel(receipt_string);
		} else {
			receipt = new JLabel(return_string);
		}

		ArrayList<JButton> buttons = new ArrayList<>();

		buttons.add(new JButton("전표반품")); // 0
		buttons.add(new JButton("재인쇄"));
		buttons.add(new JButton("현금영수증"));

		buttons.add(new JButton("결제변경"));
		buttons.add(new JButton("전체"));
		buttons.add(new JButton("현금"));
		buttons.add(new JButton("신용카드")); // 6

		// 버튼 위치 조정
		buttons.get(0).setBounds(190, 30, 150, 80);
		buttons.get(1).setBounds(360, 30, 150, 80);
		buttons.get(2).setBounds(530, 30, 150, 80);
		buttons.get(3).setBounds(730, 30, 120, 80);
		buttons.get(4).setBounds(870, 30, 120, 80);
		buttons.get(5).setBounds(1010, 30, 120, 80);
		buttons.get(6).setBounds(1150, 30, 120, 80);

		p1.setBounds(20, 130, 660, 50); // 조회 패널

		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).setFont(new Font("MV Bold", Font.BOLD, 20));
		}
		// 메인으로 가는 버튼 세부 설정 
		tomain.setBounds(20, 30, 150, 80);
//		tomain.setBackground(new Color(0xffffff));
		tomain.setFont(new Font("MV Bold", Font.BOLD, 25));
		tomain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main_test mt = new main_test();
				mt.setDefaultOptions();
				dispose();
			}
		});
		
		receipt.setFont(new Font("MV Bold", Font.BOLD, 20));
		receipt.setOpaque(true); // 백그라운드 색상 허용
		receipt.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 표시 설정
		receipt.setVerticalAlignment(JLabel.NORTH);
		; // 세로 가운데 표시 설정
		receipt.setBackground(new Color(0xffffff));// 라벨 배경색
		receipt.setBounds(730, 140, 450, 500);
//		frame.setBackground(new Color(0xffffff)); // 패널 배경색
		frame.setBounds(730, 140, 540, 500);
		receipt.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane( // 영수증에 수직 스크롤바 추가
				receipt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(540, 500)); // 스크롤바 크기조정

		frame.add(scrollPane);

		// 좌측 위 버튼 추가
		main.add(buttons.get(0));
		main.add(buttons.get(1));
		main.add(buttons.get(2));
		main.add(buttons.get(3));

		main.add(tomain); // 메인으로 가는 버튼
		main.add(p1); // 날짜 조회 출력

		// 우측 위 버튼
		main.add(buttons.get(4));
		main.add(buttons.get(5));
		main.add(buttons.get(6));

		main.add(frame);

		// ================================================================================================
		// ================================================================================================
		
		// 날짜 리스트 만드는 곳 

		

		JPanel receipt_panel = new JPanel();

		receipt_panel.setLocation(20, 170);
		receipt_panel.setSize(660, 500);

		DefaultTableModel model = new DefaultTableModel(data_default, columnNames);

		JTable table = new JTable(model);

		JScrollPane scrollPane1 = new JScrollPane(table);
		scrollPane1.setBorder(BorderFactory.createEmptyBorder());

		table.getTableHeader().setPreferredSize(new Dimension(scrollPane1.getWidth(), 50));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
		table.getColumnModel().getColumn(10).setPreferredWidth(200);

		scrollPane1.setPreferredSize(new Dimension(658, 495));

		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);

		receipt_panel.add(scrollPane1);
		add(receipt_panel);

		// ================================================================================================
		// 테이블 내에 어떤 행을 선택했을때 그 데이터의 영수증 번호를 가져오기 위한 액션
		// ================================================================================================

		ListSelectionModel selection = table.getSelectionModel();
     	
     	selection.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if (e.getValueIsAdjusting()) {
					
					try {
						
						no = table.getSelectedRow();
						select_receipt_no_string = "" + table.getValueAt(table.getSelectedRow(), 2);
						
						state_chk = "" + table.getValueAt(table.getSelectedRow(), 1);
						
						select_receipt_no = Integer.parseInt(select_receipt_no_string);
						
						RECEIPT_NO = select_receipt_no_string;
						
						connection();
						
						receipt_print();
						
						if (state.equals("complete")) {
							receipt.setText(receipt_string); 
						} else {
							receipt.setText(return_string); 
						}
						
						main.add(frame);
						payBox();
						
					} catch (Exception a) {
						a.printStackTrace();
					}
					
				}
			}
		});

		// ================================================================================================
		// '전표반품' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(0).addActionListener(new ActionListener() {
     		
     		@Override
     		public void actionPerformed(ActionEvent e) {
     			
     			if (select_receipt_no > 0 ) {
     			
	     			String a = "" + table.getValueAt(table.getSelectedRow(), 3);
	     			
	     			// JOptionPane.showConfirmDialog의 결과가 숫자로 반환된다.
	     			// X 표를 눌러 닫은 경우 = -1
	     			// 예 = 0
	     			// 아니오 = 1
	     			int yes_or_no = JOptionPane.showConfirmDialog(null, a + "원 결제하셨습니다. 반품 하시겠습니까?", "반품", JOptionPane.YES_NO_OPTION);
	     			
	     			if (state_chk.equals("complete")) {
	     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {
	     				// 예 아니오 선택없이 창 닫은경우
	     					
	     				} else if (yes_or_no == JOptionPane.YES_OPTION) {
	     				// 사용자가 예를 선택한경우
	     					refund(select_receipt_no);
	     					
	     					
	     					while(model.getRowCount() > 0) {
	         				   model.removeRow(0);
	         			   }
	     	
	         			   data_change = table_change(date_s_e);
	     	
	         			   if (data_change.length == 0) {
	         				   JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
	         			   }
	     	
	     		 			
	         			   for (int i = 0; i < data_change.length; i++) {
	         				   model.addRow(data_change[i]);
	         			   }
	         			   
	         			  JOptionPane.showMessageDialog(null, "반품이 완료되었습니다.");
	     					
	     					
	     				} else {
	     				// 사용자가 아니오를 선택한경우
	     					JOptionPane.showMessageDialog(null, "취소하셨습니다.");
	     				}
	     			}
     			} else {
     	  			  JOptionPane.showMessageDialog(null, "변경하실 내역을 선택 후 실행해주세요.");
       		   }
     			
     		}
     	});
		// ================================================================================================
		// '재인쇄' 버튼 눌렀을때의 액션 // 승민 
		// ================================================================================================
		buttons.get(1).addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_receipt_no > 0 ) {
					DateTimeFormatter receipt_time_format = 
							DateTimeFormatter.ofPattern("y년 M월 d일 HH:mm:ss");
					
					String receipt_time = receipt_time_format.format(LocalDateTime.now());
					
					File f = new File("src/receipt/reprint/" + receipt_time.replace(":", ".") +".txt");
					
					
					try (
						FileOutputStream fout = new FileOutputStream(f, true);
						BufferedOutputStream bout = new BufferedOutputStream(fout);
						PrintStream out = new PrintStream(bout);
							
					) {
						if (state.equals("complete")) { // 영수증 종류 확인
							out.write(receipt_string.getBytes());
						} else {
							out.write(return_string.getBytes());
						}
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "재인쇄 완료.");
				} else {
	    			  JOptionPane.showMessageDialog(null, "변경하실 내역을 선택 후 실행해주세요.");
	    		}
				
			}
		});

		// ================================================================================================
		// '현금영수증' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(2).addActionListener(new ActionListener() {
     		
     		@Override
     		public void actionPerformed(ActionEvent e) {
     			
     			if (select_receipt_no > 0 ) {
     			
	     			String a = "" + table.getValueAt(table.getSelectedRow(), 3);
	     			
	     			// JOptionPane.showConfirmDialog의 결과가 숫자로 반환된다.
	     			// X 표를 눌러 닫은 경우 = -1
	     			// 예 = 0
	     			// 아니오 = 1
	     			
	     			String[] check = cash_receipt(select_receipt_no);
	     			
	     			// 현금결제금액과 카드결제금액, 현금영수증처리 유무를 받아오기 
//	     			check = cash_receipt(select_receipt_no);
	     			
	     			// check[0] = 현금결제금액		
	     			// check[1] = 카드결제금액		
	     			// check[2] = 현금영수증처리 유무
	     			
	     			int yes_or_no;
	     			
	     			if (check[2].equals("Y")) {
	     				 
	     				yes_or_no = JOptionPane.showConfirmDialog(null, "이미 현금영수증 처리를 한 상태입니다. 취소하시겠습니까?", "현금영수증 취소", JOptionPane.YES_NO_OPTION);
	     				
	     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {	// 예 아니오 선택없이 창 닫은경우
	     					
	     				} else if (yes_or_no == JOptionPane.YES_OPTION) {	// 사용자가 예를 선택한경우
	     				
	     					cash_receipt_executive(select_receipt_no, "Y");
	     					
	     					
	     					
	     					while(model.getRowCount() > 0) {
	         				   model.removeRow(0);
	         			   }
	     	
	         			   data_change = table_change(date_s_e);
	     	
	         			   if (data_change.length == 0) {
	         				   JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
	         			   }
	     	
	     		 			
	         			   for (int i = 0; i < data_change.length; i++) {
	         				   model.addRow(data_change[i]);
	         			   }
	         			   
	         			  JOptionPane.showMessageDialog(null, "현금영수증을 취소처리 하였습니다.");
	         			   
	     					
	     				} else {	// 사용자가 아니오를 선택한경우
	     				
	     				}
	     				
	     			} else if (check[2].equals("N") && check[0].equals("0")) {
	     				// 현금 결제금액이 없는 경우 
	     				JOptionPane.showMessageDialog(null, "현금 결제 금액이 없습니다.");
	     				
	     			} else if (check[2].equals("N") && !check[0].equals("0")) {
	     				// 카드 결제금액의 유무와 상관없이 현금 결제 금액이 있다면 현금영수증 처리를 할지 물어봄
	     				
	     				yes_or_no = JOptionPane.showConfirmDialog(null, "현금으로 " + check[0] + "원 결제하셨습니다. 현금영수증 처리를 하시겠습니까?", "현금영수증 처리", JOptionPane.YES_NO_OPTION);
	     				
	     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {
	     				// 예 아니오 선택없이 창 닫은경우
	     					
	     				} else if (yes_or_no == JOptionPane.YES_OPTION) {
	     				// 사용자가 예를 선택한경우
	     					cash_receipt_executive(select_receipt_no, "N");
	     					
	     					
	     					
	     					while(model.getRowCount() > 0) {
	         				   model.removeRow(0);
	         			   }
	     	
	         			   data_change = table_change(date_s_e);
	     	
	         			   if (data_change.length == 0) {
	         				   JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
	         			   }
	     	
	     		 			
	         			   for (int i = 0; i < data_change.length; i++) {
	         				   model.addRow(data_change[i]);
	         			   }
	         			   
	         			  JOptionPane.showMessageDialog(null, "현금영수증을 처리 하였습니다.");
	     					
	     				} else {
	     				// 사용자가 아니오를 선택한경우
	     					JOptionPane.showMessageDialog(null, "현금영수증 처리를 취소하셨습니다.");
	     				}	
		
	     			}
     		}else {
  			  JOptionPane.showMessageDialog(null, "변경하실 내역을 선택 후 실행해주세요.");
  		   }
     			
     		}
     	});

		// ================================================================================================
		// '결제변경' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(3).addActionListener(new ActionListener() {
    	    
     	   @Override
     		public void actionPerformed(ActionEvent e) {
     			
     		  if (select_receipt_no > 0 ) {
    			   payment_change a = new payment_change(select_receipt_no);
    			   
    			   while(model.getRowCount() > 0) {
    				   model.removeRow(0);
    			   }
	
    			   data_change = table_change(date_s_e);
	
    			   if (data_change.length == 0) {
    				   JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
    			   }
    			   
    			   for (int i = 0; i < data_change.length; i++) {
    				   model.addRow(data_change[i]);
    			   }
    			   
    		   } else {
    			  JOptionPane.showMessageDialog(null, "변경하실 내역을 선택 후 실행해주세요.");
    		   }
     		  
     		}
     		
     	});

		// ================================================================================================
		// '전체' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(4).addActionListener(new ActionListener() {
     	   
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			
    			if (datePicker.getJFormattedTextField().getText().length() > 0 ) {
					date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					
					date_s_e = " DATETIME BETWEEN " + date_s + " and " + date_e;
				}
    			
    			while(model.getRowCount() > 0) {
    				model.removeRow(0);
    			}
    			
    			total_data data = new total_data();
    			data_default = data.table_total_data(date_s_e);
    			
    			if (data_default.length == 0) {
       				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
       			}
    			
    			for (int i = 0; i < data_default.length; i++) {
    				model.addRow(data_default[i]);
    			}
    		}
    		
    	});
		// ================================================================================================
		// '현금' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(5).addActionListener(new ActionListener() {
     	   
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			
    			if (datePicker.getJFormattedTextField().getText().length() > 0 ) {
					date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					
					date_s_e = " DATETIME BETWEEN " + date_s + " and " + date_e;
				}
    			
    			while(model.getRowCount() > 0) {
    				model.removeRow(0);
    			}
    			
    			cash_data data = new cash_data();
    			data_cash = data.table_cash_data(date_s_e);


       			if (data_cash.length == 0) {
       				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
       			}

    			
				for (int i = 0; i < data_cash.length; i++) {
					model.addRow(data_cash[i]);
				}
    			
    		}
    		
    	});
		// ================================================================================================
		// '신용카드' 버튼을 눌렀을때의 액션
		// ================================================================================================

		buttons.get(6).addActionListener(new ActionListener() {
        	   
       		@Override
       		public void actionPerformed(ActionEvent e) {
       			
       			if (datePicker.getJFormattedTextField().getText().length() > 0 ) {
					date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					
					date_s_e = " DATETIME BETWEEN " + date_s + " and " + date_e;
				}
       			
       			while(model.getRowCount() > 0) {
    				model.removeRow(0);
    			}
       			
       			credit_data data = new credit_data();
       			
       			data_credit = data.table_credit_data(date_s_e);
       			
       			if (data_credit.length == 0) {
       				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
       			}

       			for (int i = 0; i < data_credit.length; i++) {
    				model.addRow(data_credit[i]);
    			}

       		}
       		
       	}); 

		// ================================================================================================
		selBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (datePicker.getJFormattedTextField().getText().length() > 0 ) {
					date_s = "TO_DATE('" + datePicker.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					date_e = "TO_DATE('" + datePicker2.getJFormattedTextField().getText() + "', 'YYYY/MM/DD')";
					
					date_s_e = " DATETIME BETWEEN " + date_s + " and " + date_e;
				}
				
				
//				Receipt_list = "SELECT * FROM payment_view_2 WHERE TO_DATE(DATETIME, 'YYYY/MM/DD') BETWEEN " + date_s + " AND " + date_e
//						+ " + 1";
				
				while(model.getRowCount() > 0) {
    				model.removeRow(0);
    			}

    			data_change = table_change(date_s_e);

    			if (data_change.length == 0) {
       				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.");
       			}

    			
    			for (int i = 0; i < data_change.length; i++) {
    				model.addRow(data_change[i]);
    			}
    			

			}
		});
		
		// ================================================================================================

	}

	private static void receipt_print() {
		String product = "";
		// 받아온 HashMap 음료 수량 가격 영수증에 출력
		for (Entry<String, ArrayList<Integer>> entry : drink.entrySet()) {
			String print = "";
			int price = 0;
			for (int number : entry.getValue()) {
				price += number;
			}
			print += "<tr>" 
					+ "<td>" 
					+ entry.getKey() 
					+ "</td>" 
					+ "<td style='text-align:left;'>"
					+ formatter.format(entry.getValue().get(0)) 
					+ "</td>" 
					+ "<td style='text-align:center;'>"
					+ entry.getValue().size() 
					+ "</td>" 
					+ "<td style='text-align:right;'>" 
					+ formatter.format(price)
					+ "</td>" 
					+ "</tr>";
			product += print;
		}
//		----------------------사용한 결제금액 bill, pay에 출력
		String bill = "";
		info = "";
		pay = 0;
		if (cash != 0) {
			bill += "<table style='width:100%;'>" 
					+ "<tr>" 
					+ "<td>현&emsp금</td>"
					+ "<td style='text-align:right;'>"
					+ formatter.format(cash) 
					+ "원</td>"
					+ "</tr>" 
					+ "</table>";
			if (info.equals("")) {
				info += "현금";
			} else {
				info += ",현금";
			}

			pay += cash;

		}
		if (credit != 0) {
			bill += "<table style='width:100%;'>"
					+ "<tr>" 
					+ "<td>카&emsp드</td>" 
					+ "<td style='text-align:right;'>"
					+ formatter.format(credit) 
					+ "원</td>"
					+ "</tr>" 
					+ "</table>";
			if (info.equals("")) {
				info += "카드";
			} else {
				info += ",카드";
			}

			pay += credit;

		}
		if (point_used != 0) {
			bill += "<table style='width:100%;'>" 
					+ "<tr>" 
					+ "<td>사용 포인트</td>" 
					+ "<td style='text-align:right;'>"
					+ formatter.format(point_used) 
					+ "점</td>"
					+ "</tr>"
					+ "</table>"
					+ "<table style='width:100%;'>"
					+ "<tr>"
					+ "<td>남은 포인트</td>" 
					+ "<td style='text-align:right;'>" 
					+ formatter.format(point) 
					+ "점</td>"
					+ "</tr>" 
					+ "</table>";
			if (info.equals("")) {
				info += "포인트";
			} else {
				info += ",포인트";
			}

			pay += point_used;

		}
		
		if (receipt_chk.equals("Y")) { // 현금 영수증 체크
			bill += "<br>[현금영수증 발급]" + "<br>발급 용도: 소비자 소득공제";
		} else if (receipt_chk.equals("N")) {
			bill += "<br>[현금영수증 미발급]";
		}
		// 영수증 시간 LocalDateTime로 변환 후 String으로 변환

		LocalDateTime localDateTime = new java.sql.Timestamp(transaction_date.getTime()).toLocalDateTime();

		DateTimeFormatter my_date_format2 = DateTimeFormatter.ofPattern("y년 M월 d일 HH:mm:ss");

		DateTimeFormatter my_date_format3 = DateTimeFormatter.ofPattern("M월 d일");

		LocalDateTime returnDay = localDateTime.plusDays(30);
		String transaction_date_string = my_date_format2.format(localDateTime); // 판매일자
		String returnDayString = my_date_format3.format(returnDay); // 반품가능일자

		return_string = "<html><center><br>" 
				+ "환&emsp불&emsp전&emsp표" 
				+ "<br>" 
				+ "<table style='width:100%;'>" 
				+ "<tr>"
				+ "<td>거래일시: </td>" 
				+ "<td style='text-align:right;'>" 
				+ transaction_date_string + "</td>" + "</tr>"
				+ "</table>" 
				+ "-------------------------------------------------------------------"
				+ "<br>*정부방침에 의해 교환/환불은 반드시 영수증을" + "<br>지참하셔야 하며, 카드결제는 30일" 
				+ "(환불완료)" 
				+ "<br>이내 카드와 영수증 지참 시 가능합니다."
				+ "<br>-------------------------------------------------------------------" 
				+ "</center>"

				+ "<table style='width:100%;'>" 
				+ "<tr>" 
				+ "<td>품명</td>" 
				+ "<td style='text-align:right;'>단가</td>"
				+ "<td style='text-align:right;'>수량</td>" 
				+ "<td style='text-align:right;'>금액</td>" 
				+ "</tr>"
				+ "</table>" 
				+ "-------------------------------------------------------------------"
				+ "<table style='width:100%;'>" 
				+ product 
				+ "</table>"
				+ "-------------------------------------------------------------------" 
				+ "<table style='width:100%;'>"
				+ "<tr>" 
				+ "<td>과세매출</td>" 
				+ "<td style='text-align:right;'>" 
				+ formatter.format(vat * 10 - vat)
				+ "원</td>" 
				+ "</tr>"

				+ "<tr>" 
				+ "<td>부가세</td>" 
				+ "<td style='text-align:right;'>" 
				+ formatter.format(vat) 
				+ "원</td>"
				+ "</tr>"

				+ "<tr>" 
				+ "<td>환불금액</td>" 
				+ "<td style='text-align:right;'>" 
				+ formatter.format(total) 
				+ "원</td>"
				+ "</tr>" 
				+ "</table>" 
				+ "-------------------------------------------------------------------" 
				+ bill
				+ "<br>-------------------------------------------------------------------"
				+ "<br>[광고]스마일게이트 알피지의 차세대<br> 핵&슬래쉬 MMORPG 로스트아크."
				+ "<br>-------------------------------------------------------------------"
				+ "<center>감사합니다.<br><br> </center>" + "</html>";
		receipt_string = // 영수증 전체 내용
				"<html><center><br>" 
				+ "매&emsp출&emsp전&emsp표" 
				+ "<br>" 
				+ "<table style='width:100%;'>" + "<tr>"
				+ "<td>거래일시: </td>" + "<td style='text-align:right;'>"
				+ transaction_date_string 
				+ "</td>"
				+ "</tr>" + "</table>" + "-------------------------------------------------------------------"
				+ "<br>*정부방침에 의해 교환/환불은 반드시 영수증을" + "<br>지참하셔야 하며, 카드결제는 30일" 
				+ "(" + returnDayString + ")" // 데이터베이스에서 거래날짜 + 30
																											
				+ "<br>이내 카드와 영수증 지참 시 가능합니다."
				+ "<br>-------------------------------------------------------------------" 
				+ "</center>"

				+ "<table style='width:100%;'>" + "<tr>" + "<td>품명</td>"
				+ "<td style='text-align:right;'>단가</td>" + "<td style='text-align:right;'>수량</td>"
				+ "<td style='text-align:right;'>금액</td>" + "</tr>" + "</table>"
				+ "-------------------------------------------------------------------"
				+ "<table style='width:100%;'>" 
				+ product 
				+ "</table>"
				+ "-------------------------------------------------------------------"
				+ "<table style='width:100%;'>"
				+ "<tr>" + "<td>과세매출</td>" 
				+ "<td style='text-align:right;'>"
				+ formatter.format(vat * 10 - vat)
				+ "원</td>"
				+ "</tr>"

				+ "<tr>" 
				+ "<td>부가세</td>" 
				+ "<td style='text-align:right;'>"
				+ formatter.format(vat) 
				+ "원</td>"
				+ "</tr>"
				+ "<tr>" 
				+ "<td>합&emsp계</td>"
				+ "<td style='text-align:right;'>"
				+ formatter.format(total)
				+ "원</td>" + "</tr>" + "</table>"
				+ "-------------------------------------------------------------------"
				+ bill
				+ "<br>-------------------------------------------------------------------"
				+ "<br>[광고]스마일게이트 알피지의 차세대<br> 핵&슬래쉬 MMORPG 로스트아크."
				+ "<br>-------------------------------------------------------------------"
				+ "<center>감사합니다.<br><br> </center>" + "</html>";

		// &nbsp 띄어쓰기 &emsp 크게 띄어쓰기
		// <html> + + <br> + + </html> 줄 바꾸는 법
	}
	
	private static void payBox() {
		// 영수증 아래 박스
				String[] columnNames_yoo = { "결제구분", "금액" };
				String[][] data_yoo = {{ info, formatter.format(pay) }};

				JTable table_yoo = new JTable(data_yoo, columnNames_yoo);
				JScrollPane scrollPane_yoo = new JScrollPane(table_yoo);

				table_yoo.getTableHeader().setPreferredSize(new Dimension(scrollPane_yoo.getWidth(), 30));
				scrollPane_yoo.setBounds(730, 670, 540, 70);
				table_yoo.getTableHeader().setFont(new Font("Small Fonts", Font.BOLD, 22));
				table_yoo.getTableHeader().setBackground(new Color(0xffffff));
				table_yoo.setCellSelectionEnabled(true);

				table_yoo.setRowHeight(37);
				table_yoo.setAlignmentX(JTable.TOP_ALIGNMENT);
				table_yoo.setShowGrid(true);
				table_yoo.setFont(new Font("Small Fonts", Font.PLAIN, 20));

				main.add(scrollPane_yoo);
	}

	private static void connection() {

		try { 
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL", "cafe",
					"!!22Qorthdud");

			String sql = "select * from PAYMENT_VIEW WHERE RECEIPT_NO = " + RECEIPT_NO;

			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			drink = new HashMap<>();
			while (rs.next()) {
				// HashMap에 음료수 내역 넣기
				drink.putIfAbsent(rs.getString("MENU"), new ArrayList<>());

				drink.get(rs.getString("MENU")).add(rs.getInt("PRICE"));
				state = rs.getString("STATE");
				total = rs.getInt("TOTAL");
				vat = rs.getInt("TOTAL") / 10;
				cash = rs.getInt("CASH");
				point_used = rs.getInt("POINT_USED");
				point = rs.getInt("POINT");
				credit = rs.getInt("CREDIT");
				receipt_chk = rs.getString("RECEIPT_CHK");
				transaction_date = rs.getDate("DATETIME");
			}

			rs.close();
			pstmt.close();
			conn.close(); 

		} catch (SQLException e) {
			System.out.println("getConnection 하다가 문제 생김");
		}
	}

	public static void main(String[] args) {
		Receipt frame = new Receipt();
		frame.setDefaultOptions();
	}
}