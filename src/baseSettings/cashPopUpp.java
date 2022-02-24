package baseSettings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import exception.positiveException;

import java.awt.Toolkit;

import handler.MemberShipActionListener;

public class cashPopUpp extends JFrame{

	private String num1, num2; // 사용자로부터 입력받는 첫번째수(num1), 두번째수(num2)
	private int numOne, numTwo, sum; // 입력받은 첫번째수와 두번째수의 합계
	private final String names[] = {"7","8","9","4","5","6","1","2","3","0","<","E"};
	int total, change, sale;
	int cashReceiptCheck;
	String tel;
	boolean cashReceipt;
	///////////////////////////////////////////////////////////////////////////////////
	JTable calcTable;
	int cashMoney, cardMoney;
	DefaultTableModel orderTableModel;
	Frame frame;
	public MemberShipActionListener msal;
	int usedPoint;
	int usedCusNum;
	String input;
	public MemberShipActionListener getMsal() {
		return msal;
	}


	public void setMsal(MemberShipActionListener msal) {
		this.msal = msal;
	}


	public cashPopUpp(JTable calcTable, MemberShipActionListener msal, int usedPoint, int usedCusNum, String tel,
			DefaultTableModel orderTableModel) {
		
		super("금액");
		this.calcTable = calcTable;
		this.orderTableModel = orderTableModel;
		this.msal = msal;
		this.usedPoint = usedPoint;
		this.usedCusNum = usedCusNum;
		this.tel = tel;

		BorderLayout layout = new BorderLayout(10, 10);
		setLayout(layout);
		
		JPanel groupPanel = new JPanel(new BorderLayout(5, 2));
		 
		JLabel title = new JLabel("CASH 계산기", SwingConstants.CENTER); 
		// 타이틀 라벨 생성
		groupPanel.add(title, BorderLayout.NORTH);
		
		JTextField textField = new JTextField("", SwingConstants.CENTER); 
		// 사용자입력을 받는 텍스트 필드
//		textField.setText("0");
		int money = Integer.parseInt((String) calcTable.getValueAt(0, 1))
				- Integer.parseInt((String) calcTable.getValueAt(1, 1));
		
		textField.setText(Integer.toString(money));
		textField.setHorizontalAlignment(JTextField.RIGHT); // 프롬프트를 오른쪽정렬 시킨다!!
		
		groupPanel.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 0 , 5));	
		groupPanel.add(textField, BorderLayout.CENTER);
		
		total = Integer.parseInt((String)calcTable.getValueAt(0, 1));
		sale = Integer.parseInt((String)calcTable.getValueAt(1, 1));
		/*
		 * 숫자 키패드 생성하기!
		 */
		
		JPanel padPanel = new JPanel(new GridLayout(4,3,3,3));
		padPanel.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 0 , 5));

		JButton buttons[] = new JButton[names.length];
		
		
		// markedPrice : 전체금액에서 사용한 포인트금액만 뺀 금액
		int markedPrice = Integer.parseInt((String) calcTable.getValueAt(0, 1)) - Integer.parseInt((String)calcTable.getValueAt(1, 1));
		if(Integer.parseInt((String)calcTable.getValueAt(1, 1)) > 0) {
			System.out.println("markedPrice계산 확인하기위한 내용 : " + markedPrice);
			textField.setText(Integer.toString(markedPrice));
		}
		
		
		
		for (int count = 0; count < names.length; count++) {
			buttons[count] = new JButton(names[count]);
			buttons[count].setBackground(new Color(0xE0ECF8));
			buttons[count].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JButton btn = (JButton) e.getSource();
					String input = btn.getText();
					
					//-------------- "<" 버튼 눌렀을때 ------------------------------------------
					if(input == "<") {
						int len = textField.getText().length();
						char[] beforestr = textField.getText().toCharArray();
						String newstr="";
						
						if(len == 1) {
							textField.setText("0");//숫자 한개에서 < 버튼 누르면 0으로 내용 변경.
						}else if(len > 1) {
							for (int i = 0; i < beforestr.length-1; i++) {
								newstr += beforestr[i];// < 할때마다 숫자 1개씩 줄어드는 내용
							}
							textField.setText(newstr);
						}
					}// (<) 버튼을 눌렀을때 행동.
					
					//-------------- "E" 버튼 눌렀을때 ------------------------------------------
					else if(input == "E") {
						// (0,1) = 총 금액 / (1,1) = 사용할 포인트  / (2, 1) = 현금으로 받은 금액
						calcTable.setValueAt(textField.getText(), 2, 1);
						cashMoney = Integer.parseInt((String) calcTable.getValueAt(2, 1));
						change = total - cashMoney - sale; // 전체금액에서 현금, 포인트 뺀 돈
					
						dispose();
						input = "";
						
					}// E버튼 눌렀을때.
					
					//-------------- 숫자 버튼 눌렀을때 ------------------------------------------
					if(textField.getText()!= null && input != "<") {
						if(textField.getText().equals("0")) {

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
		//clear.setBackground(new Color(0xffffff));
		clear.setBackground(new Color(0xf5f3ed));
		clear.setFont(new Font(getName(), Font.PLAIN, 16));

		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
					textField.setText("");
			}
		});//clear의 ActionListener
		
		infoView.add(clear, BorderLayout.NORTH);
		infoView.setBorder(BorderFactory.createEmptyBorder(0 , 5 , 5 , 5));
		//JLabel infoV2 = new JLabel("kjy", SwingConstants.RIGHT);
		//infoView.add(infoV2, BorderLayout.SOUTH);
		
		// 타이틀 라벨과 텍스트 필드를 담은 groupPanel을 전체 프레임의 최상단에 위치시킨다.
		add(groupPanel, BorderLayout.NORTH);
		add(padPanel, BorderLayout.CENTER);
		add(infoView, BorderLayout.SOUTH);
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// x버튼 눌렀을때의 카드 계산으로 넘어가는 내용
		
		this.addWindowListener(new WindowAdapter() {
			
			int closingCheck = 0;
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//super.windowClosing(e);
				System.out.println("windowClosing실행");
				closingCheck = 1;
				dispose();
				
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
				System.out.println("windowClosed실행");
				int chk = 0;
				if(cashMoney > 0) {// 현금을 받았을때 현금영수증 동작을 위한 작업
					chk = 1;
					cashReceiptCheck = JOptionPane.showOptionDialog(null, "현금영수증 하시나요", "현금영수증 확인", 0, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(cashReceiptCheck == 0) {
						cashReceipt = true;
					}else {
						cashReceipt = false;
					}
				}
				//if(change > 0 && chk == 1) {
				if(change > 0 && closingCheck == 0) { //change > 0 이면 아직 결제할 돈이 남았다는 의미.
					cardPopUpp pp;
					try {
						int x_l;
						int y_l;
						Toolkit tk = Toolkit.getDefaultToolkit(); // 구현된 Toolkit객체를 얻는다.
						Dimension screenSize = tk.getScreenSize();
						
						msal.setCus_no(usedCusNum);
						msal.setUsePoint(usedPoint);
						System.out.println("cardPopUpp 호출하기전에 회원의 번호 : " + usedCusNum);
						System.out.println("cardPopUpp 호출하기전에 회원이 사용한 포인트 : " + usedPoint);
						//change = total - cashMoney - sale; // 전체금액에서 현금, 포인트 뺀 돈
						pp = new cardPopUpp(calcTable, msal, orderTableModel, change,
								cashReceipt, tel);
						//pp.setSize(210, 350);
						pp.setSize(400, 300);
						x_l = screenSize.width / 2 - pp.getWidth() / 2; 
						y_l = screenSize.height / 2 - pp.getHeight() / 2; 
						pp.setLocation(x_l, y_l);
				  		  //pp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						pp.setVisible(true);
						//pp.addWindowListener(new JFrameWindowClosingEventHandler());
						
					} catch (positiveException e1) {
						
						e1.printStackTrace();
					}

					

				}// 현금까지만으로 이미 완전히 결제를 완료했을때
				else if(change <= 0 && closingCheck == 0) {
					//change < 0 인 상황은 거슬러줄 돈이 있는 상황인거임.
					calcTable.setValueAt(Integer.toString(-change), 3, 1);
					
					payTotalCalc(total, change, usedPoint, usedCusNum);
				}
				 
			}
			//////////계산기 기능에 붙인 action내용들.
			
			
		});
		// 끝 - x버튼 눌렀을때의 카드 계산으로 넘어가는 내용
		
		//DB작업하기!!!!!!!!!!!


	}//Popupp생성자 초기화
	
	public String getInput() {
		return input;
	}


	public void setInput(String input) {
		this.input = input;
	}


	// 계산하기 함수 = payTotalCalc
	public void payTotalCalc(int total, int remainMoney, int usedPoint, int usedCusNum) {
		int cnt = 0;
		int addPoint = 0;
		int cashMoney = total - remainMoney - usedPoint;//remainMoney: cash, 포인트 내고 남은 돈.

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
			//addPoint = (total - msal.getUsePoint())/10;
			addPoint = (total - usedPoint)/10;
			System.out.println("addPoint갑 확인하기 : " + addPoint);
			// a. history_payment DB에 추가 + 설정
			hPpstmt.setInt(1, cnt);
			hPpstmt.setInt(2, total);
			hPpstmt.setInt(3, remainMoney);
			hPpstmt.setInt(4, cashMoney > total ? cashMoney - change : cashMoney);
			if(usedCusNum == 0) { 
				hPpstmt.setInt(5, 1000);
				hPpstmt.setInt(7, 0);
			} else {
				hPpstmt.setInt(5, usedCusNum);
				hPpstmt.setInt(7, addPoint);
			}
			hPpstmt.setInt(6, usedPoint);
			
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
			if(usedPoint != 0) {
              memPointpstmt.setInt(1, usedPoint);
              memPointpstmt.setString(2, "%"+tel+"%");
              memPointpstmt.executeUpdate();
              System.out.println("customer_info DB add");
			}
			// b. 끝 - 멤버쉽인 회원이 포인트 사용하면 포인트 감소 시키는 작업
			
			// c. customer_info 에 멤버쉽 포인트 추가 + 설정
			if (usedCusNum != 0) {
				System.out.println("회원이 아닌 번호 1000인지 확인 : " + usedCusNum);
				addPointpstmt.setInt(1, usedCusNum);
				addPointpstmt.setInt(2, addPoint);
				addPointpstmt.setInt(3, usedCusNum);

				addPointpstmt.executeUpdate();

				System.out.println("PointUpdate");
			}else {
				System.out.println("회원이 아닌 번호 1000인지 확인2 : " + usedCusNum);
				addPointpstmt.setInt(1, usedCusNum);
				addPointpstmt.setInt(2, 0);
				addPointpstmt.setInt(3, usedCusNum);

				addPointpstmt.executeUpdate();
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
			//addPoint = 0;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}//catch
//		this.usedPoint = 0;
//		this.usedCusNum = 0;
//		MainPos_xx m = new MainPos_xx();
//		m.setDefaultOptions();
		System.out.println("----------------------------------");
	}// 끝 - payTotalCalc
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}// 끝 - isNumeric
	
}
