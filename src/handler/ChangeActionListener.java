package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;

import baseSettings.DBConnector;
import main_component.ButtonSetting;

// 환전계산 버튼 액션 리스너
// 주문서의 총금액과 금액계산 후 누르면 거스름돈 계산해줌

public class ChangeActionListener implements ActionListener {

	JTable calcTable;
	CashActionHandler cah;
	MemberShipActionListener msal;

	public ChangeActionListener(JTable calcTable, CashActionHandler cah, MemberShipActionListener msal) {
		this.calcTable = calcTable;
		this.cah = cah;
		this.msal = msal;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int[] priceSaleReceived = new int[3];
		for(int i=0; i < calcTable.getRowCount() -1; i++) {
			if((String)calcTable.getValueAt(i, 1) == "") {
				priceSaleReceived[i] = 0;
			} else {
				priceSaleReceived[i] = Integer.parseInt((String)calcTable.getValueAt(i, 1));
			}

		}

		int change = priceSaleReceived[0] - priceSaleReceived[1]- priceSaleReceived[2];

		System.out.println(change);
		if(change <= 0)  {
			change = Math.abs(change);
		} else {

		}
		String changestr = Integer.toString(change);
		calcTable.setValueAt(changestr, 3, 1);

		// DB에 데이터 넣기

		String historyPaymentSql = "INSERT INTO history_payment VALUES(?,sysdate, ?, ?, ?, ?, ?, complete, ?)";
		String mmShipAddPointSql = "UPDATE cusomer_info SET point= ((SELECT point FROM customer_info WHERE cus_no = ?)+ ?) WHERE cus_no=?;";
		String historyBeverageSql = "INSERT INTO history_beverage VALUES((SELECT COUNT(*) FROM history_beverage)+1, ?, ?, ?)";
		
		String hPCountSql = "SELECT COUNT(*) cnt FROM history_payment";
		

		try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement hPpstmt = conn.prepareStatement(historyPaymentSql);
				PreparedStatement addPointpstmt = conn.prepareStatement(mmShipAddPointSql);
				PreparedStatement hPCntpstmt = conn.prepareStatement(hPCountSql);	
				PreparedStatement hBpstmt = conn.prepareStatement(historyBeverageSql);	
				
				){
			int cnt = 0; 
			ResultSet rs = hPCntpstmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt("cnt") + 1;
			}
			int addPoint = (priceSaleReceived[0] - msal.usePoint)/10;
			
			// history_payment DB에 추가 + 설정
			hPpstmt.setInt(1, cnt);
			hPpstmt.setInt(2, priceSaleReceived[0]);
			hPpstmt.setInt(3, cah.cardMoney);
			hPpstmt.setInt(4, cah.cashMoney);
			hPpstmt.setInt(5, msal.cus_no);
			hPpstmt.setInt(6, msal.usePoint);
			hPpstmt.setInt(7, addPoint);
			
			if( cah.cashReceipt) {
				hPpstmt.setString(8, "Y");
			} else {
				hPpstmt.setString(8, "N");
			}
			hPpstmt.executeUpdate();
			
			// customer_info 에 멤버쉽 포인트 추가 + 설정
			addPointpstmt.setInt(1, msal.cus_no);
			addPointpstmt.setInt(2, addPoint);
			addPointpstmt.setInt(3, msal.cus_no);
			
			addPointpstmt.executeUpdate();
			
			// history_beverage 데이터 DB에 추가
			

			

		} catch (SQLException e1) {

			e1.printStackTrace();
		}



	}

}
