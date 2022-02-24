package handler;

import java.awt.Component;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import baseSettings.DBConnector;
import baseSettings.cashPopUpp;


// 금액 계산 버튼 (현금 + 카드 합침, 현금 먼저 입력)

public class CashActionHandler implements ActionListener {

	
	  JTextField textField;
	  JTable calcTable;
	  int cashMoney;
	  int cardMoney;
	  boolean cashReceipt;
	  int cashReceiptCheck;
	  MemberShipActionListener msal;
	  int usedPoint, usedCusNum; 
	  String tel;
	  DefaultTableModel orderTableModel;
	  Frame frame;
	  private String num1, num2; // 사용자로부터 입력받는 첫번째수(num1), 두번째수(num2)
	  private int numOne, numTwo, sum; // 입력받은 첫번째수와 두번째수의 합계
	  private final String names[] = {"7","8","9","4","5","6","1","2","3","0"};
	  
	   public CashActionHandler(JTable calcTable, MemberShipActionListener msal, int usedPoint, int usedCusNum, String tel, DefaultTableModel orderTableModel, Frame frame) {
	      super();
	      this.calcTable = calcTable;
	      this.msal = msal;
	      this.orderTableModel = orderTableModel;
	      this.frame = frame;
	      this.usedPoint = usedPoint;
	      this.usedCusNum = usedCusNum;
	      msal.setUsePoint(usedPoint);
	      msal.setCus_no(usedCusNum);
	      
	      this.tel = tel;
	      System.out.println("cash이벤트에서 확인하는 포인트 값 : " + msal.getUsePoint());
	      System.out.println("cash이벤트에서 확인하는 Cus 값 : " + msal.getCus_no());
//	      System.out.println("cash이벤트에서 확인하는 포인트 값 : " + usedPoint);
//	      System.out.println("cash이벤트에서 확인하는 Cus 값 : " + usedCusNum);
	   }
	

	   
	   public int getCashMoney() {
		return cashMoney;
	   }



	   public void setCashMoney(int cashMoney) {
			this.cashMoney = cashMoney;
	   }



	@Override
		public void actionPerformed(ActionEvent e) {
		
		
		

		  int x_l;
		  int y_l;
		  Toolkit tk = Toolkit.getDefaultToolkit(); // 구현된 Toolkit객체를 얻는다.
		  //msal = new MemberShipActionListener(calcTable);
		  tel = msal.getMemTel();
		//usedPoint =  msal.getUsePoint();
		// usedCusNum = msal.getCus_no();
		  if(Integer.parseInt((String)calcTable.getValueAt(1, 1)) == 0) {
			  msal.setUsePoint(0);
			  msal.setCus_no(0);
		  }
		  System.out.println("결제클릭할때 확인하는 전화번호 : " + tel);
		  System.out.println("결제클릭할때 확인하는 포인트 값 : " + usedPoint);
		  System.out.println("결제클릭할때 확인하는 고객번호 : " + usedCusNum);
		  Dimension screenSize = tk.getScreenSize();
		  cashPopUpp pp = new cashPopUpp(calcTable, msal, msal.getUsePoint(), msal.getCus_no(), tel, orderTableModel);// 계산기 UI만든 내용
		//pp.setSize(220, 350);
		  pp.setSize(400, 300);
		  
		  
		  x_l = screenSize.width / 2 - pp.getWidth() / 2; 
		  y_l = screenSize.height / 2 - pp.getHeight() / 2; 
		  pp.setLocation(x_l, y_l);
		  //pp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  pp.setVisible(true);
		  pp.addWindowListener(new JFrameWindowClosingEventHandler());
		  
		  msal.setUsePoint(0);
		  msal.setCus_no(0);
		  usedPoint = 0;
		  usedCusNum = 0;
		  //usedPoint = 0;
		} // actionPerformed
	   
	   
		public static boolean isNumeric(String s) {
			try {
				Double.parseDouble(s);
				return true;
			} catch(NumberFormatException e) {
				return false;
			}
		}
}
