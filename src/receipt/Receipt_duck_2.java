package receipt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import baseSettings.PosFrame;

public class Receipt_duck_2 extends PosFrame {
	
	static ArrayList<ArrayList<String>> list_data_default = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> list_data_cash = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> list_data_credit = new ArrayList<ArrayList<String>>();
	
	static String[] columnNames = null;
	
	static String Receipt_list = "select * from payment_view_2";
	static String Receipt_list_cash = Receipt_list + " where cash > 0";
	static String Receipt_list_credit = Receipt_list + " where credit > 0";
	
//	static String Receipt_list_cash = "select * from payment_view_1 where cash > 0";
//	static String Receipt_list_credit = "select * from payment_view_1 where credit > 0";
	
	static String refund_sql = "UPDATE history_payment SET state = 'cancel' WHERE receipt_no = ";
	static String cash_receipt_chk = "select cash, credit, receipt_chk from history_payment WHERE receipt_no = ";
	static String cash_receipt = "UPDATE history_payment SET receipt_chk = 'Y' WHERE receipt_no = ";
	static String cash_receipt_cancel = "UPDATE history_payment SET receipt_chk = 'N' WHERE receipt_no = ";
	
	static String[][] data_default = null;
	static String[][] data_cash = null;
	static String[][] data_credit = null;
	
	static int select_receipt_no = -1;
	static String select_receipt_no_string = "";
	
	static int w_size, h_size;
	static int cash_w_size, cash_h_size;
	static int credit_w_size, credit_h_size;
	
	static int no;				// 
	static String state_chk;	// 현재의 결제상태
	
	static String[] cash_receipt_result;
	
	
	public void total() {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================
            	
            	// 기본 디폴트 리스트 
            	PreparedStatement pstmt_Receipt_list = conn.prepareStatement(Receipt_list);
            	PreparedStatement pstmt_Receipt_list_cash = conn.prepareStatement(Receipt_list_cash);
            	PreparedStatement pstmt_Receipt_list_credit = conn.prepareStatement(Receipt_list_credit);
            	
            	ResultSet rs_list = pstmt_Receipt_list.executeQuery();
            	ResultSet rs_list_cash = pstmt_Receipt_list_cash.executeQuery();
            	ResultSet rs_list_credit = pstmt_Receipt_list_credit.executeQuery();
            	
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
            	
            	// 기존에 담겨있던 데이터를 비움
            	list_data_cash.clear();
            	
            	// 가져온 데이터를 list_data_cash(ArrayList) 에 저장
            	while (rs_list_cash.next()) {
            		
            		ArrayList<String> data_cash = new ArrayList<>();
            		data_cash.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data_cash.add("" + i);
            			} else {
            				data_cash.add(rs_list_cash.getString(columnNames[i]));
            			}
            		}
            		list_data_cash.add(data_cash);
            	}
            	
            	// 기존에 담겨있던 데이터를 비움
            	list_data_credit.clear();
            	
            	// 가져온 데이터를 list_data_credit(ArrayList) 에 저장
            	while (rs_list_credit.next()) {
            		ArrayList<String> data = new ArrayList<>();
            		data.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data.add("" + i);
            			} else {
            				data.add(rs_list_credit.getString(columnNames[i]));
            			}
            		}
            		list_data_credit.add(data);
            	}
            	
            	// 오픈 했던 각 리소스들 종료 
            	rs_list.close();
            	rs_list_cash.close();
            	rs_list_credit.close();
            	
            	pstmt_Receipt_list.close();
            	pstmt_Receipt_list_cash.close();
            	pstmt_Receipt_list_credit.close();
            	
            	conn.close();
            
            // ================================================================================================
        	// ================================================================================================
//            	System.out.println("w_size : " + w_size);
//            	System.out.println("cash_w_size : " + cash_w_size);
//            	System.out.println("credit_w_size : " + credit_w_size);
            	// JTable에 담길 데이터의 사이즈를 설정하기 위한 각 데이터의 사이즈 구하기
            	w_size = list_data_default.size();
            	h_size = list_data_default.get(0).size();
            	cash_w_size = list_data_cash.size();
            	cash_h_size = list_data_cash.get(0).size();
            	credit_w_size = list_data_credit.size();
            	credit_h_size = list_data_credit.get(0).size();
//            	
//            	System.out.println("w_size : " + w_size);
//            	System.out.println("cash_w_size : " + cash_w_size);
//            	System.out.println("credit_w_size : " + credit_w_size);
//            	
            	// 구한 각 데이터의 사이즈를 가지고 JTable의 크기 설정
             	data_default = new String[w_size][h_size];
             	data_cash = new String[cash_w_size][cash_h_size];
             	data_credit = new String[credit_w_size][credit_h_size];
             	
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
             	
             	// 현금(Cash) 데이터를 JTable에 적용할 배열에 저장
             	for (int i = 0; i < cash_w_size; i++) {
            		for (int x = 0; x < cash_h_size; x++) {
            			if (x == 0) {
            				data_cash[i][x] = "" + (i+1);
            			}else {
            				data_cash[i][x] = list_data_cash.get(i).get(x);
            			}
            		}
            	}
             	
             	// 카드(Credit) 데이터를 JTable에 적용할 배열에 저장
             	for (int i = 0; i < credit_w_size; i++) {
            		for (int x = 0; x < credit_h_size; x++) {
            			if (x == 0) {
            				data_credit[i][x] = "" + (i+1);
            			}else {
        	    			data_credit[i][x] = list_data_credit.get(i).get(x);
            			}
            		}
            	}
             	
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	
	// 반품 처리
	public void refund(int receipt_no) {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            refund_sql += ("" + receipt_no);
            System.out.println(refund_sql);
            
            PreparedStatement refund = conn.prepareStatement(refund_sql);
            
			int row = refund.executeUpdate();
			
//			System.out.println(row + "row(s) changed.");
			
			refund.close();
			conn.close();
			total();
            
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
	            cash_receipt_chk += ("" + receipt_no);
	            System.out.println(cash_receipt_chk);
	            
	            PreparedStatement cash_receipt_yn = conn.prepareStatement(cash_receipt_chk);
	            ResultSet rs_cash_receipt_yn = cash_receipt_yn.executeQuery();
	            
	            cash_receipt_result = new String[3];
	            
	            while (rs_cash_receipt_yn.next()) {
//	            	System.out.println(rs_cash_receipt_yn.getString("receipt_chk"));
	            	cash_receipt_result[0] = rs_cash_receipt_yn.getString("cash");
	            	cash_receipt_result[1] = rs_cash_receipt_yn.getString("credit");
	            	cash_receipt_result[2] = rs_cash_receipt_yn.getString("receipt_chk");
            	}
	            
				cash_receipt_yn.close();
				conn.close();
				total();
	            
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
				total();
	            
			} catch (SQLException e) {
	            System.out.println("getConnection 하다가 문제 생김");
	        }
			
			
		}
	
	
	
	
	public Receipt_duck_2() {
		super();
		super.setTitle("영수증 관리");
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");

            String sql = "select * from PAYMENT_VIEW WHERE RECEIPT_NO = 24" + "";
//            
//            
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            ResultSet rs = pstmt.executeQuery();

//            while (rs.next()) {
//                System.out.print(rs.getString("MENU"));
//                System.out.print(rs.getInt("PRICE"));
//                System.out.println();
//            }

            // 6. 다 사용한 연결을 나중에 연 순서대로 닫아준다
//            rs.close();
//            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
		setLayout(null);
		
		// 시간을 문자열로 변경하기
		DateTimeFormatter my_date_format = 
				DateTimeFormatter.ofPattern("y년 M월 d일");
		
		String message = my_date_format.format(LocalDate.now());

		JButton date = new JButton(message);
		String product = "";
		
		String string = // 영수증 전체 내용 
				"<html><center>-------------------------------------------------------------------"
				+ "<br>*정부방침에 의해 교환/환불은 반드시 영수증을"
				+ "<br>지참하셔야 하며, 카드결제는 30일(09월08일)"
				+ "<br>이내 카드와 영수증 지참 시 가능합니다."
				+ "<br>-------------------------------------------------------------------"
				+ "</center>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>품명</td>"
				+ "<td style='text-align:center;'>단가</td>"
				+ "<td style='text-align:center;'>수량</td>"
				+ "<td style='text-align:right;'>금액</td>"
				+ "</tr>"
				+ "</table>"
				+ "-------------------------------------------------------------------"
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>과세매출</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>부가세</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>합&emsp계</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>현&emsp금</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>현&emsp금</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>현&emsp금</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				
				+ "</html>";
		
		
		
		
		
		// &nbsp 띄어쓰기 &emsp 크게 띄어쓰기
		// <html> +  + <br> + + <p>  + + </html> 줄 바꾸는 법
		
		JPanel frame = new JPanel();
		
		JLabel receipt = new JLabel(string);
		
		
		
		ArrayList<JButton> buttons = new ArrayList<>();
		
		
		
		buttons.add(new JButton("전표반품")); // 0 
		buttons.add(new JButton("재인쇄"));
		buttons.add(new JButton("현금영수증"));
		
		
		buttons.add(new JButton("결제변경"));
		buttons.add(new JButton("전체"));
		buttons.add(new JButton("현금"));
		buttons.add(new JButton("신용카드")); // 6
		
		// 버튼 위치 조정
		buttons.get(0).setBounds(20, 30, 150, 80);
		buttons.get(1).setBounds(190, 30, 150, 80);
		buttons.get(2).setBounds(360, 30, 150, 80);
		buttons.get(3).setBounds(530, 30, 150, 80);
		
		buttons.get(4).setBounds(870, 30, 120, 80);
		buttons.get(5).setBounds(1010, 30, 120, 80);
		buttons.get(6).setBounds(1150, 30, 120, 80);
		
		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).setFont(new Font("MV Bold", Font.BOLD, 20));
		}
		
		date.setBounds(730, 30, 120, 80);
		date.setBackground(new Color(0xffffff));
		
		receipt.setFont(new Font("MV Bold", Font.BOLD, 20));
		receipt.setOpaque(true); // 백그라운드 색상 허용
		receipt.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 표시 설정
		receipt.setVerticalAlignment(JLabel.NORTH);; // 세로 가운데 표시 설정
		receipt.setBackground(new Color(0xffffff));// 라벨 배경색
//		receipt.setBounds(730, 140, 540, 2000);
//		frame.setBackground(new Color(0xffffff)); // 패널 배경색
		frame.setBounds(730, 140, 540, 500);
		receipt.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane( // 영수증에 수직 스크롤바 추가
				receipt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(540, 500)); // 스크롤바 크기조정
		
		frame.add(scrollPane);
		
		// 좌측 위 버튼 추가
		add(buttons.get(0));
		add(buttons.get(1));
		add(buttons.get(2));
		add(buttons.get(3));
		
		add(date); // 날짜 출력
		
		// 우측 위 버튼
		add(buttons.get(4));
		add(buttons.get(5));
		add(buttons.get(6));
		
        add(frame);
        

     // ================================================================================================
     // ================================================================================================
        
        total();
//        cash_list();
//        credit_list();

        JPanel receipt_panel = new JPanel();        
        
     	receipt_panel.setBackground(Color.black);
     	receipt_panel.setLocation(20, 140);
     	receipt_panel.setSize(660, 500);

//     	JPanel a = new JPanel();
//     	a.setSize(20,20);
     	
     	
     	DefaultTableModel model = new DefaultTableModel(data_default, columnNames);
//     	JTable table = new JTable(data_default, columnNames);
		JTable table = new JTable(model);
     	
     	JScrollPane scrollPane1 = new JScrollPane(table);
     	scrollPane1.setBorder(BorderFactory.createEmptyBorder());
//     	scrollPane1.setBounds(20, 120, 6600, 4700);
     	
//     	scrollPane1.setBackground(Color.pink);
     	
//     	table.setPreferredSize(new Dimension(660, 1500));
//     	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     	
     	table.getTableHeader().setPreferredSize(new Dimension(scrollPane1.getWidth(), 50));
     	
     	scrollPane1.setPreferredSize(new Dimension(658, 495));
//     	scrollPane1.setPreferredSize(660, 1500);
//     	scrollPane1.setSize(6600, 1500);
     	
     	table.setRowSelectionAllowed(true);
     	table.setColumnSelectionAllowed(false);
//     	System.out.println(scrollPane1.getSize(getPreferredSize()));
     	
//     	table.setShowGrid(true);
     	
//     	a.add(scrollPane1);
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
//						System.out.println("w_size : " + w_size + "\tcash_w_size : " + cash_w_size + "\tcredit_w_size : " + credit_w_size);
//						System.out.println("model.getColumnCount() : " + model.getValueAt(table.getSelectedRow(), 2));
						
						no = table.getSelectedRow();
						select_receipt_no_string = "" + table.getValueAt(table.getSelectedRow(), 2);
						
						state_chk = "" + table.getValueAt(table.getSelectedRow(), 1);
//						System.out.println("ddf : " + state_chk);
						
						select_receipt_no = Integer.parseInt(select_receipt_no_string);
						
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
     			
     			String a = "" + table.getValueAt(table.getSelectedRow(), 3);
     			
     			// JOptionPane.showConfirmDialog의 결과가 숫자로 반환된다.
     			// X 표를 눌러 닫은 경우 = -1
     			// 예 = 0
     			// 아니오 = 1
     			int yes_or_no = JOptionPane.showConfirmDialog(null, a + "원 결제하셨습니다. 반품 하시겠습니까?", "반품", JOptionPane.YES_NO_OPTION);
     			
     			if (state_chk.equals("complete")) {
     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {
     				// 예 아니오 선택없이 창 닫은경우
     					System.out.println(yes_or_no);
     					System.out.println("그냥 닫았네?");
     				} else if (yes_or_no == JOptionPane.YES_OPTION) {
     				// 사용자가 예를 선택한경우
//     					refund(select_receipt_no);
     					System.out.println(yes_or_no);
     					System.out.println("반품이라니..");
     				} else {
     				// 사용자가 아니오를 선택한경우
     					System.out.println(yes_or_no);
     					System.out.println("돈 안줘도 된다~!~!");
     				}	
     			}
     			
     		}
     	});
     	
     	// ================================================================================================
        // '현금영수증' 버튼을 눌렀을때의 액션
		// ================================================================================================
     	
     	buttons.get(2).addActionListener(new ActionListener() {
     		
     		@Override
     		public void actionPerformed(ActionEvent e) {
     			
     			String a = "" + table.getValueAt(table.getSelectedRow(), 3);
     			
     			// JOptionPane.showConfirmDialog의 결과가 숫자로 반환된다.
     			// X 표를 눌러 닫은 경우 = -1
     			// 예 = 0
     			// 아니오 = 1
     			
     			String[] check;
     			
     			// 현금결제금액과 카드결제금액, 현금영수증처리 유무를 받아오기 
     			check = cash_receipt(select_receipt_no);
     			
//     			for (int i = 0; i < check.length; i++) {
//     				System.out.print(check[i]);
//     			}
     			// check[0] = 현금결제금액		
     			// check[1] = 카드결제금액		
     			// check[2] = 현금영수증처리 유무
     			
     			int yes_or_no;
     			
     			if (check[2].equals("Y")) {
     				
     				yes_or_no = JOptionPane.showConfirmDialog(null, "이미 현금영수증 처리를 한 상태입니다. 취소하시겠습니까?", "현금영수증 취소", JOptionPane.YES_NO_OPTION);
     				
     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {	// 예 아니오 선택없이 창 닫은경우
     				
     					System.out.println(yes_or_no);
     					System.out.println("그냥 닫았네?");
     					
     				} else if (yes_or_no == JOptionPane.YES_OPTION) {	// 사용자가 예를 선택한경우
     				
     					cash_receipt_executive(select_receipt_no, "Y");
     					System.out.println(yes_or_no); 
//     					System.out.println("현금영수증취소");
     					JOptionPane.showMessageDialog(null, "현금영수증을 취소처리 하였습니다.");
     					
     				} else {	// 사용자가 아니오를 선택한경우
     				
     					System.out.println(yes_or_no);
     					System.out.println("현금영수증 취소 안함");
     				}
     				
     			} else if (check[2].equals("N") && check[0].equals("0") && !check[1].equals("0")) {
     				// 현금 결제금액 없이 카드 결제금액만 있는 경우 
     				JOptionPane.showMessageDialog(null, "카드 결제로 " + check[1] + "원 결제 하셨습니다.");
     				
     			} else if (check[2].equals("N") && !check[1].equals("0")) {
     				
     				// 카드 결제금액의 유무와 상관없이 현금 결제 금액이 있다면 현금영수증 처리를 할지 물어봄
     				
     				yes_or_no = JOptionPane.showConfirmDialog(null, "현금으로 " + check[1] + "원 결제하셨습니다. 현금영수증 처리를 하시겠습니까?", "현금영수증 처리", JOptionPane.YES_NO_OPTION);
     				
     				if (yes_or_no == JOptionPane.CLOSED_OPTION) {
     				// 예 아니오 선택없이 창 닫은경우
     					System.out.println(yes_or_no);
     					System.out.println("그냥 닫았네?");
     				} else if (yes_or_no == JOptionPane.YES_OPTION) {
     				// 사용자가 예를 선택한경우
     					cash_receipt_executive(select_receipt_no, "N");
     					System.out.println(yes_or_no);
     					System.out.println("현금영수증처리");
     					JOptionPane.showMessageDialog(null, "현금영수증을 처리 하였습니다.");
     				} else {
     				// 사용자가 아니오를 선택한경우
     					System.out.println(yes_or_no);
     					System.out.println("현금영수증 처리 안함");
     				}	
         			
     				
     			}
     			
     			
     		}
     	});
     	
     	// ================================================================================================
        // '결제변경' 버튼을 눌렀을때의 액션
		// ================================================================================================     	
           
           buttons.get(3).addActionListener(new ActionListener() {
        	    
        	   
        	   
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			
        			payment_change a = new payment_change(select_receipt_no);

        		}
        		
        	});
     	
		// ================================================================================================
        // '전체' 버튼을 눌렀을때의 액션
		// ================================================================================================     	
           
           buttons.get(4).addActionListener(new ActionListener() {
        	   
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			
//        			total();

        			DefaultTableModel model = new DefaultTableModel(data_default, columnNames);
        			table.setModel(model);
//        			JTable table = new JTable(model);

        			model.fireTableDataChanged();
        		}
        		
        	});
		// ================================================================================================
        // '현금' 버튼을 눌렀을때의 액션
		// ================================================================================================
           
           buttons.get(5).addActionListener(new ActionListener() {
        	   
        		@Override
        		public void actionPerformed(ActionEvent e) {

//        			cash_list();

        			DefaultTableModel model = new DefaultTableModel(data_cash, columnNames);
        			
        			table.setModel(model);
//        			JTable table = new JTable(model);
        			
        			model.fireTableDataChanged();
        			
        		}
        		
        	});
       // ================================================================================================
       // '신용카드' 버튼을 눌렀을때의 액션
       // ================================================================================================
              
              buttons.get(6).addActionListener(new ActionListener() {
           	   
           		@Override
           		public void actionPerformed(ActionEvent e) {
           			
//           	        credit_list();
           	        
           			DefaultTableModel model = new DefaultTableModel(data_credit, columnNames);
           			table.setModel(model);
//           		JTable table = new JTable(model);
           			
           			model.fireTableDataChanged();
           		}
           		
           	});  

           
     // ================================================================================================
     // ================================================================================================

	}
	
	public static void main(String[] args) {
		 Receipt_duck_2 frame = new Receipt_duck_2();	
		 frame.setDefaultOptions();
	}
}

