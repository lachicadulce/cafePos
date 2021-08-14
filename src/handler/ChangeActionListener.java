package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import baseSettings.DBConnector;
import main_component.ButtonSetting;

// 환전계산 버튼 액션 리스너
// 주문서의 총금액과 금액계산 후 누르면 거스름돈 계산해줌

public class ChangeActionListener implements ActionListener {

	JTable calcTable;
	CashActionHandler cah;
	MemberShipActionListener msal;
	DefaultTableModel orderTableModel;
	
	public ChangeActionListener(JTable calcTable, CashActionHandler cah, MemberShipActionListener msal, DefaultTableModel orderTableModel) {
		this.calcTable = calcTable;
		this.cah = cah;
		this.msal = msal;
		this.orderTableModel = orderTableModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int[] priceSaleReceived = new int[3];
		for(int i=0; i < calcTable.getRowCount() -1; i++) {
			if((String)calcTable.getValueAt(i, 1) == null) {
				priceSaleReceived[i] = 0;
			} else {
				priceSaleReceived[i] = Integer.parseInt((String)calcTable.getValueAt(i, 1));
			}

		}

		int change = priceSaleReceived[0] - priceSaleReceived[1]- priceSaleReceived[2];

		
		if(change <= 0)  {
			change = Math.abs(change);
		} else {

		}
		
		String changestr = Integer.toString(change);
		calcTable.setValueAt(changestr, 3, 1);
		
		

		// DB에 데이터 넣기

		String historyPaymentSql = "INSERT INTO history_payment VALUES(?,sysdate, ?, ?, ?, ?, ?, ?, "+"'"+"complete"+"'"+", ?)";
		
		String mmShipAddPointSql = "UPDATE customer_info SET point= ((SELECT point FROM customer_info WHERE cus_no = ?)+ ?) WHERE cus_no=?";
		
		String historyBeverageSql = "INSERT INTO history_beverage VALUES((SELECT MAX(no) FROM history_beverage)+1, ?, (SELECT menu_no FROM menu WHERE MNAME LIKE TRIM(?)), ?)";
		
//		String findmenuNoSql = "SELECT menu_no FROM menu WHERE MNAME LIKE ?";
		
		String hPCountSql = "SELECT MAX(receipt_no) AS cnt FROM history_payment";
		int cnt = 0;
		int addPoint = 0;
		try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement hPpstmt = conn.prepareStatement(historyPaymentSql);
				PreparedStatement hPCntpstmt = conn.prepareStatement(hPCountSql);	
				
				){
			; 
			ResultSet rs = hPCntpstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt("cnt") + 1;
			}
			rs.close();
			
			addPoint = (priceSaleReceived[0] - msal.usePoint)/10;
			
			// history_payment DB에 추가 + 설정
			hPpstmt.setInt(1, cnt);
			hPpstmt.setInt(2, priceSaleReceived[0]);
			hPpstmt.setInt(3, cah.cardMoney);
			hPpstmt.setInt(4, cah.cashMoney > priceSaleReceived[0] ? cah.cashMoney - change : cah.cashMoney);
//			hPpstmt.setInt(5, msal.cus_no); // 고객번호 주석처리하고 1000번 테스트한다고 넣어놨어요. 아래쪽 포인트 조회쪽도 같아여 확인해주세용. 210813[백소영]
			hPpstmt.setInt(5, 1000);
			hPpstmt.setInt(6, msal.usePoint);
			hPpstmt.setInt(7, addPoint);
			
			if( cah.cashReceipt) {
				hPpstmt.setString(8, "Y");
			} else {
				hPpstmt.setString(8, "N");
			}
//			System.out.println("history_payment DB1");
			hPpstmt.executeUpdate();
			System.out.println("history_payment DB add");
			
			

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		try(
				Connection conn = DBConnector.getConnection();
				PreparedStatement addPointpstmt = conn.prepareStatement(mmShipAddPointSql);
				PreparedStatement hBpstmt = conn.prepareStatement(historyBeverageSql);
//				PreparedStatement fMNpstmt = conn.prepareStatement(findmenuNoSql);	
				
				) {
			
			// customer_info 에 멤버쉽 포인트 추가 + 설정
//			addPointpstmt.setInt(1, msal.cus_no);
			addPointpstmt.setInt(1, 1000);
			addPointpstmt.setInt(2, addPoint);
//			addPointpstmt.setInt(3, msal.cus_no);
			addPointpstmt.setInt(3, 1000);
			
			addPointpstmt.executeUpdate();
			
			// history_beverage 데이터 DB에 추가
			
			hBpstmt.setInt(1, cnt);
			
			int menuNo = 0;
			for(int i = 0; i < orderTableModel.getRowCount(); i++) {
				hBpstmt.setString(2,(String)orderTableModel.getValueAt(i, 0));
				hBpstmt.setInt(3, (int)orderTableModel.getValueAt(i, 1));
				
				hBpstmt.executeUpdate();
			}
			System.out.println("history_beverage DB add");
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}



	}

}
