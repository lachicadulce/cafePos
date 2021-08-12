package receipt;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class payment_change extends JDialog {

//  JLabel jlb = new JLabel("");
//  JLabel label = new JLabel();
	
	JLabel label_payment_print_top = new JLabel();
  
	JLabel label_payment_left1 = new JLabel();
	JLabel label_payment_left2 = new JLabel();
	JLabel label_payment_left3 = new JLabel();
	JLabel label_payment_left4 = new JLabel();
	JLabel label_payment_left5 = new JLabel();
	
	JLabel label_payment_right = new JLabel();
	
	JLabel label_change_who = new JLabel();
//	JLabel label_change_explanation = new JLabel();
	
	JButton btn1 = new JButton();
	JButton btn2 = new JButton();
	
	JPanel new_s1 = new JPanel(); 
	JPanel new_s2 = new JPanel();
	
	JPanel new_text_top = new JPanel();
	JPanel new_text_left = new JPanel();
	JPanel new_text_right = new JPanel();
	
	String[] columnNames;
    String[] columnData;
	
	String[] return_payment_change;
	
	  public payment_change(int receipt_no){
//	    getContentPane().add(label);
		  
		return_payment_change = dbcheck(receipt_no);
		
		payment_check(return_payment_change);
		
		System.out.println(payment_check(return_payment_change));
//		for (int i = 0; i < return_payment_change.length; i++) {
//			System.out.println(i + " 번째 값 : " + return_payment_change[i]);
//		}
	      
		setLayout(null);
		
		new_s1.setBounds(10, 220, 210, 210);
		new_s2.setBounds(250, 220, 220, 210);
		
		new_text_top.setBounds(10, 10, 460, 50);
		new_text_left.setBounds(10, 60, 230, 160);
		new_text_right.setBounds(240, 60, 230, 160);
		
//		new_text.setBounds(10, 10, 460, 200);
		
		System.out.println();
		System.out.println(receipt_no);
//		new_s1.setBackground(Color.black);
//		new_s2.setBackground(Color.black);
		
		label_change_who.setBounds(280, 550, 260, 452);

		label_payment_left1.setBounds(280, 65, 120, 22);
		label_payment_left2.setBounds(280, 87, 120, 22);
		label_payment_left3.setBounds(280, 109, 120, 22);
		label_payment_left4.setBounds(280, 131, 120, 22);
		label_payment_left5.setBounds(280, 153, 120, 22);
		
		label_payment_right.setBounds(280, 153, 120, 22);
		
		label_payment_left1.setForeground(Color.white);
		label_payment_left2.setForeground(Color.white);
		label_payment_left3.setForeground(Color.white);
		label_payment_left4.setForeground(Color.white);
		label_payment_left5.setForeground(Color.white);
		
		label_payment_right.setForeground(Color.yellow);
		
		label_change_who.setForeground(Color.white);
		
		new_text_top.setBackground(Color.black);
		new_text_left.setBackground(Color.black);
		new_text_right.setBackground(Color.black);
		
		
//		label.setText("<html><body><font size=6>현재 결제 정보는 xxxxxx 입니다<font></body></html>");
		
//		label.setIcon(new ImageIcon("image/cash.png"));
		
		
//	    btn1.setText("현금결제");
	    btn1.setFont(new Font("돋움", Font.PLAIN, 20));
	    btn1.setIcon(new ImageIcon("image/cash.png"));
	    btn1.setLocation(150, 150);
//	    btn1.setSize(300, 300);
	    btn1.setBackground(Color.black);
	    
//	    System.out.println(btn1.getSize(getSize()));
	    
	    
//	    btn2.setText("신용카드결제");
	    
	    btn2.setIcon(new ImageIcon("image/credit.png"));
	    btn2.setLocation(150, 150);
//	    btn2.setSize(300, 300);
	    btn2.setBackground(Color.black);
	    
	    new_s1.add(btn1);
	    new_s2.add(btn2);
	    
	    label_change_who.setFont(new Font("Small Fonts", Font.BOLD, 20));
	    
	    label_payment_left1.setFont(new Font("Small Fonts", Font.BOLD, 16));
	    label_payment_left2.setFont(new Font("Small Fonts", Font.BOLD, 16));
	    label_payment_left3.setFont(new Font("Small Fonts", Font.BOLD, 16));
	    label_payment_left4.setFont(new Font("Small Fonts", Font.BOLD, 16));
	    label_payment_left5.setFont(new Font("Small Fonts", Font.BOLD, 16));
	    
	    label_payment_right.setFont(new Font("Small Fonts", Font.BOLD, 17));
	    
	    
	    new_text_top.add(label_change_who);
	    
	    new_text_left.add(label_payment_left1);
	    new_text_left.add(label_payment_left2);
	    new_text_left.add(label_payment_left3);
	    new_text_left.add(label_payment_left4);
	    new_text_left.add(label_payment_left5);
	    
	    new_text_right.add(label_payment_right);
	    
	    add(new_s1);
	    add(new_s2);
	    
	    add(new_text_left);
	    add(new_text_right);
	    add(new_text_top);	// 추가하는 순서가 나중일수록 겹치는 순위에서 아래쪽에 배치된다. 무엇보다도 윗쪽에 배치하고 싶다면 가장 먼저 add 할 것   
	    
//	    jlb.setText(receipt_no.toString());
//	    label.add(btn);
//	    add(btn);
//	    this.setSize(600,800);
	    this.setBounds(250, 300, 500, 480);
	    this.setModal(true);
	    this.setVisible(true);
	    this.setResizable(false);
	    this.setBackground(Color.black);
	    this.setForeground(Color.black);
	}
	
	public String payment_check(String[] data_list) {
		
//		for (int i = 0; i < data_list.length; i++) {
//			System.out.println(i + " 번째 값 : " + data_list[i]);
//		}
		/*
				data_list[0]	// 영수증번호
				data_list[1]	// 결제일자
				data_list[2]	// 전체금액
				data_list[3]	// 카드결제금액
				data_list[4]	// 현금결제금액
				data_list[5]	// 고객번호
				data_list[6]	// 사용포인트
				data_list[7]	// 적립포인트
				data_list[8]	// 결제상태
				data_list[9]	// 현금영수증 유무
				data_list[10]	// 고객이름
				data_list[11]	// 전화번호
				data_list[12]	// 포인트
		*/	
		
		label_change_who.setText(data_list[10] + " 고객님의 총 결제 금액은 " + data_list[2] + " 원 입니다.");
		
		label_payment_left1.setText("카드결제금액 : " + data_list[3]);
		label_payment_left2.setText("현금결제금액 : " + data_list[4]);
		label_payment_left3.setText("사용한포인트 : " + data_list[6]);
		label_payment_left4.setText("현금영수증 유무 : " + data_list[9]);
		label_payment_left5.setText("적립된 포인트 : " + data_list[7]);
		
		label_payment_right.setText("<html><center>"
								  + "현금 → 카드<br> 현금주머니 버튼을"
								  + "<hr>"
								  + "카드 → 현금<br> 오른쪽 카드 버튼을<br>"
								  + "<hr>"
								  + "원하시는 버튼을 눌러주세요"
								  + "</center></html>");
		
		int cash = Integer.parseInt(data_list[4]);
		int credit = Integer.parseInt(data_list[3]);
		int save_point = Integer.parseInt(data_list[7]);
		int use_point = Integer.parseInt(data_list[6]);
		int cus_point = Integer.parseInt(data_list[12]);
		
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				String d = "UPDATE history_payment SET cash = 0, credit = "+ (credit+cash) +", RECEIPT_CHK = 'N' WHERE receipt_no = " + data_list[0];
				
				if (cash == 0 ) {	// 현금 결제 내역이 없는 경우_( 현금→카드 로 바꿔주는 프로세스 이므로 아무런 동작이 없음 )
					
				} else if (cash > 0  && use_point > 0) { 		// 현금 결제 내역과 포인트 사용 내역이 있는 경우_(
					
				} else if (cash > 0  && use_point <= 0) {		// 현금 결제 내역이 있고 포인트는 사용하지 않은 경우_( 
					
				}
				
				
			}
		});
		
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		

		
		
		return "dd";
	}
	
	// 업데이트 
	public void dbupdate() {
		
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            
            String sql = "";

			PreparedStatement pstmt_payment_change = conn.prepareStatement(sql);
            
            ResultSet rs_list = pstmt_payment_change.executeQuery();
            
            ResultSetMetaData md = rs_list.getMetaData();
            
            int column_size = md.getColumnCount();
            
            columnNames = new String[column_size];
            columnData = new String[column_size];
            
            for(int i = 0; i < columnNames.length; i++) {
            	columnNames[i] = md.getColumnName(i+1);
//        		System.out.print(columnNames[i] + "\t\t");
            };
//            System.out.println("\n========================================================================================================================================================================================");
            
            while (rs_list.next()) {
        		
        		for (int i = 0; i < columnNames.length; i++) {
    				columnData[i] = rs_list.getString(columnNames[i]);
//    				System.out.print(columnData[i] + "\t\t");
        		}
        		
        	}
            pstmt_payment_change.close();
			conn.close();
			
			
		} catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }
		
	}
	  
	  
	public String[] dbcheck(int receipt_no) {
		
		try {
	            Connection conn = DriverManager.getConnection(
	            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
	            		"cafe",
	            		"!!22Qorthdud");
	            
	            String sql = "select "
						+ "a.RECEIPT_NO,"
						+ "a.DATETIME,"
						+ "a.TOTAL,"
						+ "a.CREDIT,"
						+ "a.CASH,"
						+ "a.CUS_NO,"
						+ "a.POINT_USED,"
						+ "a.POINT_SAVED,"
						+ "a.STATE,"
						+ "a.RECEIPT_CHK,"
						+ "b.NAME,"
						+ "b.TEL,"
						+ "b.POINT"
				+ " from history_payment a, customer_info b "
				+ "where a.cus_no = b.cus_no and RECEIPT_NO = ";
	
				sql += "" + receipt_no;
				
	            PreparedStatement pstmt_payment_change = conn.prepareStatement(sql);
	            
	            ResultSet rs_list = pstmt_payment_change.executeQuery();
	            
	            ResultSetMetaData md = rs_list.getMetaData();
	            
	            int column_size = md.getColumnCount();
	            
	            columnNames = new String[column_size];
	            columnData = new String[column_size];
	            
	            for(int i = 0; i < columnNames.length; i++) {
	            	columnNames[i] = md.getColumnName(i+1);
//            		System.out.print(columnNames[i] + "\t\t");
                };
//                System.out.println("\n========================================================================================================================================================================================");
                
                while (rs_list.next()) {
            		
            		for (int i = 0; i < columnNames.length; i++) {
        				columnData[i] = rs_list.getString(columnNames[i]);
//        				System.out.print(columnData[i] + "\t\t");
            		}
            		
            	}
                pstmt_payment_change.close();
				conn.close();
				
				
			} catch (SQLException e) {
	            System.out.println("getConnection 하다가 문제 생김");
	        }
			
			return columnData;
		
	}
	  
	
	
}

