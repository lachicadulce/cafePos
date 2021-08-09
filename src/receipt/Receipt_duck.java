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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import baseSettings.PosFrame;

public class Receipt_duck extends PosFrame {
	
	static ArrayList<ArrayList<String>> list_data_default = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> list_data_cash = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> list_data_credit = new ArrayList<ArrayList<String>>();
	
	static String[] columnNames = null;
	
	static String Receipt_list = "select * from payment_view_1";
	static String Receipt_list_cash = "select * from payment_view_1 where cash > 0";
	static String Receipt_list_credit = "select * from payment_view_1 where credit > 0";
	
	static String[][] data_default = null;
	static String[][] data_cash = null;
	static String[][] data_credit = null;
	
	public void total() {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================            	
            	PreparedStatement pstmt_Receipt_list = conn.prepareStatement(Receipt_list);
            	ResultSet rs_list = pstmt_Receipt_list.executeQuery();

            	ResultSetMetaData md = rs_list.getMetaData();
            	
            	int column_size = md.getColumnCount();
            	
            	columnNames = new String[column_size+1];
            	
            	for(int i = 0; i < columnNames.length; i++) {
            		if (i == 0) {
            			columnNames[i] = "No";
            		} else {
            			columnNames[i] = md.getColumnName(i);
            		}
                }
            	list_data_default.clear();
            	while (rs_list.next()) {
            		ArrayList<String> data = new ArrayList<>();
            		data.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data.add("" + i);
            			} else {
            				data.add(rs_list.getString(columnNames[i]));
            			}
            		}
            		list_data_default.add(data);
            	}
            	
            	rs_list.close();
            	pstmt_Receipt_list.close();
            	conn.close();
            
            // ================================================================================================
        	// ================================================================================================
            	int w_size = list_data_default.size(), h_size = list_data_default.get(0).size();
             	data_default = new String[w_size+1][h_size];

             	for (int i = 0; i < w_size; i++) {
            		for (int x = 0; x < h_size; x++) {
            			if (x == 0) {
            				data_default[i][x] = "" + i;
            			}else {
            				data_default[i][x] = list_data_default.get(i).get(x);
            			}
            		}
            	}
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	
	
	public void cash_list() {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================            	
            	PreparedStatement pstmt_Receipt_list_cash = conn.prepareStatement(Receipt_list_cash);
            	ResultSet rs_list_cash = pstmt_Receipt_list_cash.executeQuery();

            	list_data_cash.clear();
            	while (rs_list_cash.next()) {
            		
            		ArrayList<String> data = new ArrayList<>();
            		data.clear();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data.add("" + i);
            			} else {
            				data.add(rs_list_cash.getString(columnNames[i]));
            			}
            		}
            		list_data_cash.add(data);
            	}
            	System.out.println(list_data_cash.size());
            	
            	rs_list_cash.close();
            	pstmt_Receipt_list_cash.close();
            	conn.close();
            
            // ================================================================================================
        	// ================================================================================================
             	int cash_w_size = list_data_cash.size(), cash_h_size = list_data_cash.get(0).size();
             	data_cash = new String[cash_w_size+1][cash_h_size];
             	
             	for (int i = 0; i < cash_w_size; i++) {
            		for (int x = 0; x < cash_h_size; x++) {
            			if (x == 0) {
            				data_cash[i][x] = "" + i;
            			}else {
            				data_cash[i][x] = list_data_cash.get(i).get(x);
            			}
            		}
            	}
            
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	
	public void credit_list() {
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
        // ================================================================================================
        // ================================================================================================            	

            	PreparedStatement pstmt_Receipt_list_credit = conn.prepareStatement(Receipt_list_credit);
            	ResultSet rs_list_credit = pstmt_Receipt_list_credit.executeQuery();
            	
            	list_data_credit.clear();
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

            	rs_list_credit.close();
            	pstmt_Receipt_list_credit.close();
            	
            	conn.close();
            
            // ================================================================================================
        	// ================================================================================================

             	int credit_w_size = list_data_credit.size(), credit_h_size = list_data_credit.get(0).size();

             	data_credit = new String[credit_w_size+1][credit_h_size];
             	
             	for (int i = 0; i < credit_w_size; i++) {
            		for (int x = 0; x < credit_h_size; x++) {
            			if (x == 0) {
            				data_credit[i][x] = "" + i;
            			}else {
        	    			data_credit[i][x] = list_data_credit.get(i).get(x);
            			}
            		}
            	}
            
            
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}	
	
	public Receipt_duck() {
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
     	receipt_panel.setAutoscrolls(true);
     	JPanel a = new JPanel();
     	
     	
     	DefaultTableModel model = new DefaultTableModel(data_default, columnNames);
		JTable table = new JTable(model);
     	
     	JScrollPane scrollPane1 = new JScrollPane(table);
     	
     	scrollPane1.setBackground(Color.pink);
     	
     	table.setPreferredSize(new Dimension(660, 500));
     	scrollPane1.setPreferredSize(new Dimension(660, 500));
     	
     	
     	table.getTableHeader().setPreferredSize(new Dimension(scrollPane1.getWidth(), 50));
     	
     	table.setRowSelectionAllowed(true);
     	table.setColumnSelectionAllowed(true);
     	
     	table.setShowGrid(true);
     	
     	a.add(scrollPane1);
     	receipt_panel.add(scrollPane1);
     	add(receipt_panel);
		// ================================================================================================
		// ================================================================================================
           
           buttons.get(4).addActionListener(new ActionListener() {
        	   
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			
        			total();

        			DefaultTableModel model = new DefaultTableModel(data_default, columnNames);
        			table.setModel(model);
        			JTable table = new JTable(model);

        			model.fireTableDataChanged();
        		}
        		
        	});
		// ================================================================================================
		// ================================================================================================
           
           buttons.get(5).addActionListener(new ActionListener() {
        	   
        		@Override
        		public void actionPerformed(ActionEvent e) {

        			cash_list();

        			DefaultTableModel model = new DefaultTableModel(data_cash, columnNames);

        			table.setModel(model);
        			JTable table = new JTable(model);
        			
        			model.fireTableDataChanged();
        		}
        		
        	});
       // ================================================================================================
       // ================================================================================================ 
              
              buttons.get(6).addActionListener(new ActionListener() {
           	   
           		@Override
           		public void actionPerformed(ActionEvent e) {

           	        credit_list();
           	        
           			DefaultTableModel model = new DefaultTableModel(data_credit, columnNames);
           			table.setModel(model);
           			JTable table = new JTable(model);
           			
           			model.fireTableDataChanged();
           		}
           		
           	});  

           
     // ================================================================================================
     // ================================================================================================
     	

	}
	
	public static void main(String[] args) {
		 Receipt_duck frame = new Receipt_duck();	
		 frame.setDefaultOptions();
	}
}