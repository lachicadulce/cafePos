package receipt;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class payment_change extends JDialog {
	
	// =================================== 여기부터 패널 생성 ===================================
	JPanel new_s1 = new JPanel(); 
	JPanel new_s2 = new JPanel();
	
	JPanel new_text_top = new JPanel();
	JPanel new_text_left = new JPanel();
	JPanel new_text_right = new JPanel();
	
	JPanel new_text_bottom = new JPanel();
	
	// =================================== 여기까지 패널 생성 ===================================
	// =================================== 여기부터 라벨 생성 ===================================
	JLabel label_payment_print_top = new JLabel();
  
	JLabel label_payment_left1 = new JLabel();	// 현재 선택된 OOO 고객의 OOO 정보를 보여줄 부분
	JLabel label_payment_left2 = new JLabel();	// 현재 선택된 OOO 고객의 OOO 정보를 보여줄 부분
	JLabel label_payment_left3 = new JLabel();	// 현재 선택된 OOO 고객의 OOO 정보를 보여줄 부분
	JLabel label_payment_left4 = new JLabel();	// 현재 선택된 OOO 고객의 OOO 정보를 보여줄 부분
	JLabel label_payment_left5 = new JLabel();	// 현재 선택된 OOO 고객의 OOO 정보를 보여줄 부분
	
	JLabel label_payment_right = new JLabel();	// 	이 후의 프로세스를 진행하기 위해선 어떻게 해야 하는지를 안내하는 메시지를 보여줄 부분
	
	JLabel label_change_who = new JLabel();		//	누구의 결제 정보를 보고 있는 지 알려주기 위한 메시지를 보여 줄 부분 
	JLabel label_bottom = new JLabel();			//	point 관련 안내 메시지
	
	// =================================== 여기까지 라벨 생성 ===================================
	// =================================== 여기부터 버튼 생성 ===================================
	
	JButton btn1 = new JButton();	//	왼쪽 돈 주머니 이미지를 보여줄 버튼 생성
	JButton btn2 = new JButton();	//	오른쪽 돈 주머니 이미지를 보여줄 버튼 생성
	
	// =================================== 여기까지 버튼 생성 ===================================
	// =================================== return_payment_change 데이터 구성 ===================================
	/*
				return_payment_change[0]	// 영수증번호
				return_payment_change[1]	// 결제일자
				return_payment_change[2]	// 전체금액
				return_payment_change[3]	// 카드결제금액
				return_payment_change[4]	// 현금결제금액
				return_payment_change[5]	// 고객번호
				return_payment_change[6]	// 사용포인트
				return_payment_change[7]	// 적립포인트
				return_payment_change[8]	// 결제상태
				return_payment_change[9]	// 현금영수증 유무
				return_payment_change[10]	// 고객이름
				return_payment_change[11]	// 전화번호
				return_payment_change[12]	// 포인트
	 */	
	// =================================== return_payment_change 데이터 구성 ===================================
	
	String[] columnNames;
    String[] columnData;
	
	String[] return_payment_change;
	
	  public payment_change(int receipt_no){
//	    getContentPane().add(label);
		  
		return_payment_change = dbcheck(receipt_no);
		
		// 현제 가져온 영수증 정보의 뿌려줄 내용구성
		payment_check(return_payment_change);
		
		setLayout(null);
		
		new_s1.setBounds(10, 220, 210, 210);
		new_s2.setBounds(250, 220, 220, 210);
		
		new_text_top.setBounds(10, 10, 460, 50);
		new_text_left.setBounds(10, 60, 230, 160);
		new_text_right.setBounds(240, 60, 230, 160);
		
//		new_text.setBounds(10, 10, 460, 200);
		
//		System.out.println();
//		System.out.println(receipt_no);
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
		
//	    btn1.setText("현금결제");
	    btn1.setFont(new Font("돋움", Font.PLAIN, 20));
	    btn1.setIcon(new ImageIcon("image/cash.png"));
	    btn1.setLocation(150, 150);
	    btn1.setBackground(Color.black);
	    
//	    btn2.setText("신용카드결제");
	    btn2.setIcon(new ImageIcon("image/credit.png"));
	    btn2.setLocation(150, 150);
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
	    add(new_text_top);		// 추가하는 순서가 나중일수록 겹치는 순위에서 아래쪽에 배치된다. 무엇보다도 윗쪽에 배치하고 싶다면 가장 먼저 add 할 것
	    add(new_text_bottom); 	
	    
	    
	    int cash = Integer.parseInt(return_payment_change[4]);
		int credit = Integer.parseInt(return_payment_change[3]);
		int save_point = Integer.parseInt(return_payment_change[7]);
		int use_point = Integer.parseInt(return_payment_change[6]);
		int cus_point = Integer.parseInt(return_payment_change[12]);
	    
	    // 버튼1 (현금 → 카드로 결제금액 변경 시의 액션) 
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String sql = "UPDATE history_payment SET cash = 0, credit = "+ (credit+cash) +", RECEIPT_CHK = 'N' WHERE receipt_no = " + return_payment_change[0];
				
				
//				System.out.println(sql);
				if ( cash == 0 ) {				// 현금 결제 내역이 없는 경우_( 현금→카드 로 바꿔주는 프로세스 이므로 아무런 동작이 없음 )
					JOptionPane.showMessageDialog(null, "너 임마 너 !!  현금 낸거 없는데???? 다시 확인해봐 !! ");
				} else if ( cash > 0 ) { 			// 현금 결제 내역과 포인트 사용 내역이 있는 경우
					
					int yes_or_no = JOptionPane.showConfirmDialog(null, "너 현금 " + cash + " 이만큼 냈는데 이거 카드 계산할거야??. ", "카드수수료 나간다.........", JOptionPane.YES_NO_OPTION);
					
					if (yes_or_no == JOptionPane.CLOSED_OPTION) {			// "예", "아니오" 선택없이 창 닫은경우 yes_or_no = 2
	     					System.out.println("그냥 닫았네?");
	     				} else if (yes_or_no == JOptionPane.YES_OPTION) {	// 사용자가 "예"를 선택한경우 yes_or_no = 0
	     				
	     					data_update(sql);
	     					JOptionPane.showMessageDialog(null, "현금영수증을 처리 하였습니다.");
	     				} else {							   				// 사용자가 "아니오"를 선택한경우 yes_or_no = 1
	     					JOptionPane.showMessageDialog(null, "그래그래~~ 잘 생각했어 ~ ");
	     				}
				}
			}
			
		});
		
		// 버튼2 (카드 → 현금으로 결제금액 변경 시의 액션)
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String sql = "UPDATE history_payment SET cash = " + (credit+cash) + ", credit = 0, RECEIPT_CHK = 'Y' WHERE receipt_no = " + return_payment_change[0];
				
//				System.out.println(sql);
				if ( credit == 0 ) {				// 현금 결제 내역이 없는 경우_( 현금→카드 로 바꿔주는 프로세스 이므로 아무런 동작이 없음 )
					JOptionPane.showMessageDialog(null, "너 임마 너 !!  카드 안썼어!?!? 다시 확인해봐 !! ");
				} else if ( credit > 0 ) { 			// 현금 결제 내역과 포인트 사용 내역이 있는 경우
					
					int yes_or_no = JOptionPane.showConfirmDialog(null, "너 카드로 " + credit + " 이만큼 계산했어", "현금영수증 해줘야하네.....", JOptionPane.YES_NO_OPTION);
					
					
					if (yes_or_no == JOptionPane.CLOSED_OPTION) {			// "예", "아니오" 선택없이 창 닫은경우 yes_or_no = 2
	     					System.out.println("그냥 닫았네?");
	     				} else if (yes_or_no == JOptionPane.YES_OPTION) {	// 사용자가 "예"를 선택한경우 yes_or_no = 0
	     					data_update(sql);
	     					JOptionPane.showMessageDialog(null, "현금영수증을 처리 하였습니다.");
	     				} else {							   				// 사용자가 "아니오"를 선택한경우 yes_or_no = 1
	     					JOptionPane.showMessageDialog(null, "그래그래~~ 잘 생각했어 ~ ");
	     				}
				}
				
			}
		});
	    this.setBounds(250, 300, 500, 480);
	    this.setModal(true);
	    this.setVisible(true);
	    this.setResizable(false);
	    this.setBackground(Color.black);
	    this.setForeground(Color.black);
	}

	public void payment_check(String[] data_list) {
		
		/*
				return_payment_change[0]	// 영수증번호
				return_payment_change[1]	// 결제일자
				return_payment_change[2]	// 전체금액
				return_payment_change[3]	// 카드결제금액
				return_payment_change[4]	// 현금결제금액
				return_payment_change[5]	// 고객번호
				return_payment_change[6]	// 사용포인트
				return_payment_change[7]	// 적립포인트
				return_payment_change[8]	// 결제상태
				return_payment_change[9]	// 현금영수증 유무
				return_payment_change[10]	// 고객이름
				return_payment_change[11]	// 전화번호
				return_payment_change[12]	// 포인트
		*/	
		
		label_change_who.setText(return_payment_change[10] + " 님의 총 결제 금액 ▶▷ " + return_payment_change[2] + " 원.");
		
		label_payment_left1.setText("카드결제금액 : " + return_payment_change[3]);
		label_payment_left2.setText("현금결제금액 : " + return_payment_change[4]);
		label_payment_left3.setText("사용한포인트 : " + return_payment_change[6]);
		label_payment_left4.setText("현금영수증 유무 : " + return_payment_change[9]);
		label_payment_left5.setText("적립된 포인트 : " + return_payment_change[7]);
		
		label_payment_right.setText("<html><center>"
								  + "현금 → 카드<br> 현금주머니 버튼을"
								  + "<hr>"
								  + "카드 → 현금<br> 오른쪽 카드 버튼을<br>"
								  + "<hr>"
								  + "원하시는 버튼을 눌러주세요"
								  + "</center></html>");	
	}
	 
	// 업데이트 
	public void data_update(String sql) {
		
		try {
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");
            System.out.println(sql);
            PreparedStatement cash_to_credit = conn.prepareStatement(sql);
			
            cash_to_credit.executeUpdate();
			
            cash_to_credit.close();
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
						+ "a.RECEIPT_NO,"		// 영수증 번호
						+ "a.DATETIME,"			// 결제시간
						+ "a.TOTAL,"			// 총 결제 금액
						+ "a.CREDIT,"			// 카드결제금액 
						+ "a.CASH,"				// 현금결제금액
						+ "a.CUS_NO,"			// 고객번호
						+ "a.POINT_USED,"		// 사용포인트
						+ "a.POINT_SAVED,"		// 적립포인트
						+ "a.STATE,"			// 결제상태
						+ "a.RECEIPT_CHK,"		// 현금영수증 유무
						+ "b.NAME,"				// 고객 이름
						+ "b.TEL,"				// 고객 전화번호
						+ "b.POINT"				// 보유 포인트
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
                };
                
                while (rs_list.next()) {
            		
            		for (int i = 0; i < columnNames.length; i++) {
        				columnData[i] = rs_list.getString(columnNames[i]);
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

