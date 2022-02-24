package baseSettings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import exception.positiveException;
import handler.MemberShipActionListener;

public class cardPopUpp extends JFrame{

	private String num1, num2; // 사용자로부터 입력받는 첫번째수(num1), 두번째수(num2)
	private int numOne, numTwo, sum; // 입력받은 첫번째수와 두번째수의 합계
	private final String names[] = {"7","8","9","4","5","6","1","2","3","0","<","E"};
	private int cardMoney;
	//int change;
	private MemberShipActionListener msal;
	String tel;
	int usePoint;
	
	public cardPopUpp(JTable calcTable, MemberShipActionListener msal,
					DefaultTableModel orderTableModel, int change, boolean cashReceipt, String tel) throws positiveException{
		
		super("금액");
		this.cardMoney = change;
		this.msal = msal;
		this.msal.setMemTel(tel);
		usePoint = msal.getUsePoint();
		//(this.msal).setUsePoint(Integer.parseInt((String) calcTable.getValueAt(1, 1)));
		this.tel = tel;
		BorderLayout layout = new BorderLayout(10, 10);
		setLayout(layout);
		System.out.println("Popupp3에서 확인하는 msal내용 : " + usePoint);
		JPanel groupPanel = new JPanel(new BorderLayout(5, 2));
		
		JLabel title = new JLabel("CARD 계산기", SwingConstants.CENTER); 
		// 타이틀 라벨 생성
		groupPanel.add(title, BorderLayout.NORTH);
		
		JTextField textField = new JTextField("", SwingConstants.CENTER); 
		// 사용자입력을 받는 텍스트 필드
//		textField.setText("0");
		textField.setText(Integer.toString(change));
		System.out.println("popup3에서 출력하는 남은 잔돈 : " + textField.getText());
		textField.setHorizontalAlignment(JTextField.RIGHT); // 프롬프트를 오른쪽정렬 시킨다!!
		
		groupPanel.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 0 , 5));	
		groupPanel.add(textField, BorderLayout.CENTER);
		
		/*
		 * 숫자 키패드 생성하기!
		 */
		
		
		JPanel padPanel = new JPanel(new GridLayout(4,3,3,3));
		padPanel.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 0 , 5));
		
		JButton buttons[] = new JButton[names.length];
		
		for (int count = 0; count < names.length; count++) {
			buttons[count] = new JButton(names[count]);
			buttons[count].setBackground(new Color(0xE0ECF8));
			buttons[count].addActionListener(new ActionListener() {
				
				
				
				
				public void calcCardMoney(int total, int remainMoney, int actionchange) throws positiveException {
					
					remainMoney -= actionchange;
					// total => 전체결제 금액
					// remainMoney => 현금결제+ 포인트 결제하고 남은 금액
					// actionchange => 현금결제하고 카드계산기로 넘어가져서 textField에 적은 돈
					if(remainMoney > 0) { // remainMoney > 0 의 의미 => 
						throw new positiveException("결제 하지 않은 금액이 남았습니다.");
					}
					else {// 모든 계산을 완료할때의 처리
						orderTableModel.setRowCount(0);
						for(int i= 0; i < calcTable.getRowCount(); i++) {
							calcTable.setValueAt("0", i, 1);	
						}// 계산내용 전부 0으로 초기화
						payTotalCalc(total, actionchange);
					}
				}// 끝 - calcCardMoney
				
				public void payTotalCalc(int total, int payedCardMoney) {
					int cnt = 0;
					int addPoint = 0;
					//int cashMoney = total - payedCardMoney - Integer.parseInt((String) calcTable.getValueAt(1, 1));
					int cashMoney = total - payedCardMoney - usePoint;
					System.out.println("popupp3에 넘어와서 확인하는 포인트 금액 : " + usePoint);
					System.out.println("popupp3에 넘어와서 확인하는 cashMoney금액 : " + cashMoney);
					String historyPaymentSql = "INSERT INTO history_payment VALUES(?,sysdate, ?, ?, ?, ?, ?, ?, "+"'"+"complete"+"'"+", ?)";
					String memPointDeductionSql = "UPDATE customer_info SET last_visit = sysdate,  point = point - ? WHERE tel LIKE ?" ;
					String mmShipAddPointSql = "UPDATE customer_info SET point= ((SELECT point FROM customer_info WHERE cus_no = ?)+ ?) WHERE cus_no=?";
					String historyBeverageSql = "INSERT INTO history_beverage VALUES((SELECT MAX(no) FROM history_beverage)+1,"
							+ " ?, (SELECT menu_no FROM menu WHERE MNAME LIKE TRIM(?)), ?)";
					String hPCountSql = "SELECT MAX(receipt_no) AS cnt FROM history_payment";//결제내용이 담겨 있는 영수증 정보
					try(
							
						Connection conn = DBConnector.getConnection();
						PreparedStatement hPpstmt = conn.prepareStatement(historyPaymentSql);
						PreparedStatement memPointpstmt  = conn.prepareStatement(memPointDeductionSql);
						PreparedStatement addPointpstmt = conn.prepareStatement(mmShipAddPointSql);
						PreparedStatement hBpstmt = conn.prepareStatement(historyBeverageSql);
						PreparedStatement hPCntpstmt = conn.prepareStatement(hPCountSql);//결제 기록을 담은 내용
						){
						
						ResultSet rs = hPCntpstmt.executeQuery();

						while(rs.next()) {
							cnt = rs.getInt("cnt") + 1;
						}//while문 끝
						rs.close();
						
						//포인트 적립 하기
						addPoint = (total - msal.getUsePoint())/10;
						
						// a. history_payment DB에 추가 + 설정
						hPpstmt.setInt(1, cnt);
						hPpstmt.setInt(2, total);
						hPpstmt.setInt(3, cardMoney);
//						hPpstmt.setInt(4, cashMoney >= total ? cashMoney - change : cashMoney);//4번째 컬럼 : CASH
						hPpstmt.setInt(4, cashMoney);//4번째 컬럼 : CASH
						// cashMoney - change 는 순수하게 total값만 나오게 계산한 것.
						if(msal.getCus_no() == 0) { 
							hPpstmt.setInt(5, 1000);
							hPpstmt.setInt(7, 0);
						} else {
							hPpstmt.setInt(5, msal.getCus_no());
							hPpstmt.setInt(7, addPoint);
						}
						hPpstmt.setInt(6, msal.getUsePoint());
						
						// 현금영수증 한다고 할때 DB 처리하는 내용
						if(cashReceipt) {
							hPpstmt.setString(8, "Y");
						} else {
							hPpstmt.setString(8, "N");
						}
						hPpstmt.executeUpdate();
						System.out.println("history_payment DB add");
						//a. 끝 - history_payment DB에 추가 + 설정
						
						// b. 멤버쉽인 회원이 포인트 사용하면 포인트 감소 시키는 작업
						if(msal.getUsePoint() != 0) {
						  System.out.println("카드 계산에서 확이하는 사용된 포인트 : " + msal.getUsePoint());
						  System.out.println("카드 계산에서 확이하는 회원 전번 : " + msal.getMemTel());
	                      memPointpstmt.setInt(1, msal.getUsePoint());
	                      memPointpstmt.setString(2, "%"+msal.getMemTel()+"%");
	                      memPointpstmt.executeUpdate();
	                      System.out.println("customer_info DB add");
						}
						// b. 끝 - 멤버쉽인 회원이 포인트 사용하면 포인트 감소 시키는 작업
						
						// c. customer_info 에 멤버쉽 포인트 추가 + 설정
						if (msal.getCus_no() != 0 ) {
							addPointpstmt.setInt(1, msal.getCus_no());
							addPointpstmt.setInt(2, addPoint);
							addPointpstmt.setInt(3, msal.getCus_no());

							addPointpstmt.executeUpdate();

							System.out.println("PointUpdate");
						}
						// c. 끝 - customer_info 에 멤버쉽 포인트 추가 + 설정
						
						// d. history_beverage 데이터 DB에 추가
						hBpstmt.setInt(1, cnt);
						for(int i = 0; i < orderTableModel.getRowCount(); i++) {

							//				fMNpstmt.setString(1, (String)orderTableModel.getValueAt(i, 0) +"%");
							//				ResultSet fMNRS = fMNpstmt.executeQuery();

							String menuName = (String)orderTableModel.getValueAt(i, 0);
							hBpstmt.setString(2, menuName.trim());
							try {
								hBpstmt.setInt(3, (int)orderTableModel.getValueAt(i, 1));
							} catch (Exception e3) {
								hBpstmt.setString(3, (String)orderTableModel.getValueAt(i, 1));
							}

							hBpstmt.executeUpdate();
						}
						System.out.println("history_beverage DB add");
						//d. 끝 - history_beverage 데이터 DB에 추가
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
//					MainPos_xx m = new MainPos_xx();
//					m.setDefaultOptions();
				}// 끝 - payTotalCalc
				
				
				@Override
				public void actionPerformed(ActionEvent e){
					
					JButton btn = (JButton) e.getSource();
					String input = btn.getText();
					int actionchange;
					int remainMoney = Integer.parseInt((String) calcTable.getValueAt(0, 1))
							- Integer.parseInt((String) calcTable.getValueAt(1, 1))
							- Integer.parseInt((String) calcTable.getValueAt(2, 1));
					if(input == "<") {
						int len = textField.getText().length();
						char[] beforestr = textField.getText().toCharArray();
						String newstr="";
						
						if(len == 1) {
							textField.setText("0");
						}else if(len > 1) {
							for (int i = 0; i < beforestr.length-1; i++) {
								newstr += beforestr[i];
							}
							textField.setText(newstr);
						}
					}// (<) 버튼을 눌렀을때 행동.
					else if(input == "E") {// (0,1) = 총 금액 / (1,1) = 사용할 포인트  / (2, 1) = 현금으로 받은 금액
						// Integer.parseInt((String) calcTable.getValueAt(0, 1)) - 
						//calcTable.setValueAt(textField.getText(), 2, 1); //받은 금액 긁어오기
						
						//calcTable.setValueAt(,2, 1);
						//cardMoney = Integer.parseInt((String) calcTable.getValueAt(2, 1));
						
						actionchange = Integer.parseInt(textField.getText());//actionchange : 카드로 계산하려고 입력받은 돈.
						cardMoney = actionchange;
						dispose();
						input = "";
						//remainMoney = remainMoney - cardMoney;
						try {
							calcCardMoney(Integer.parseInt((String) calcTable.getValueAt(0, 1)), remainMoney, actionchange);
						} catch (positiveException e1) {
							calcTable.setValueAt("0", 1, 1);
							calcTable.setValueAt("0", 2, 1);
							
							JOptionPane.showMessageDialog(null, "결제를 할수 없습니다.","Message",JOptionPane.ERROR_MESSAGE);
							
						}
						
					}// E 눌렀을때
					
					if(textField.getText()!= null && input != "<") {
						if(textField.getText() == "0") {
							textField.setText("");
							textField.setText(input);
						}
						else {
							
							textField.setText(textField.getText() + input);
						}
					}//if
					else if(textField.getText() == null) {
						textField.setText(input);
					}
					
				}//actionPerformed
					
			});
			padPanel.add(buttons[count]);
		}//for문 => button에다가 AddActionListener를 붙이는 내용.
		////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * 하단 클리어 버튼 및 만든이 View
		 */
		
		JPanel infoView = new JPanel(new BorderLayout(10, 10));
		
		JButton clear = new JButton("Clear");// clear버튼 내용
		clear.setBackground(new Color(0xf5f3ed));
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					textField.setText("");
			}
		});//clear의 ActionListener
		
		infoView.add(clear, BorderLayout.NORTH);
		infoView.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 5 , 5));
		
		
		// 타이틀 라벨과 텍스트 필드를 담은 groupPanel을 전체 프레임의 최상단에 위치시킨다.
		add(groupPanel, BorderLayout.NORTH);
		add(padPanel, BorderLayout.CENTER);
		add(infoView, BorderLayout.SOUTH);
		
	}//Popupp3생성자 초기화
	



	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}

