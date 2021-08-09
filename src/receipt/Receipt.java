package receipt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import baseSettings.PosFrame;

public class Receipt extends PosFrame {
	
	
	static ArrayList<ArrayList<String>> list_data = new ArrayList<ArrayList<String>>();
	String[] columnNames = null;
	String RECEIPT_NO = "24";
	HashMap<String, ArrayList<Integer>> drink;
	
	int total;
	int vat;
	int cash;
	int point_used;
	int point;
	int credit;
	String receipt_chk;
	Date transaction_date;
	
	DecimalFormat formatter = new DecimalFormat("##,###,###"); // 금액 출력 포멧터
	
	
	public Receipt() {
		super();
		super.setTitle("영수증 관리");
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");

            String sql = "select * from PAYMENT_VIEW WHERE RECEIPT_NO = " + RECEIPT_NO;
            
            // ================================================================================================
            // ================================================================================================            	
            	String Receipt_list = "select * from payment_view_1 ORDER BY DATETIME";
            	PreparedStatement pstmt_Receipt_list = conn.prepareStatement(Receipt_list);
            	
            	ResultSet rs_list = pstmt_Receipt_list.executeQuery();
            	
            	ResultSetMetaData md = rs_list.getMetaData() ;
            	int column_size = md.getColumnCount();
            	
            	columnNames = new String[column_size+1];
            	
            	System.out.println(columnNames.length);
            	for(int i = 0; i < columnNames.length; i++) {
            		System.out.print(i + " ");
            		if (i == 0) {
            			System.out.println(i);
            			columnNames[i] = "No";
            		} else {
            			System.out.println(md.getColumnName(i));
            			columnNames[i] = md.getColumnName(i);
            		}
                }

            	while (rs_list.next()) {
            		ArrayList<String> data = new ArrayList<>();
            		for (int i = 0; i < columnNames.length; i++) {
            			if (i == 0) {
            				data.add("" + i);
            			} else {
            				data.add(rs_list.getString(columnNames[i]));
            			}
            		}
            		list_data.add(data);
            		
            	}
            	rs_list.close();
            	pstmt_Receipt_list.close();
            
            // ================================================================================================
        	// ================================================================================================
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            drink = new HashMap<>();
            while (rs.next()) {
            	// HashMap에 음료수 내역 넣기
            	drink.putIfAbsent(rs.getString("MENU"), new ArrayList<>());
            	
            	drink.get(rs.getString("MENU")).add(rs.getInt("PRICE"));
            	
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
		
		setLayout(null);
		
		
		
		// 시간을 문자열로 변경하기
		DateTimeFormatter my_date_format = 
				DateTimeFormatter.ofPattern("y년 M월 d일");
		
		String message = my_date_format.format(LocalDate.now());

		JButton date = new JButton(message);
		
		
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
//		----------------------사용한 결제금액 bill에 출력
		String bill = "";
		if (cash != 0) {
			bill += "<table style='width:100%;'>"
					+ "<tr>"
					+ "<td>현&emsp금</td>"
					+ "<td style='text-align:right;'>"
					+ formatter.format(cash)
					+ "원</td>"
					+ "</tr>"
					+ "</table>";
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
		}
		if (receipt_chk.equals("Y")) { // 현금 영수증 체크
			bill += "<br>[현금영수증 발급]"
					+ "<br>발급 용도: 소비자 소득공제";
		} else if (receipt_chk.equals("N")) {
			bill += "<br>[현금영수증 미발급]";
		}
		// 영수증 시간 LocalDate로 변환 후 String으로 변환
			
		LocalDateTime localDateTime = new java.sql.Timestamp(transaction_date.getTime())
				.toLocalDateTime();


		
		DateTimeFormatter my_date_format2 = 
				DateTimeFormatter.ofPattern("y년 M월 d일 HH:mm:ss");
		
		
		DateTimeFormatter my_date_format3 = 
				DateTimeFormatter.ofPattern("M월 d일");
		

		LocalDateTime returnDay = localDateTime.plusDays(30);
		String transaction_date_string = my_date_format2.format(localDateTime);	// 판매일자
		String returnDayString = my_date_format3.format(returnDay); // 반품가능일자
		
		String string = // 영수증 전체 내용 
				"<html><center><br>"
				+ "매&emsp출&emsp전&emsp표"
				+ "<br>"
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>거래일시: </td>"
				+ "<td style='text-align:right;'>"+ transaction_date_string + "</td>"
				+ "</tr>"
				+ "</table>"
				+ "-------------------------------------------------------------------"
				+ "<br>*정부방침에 의해 교환/환불은 반드시 영수증을"
				+ "<br>지참하셔야 하며, 카드결제는 30일"
				+ "(" + returnDayString + ")" // 데이터베이스에서 거래날짜 + 30 
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
				+ "<td>합&emsp계</td>"
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
				+ "<center>감사합니다.<br><br> </center>"
				+ "</html>";
		
		
		
		
		
		// &nbsp 띄어쓰기 &emsp 크게 띄어쓰기
		// <html> +  + <br> +  + </html> 줄 바꾸는 법
		
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

        JPanel receipt_panel = new JPanel();        
        
     	receipt_panel.setBackground(Color.black);
     	receipt_panel.setLocation(20, 140);
     	receipt_panel.setSize(660, 500);

     	String[][] data1 = null;
     	
     	int w_size = list_data.size(), h_size = list_data.get(0).size();
     	
     	data1 = new String[w_size+1][h_size];
     	
     	for (int i = 0; i < w_size; i++) {
    		for (int x = 0; x < h_size; x++) {
    			if (x == 0) {
    				data1[i][x] = "" + i;
    			}else {
	    			data1[i][x] = list_data.get(i).get(x);
    			}
    		}
    	}
     	
     	JPanel a = new JPanel();
     	
     	JTable table = new JTable(data1, columnNames);
     	JScrollPane scrollPane1 = new JScrollPane(table);
     	
     	scrollPane1.setBackground(Color.pink);
     	
     	table.setPreferredSize(new Dimension(660, 1000));
     	scrollPane1.setPreferredSize(new Dimension(660, 1000));
//     	승민 영수증넘버 받아오는 로직-----------------------------------------------------------
     	ListSelectionModel selection = table.getSelectionModel();
		
		selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(false);
		
		
		selection.addListSelectionListener(new ListSelectionListener() {
		
			public void valueChanged(ListSelectionEvent e) {
				RECEIPT_NO = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));// 클릭한곳의 RECEIPT_NO 반환하는 로직
				
				
				receipt.setText(string); // 영수증 바뀌게 하는 처리 해야한다.
				
			} 
		
				
		});	
//     	-------------------------------------------------------------------------------------------------
     	table.getTableHeader().setPreferredSize(new Dimension(scrollPane1.getWidth(), 50));
     	
     	table.setRowSelectionAllowed(true);
     	table.setColumnSelectionAllowed(true);
     	
     	table.setShowGrid(true);

     	a.add(scrollPane1);
     	receipt_panel.add(scrollPane1);
     	add(receipt_panel);
     // ================================================================================================
     // ================================================================================================
        
        

	}
	
	public static void main(String[] args) {
		 Receipt frame = new Receipt();	
		 frame.setDefaultOptions();
	}
}