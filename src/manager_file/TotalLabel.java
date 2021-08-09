package manager_file;

import java.awt.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import baseSettings.*;

public class TotalLabel extends JLabel {
	
	private ArrayList<JLabel> labels;
	private DecimalFormat fm;
	private String sql = "SELECT sum(credit) AS dsum, sum(cash) AS hsum, sum(total) AS total "
			+ "FROM history_payment WHERE state = 'complete'";
	
	public TotalLabel() { 
		labels = new ArrayList<>();
		// 금액에 , 표시되도록 설정
		fm = new DecimalFormat("###,###");

		for(int i = 0; i < 6; i++) {
			JLabel lb = new JLabel();
			if(i < 4) lb.setFont(new Font("", Font.BOLD, 18));
			else lb.setFont(new Font("", Font.BOLD, 19));
			lb.setHorizontalAlignment(SwingConstants.RIGHT);
			labels.add(lb);
		}
	    selDB();
	}
	
	public void selDB() {
		try (
		    	Connection conn = DBConnector.getConnection();
		    	PreparedStatement pstmt = conn.prepareStatement(sql);
		    	ResultSet rs = pstmt.executeQuery();
		    	){
		    	ResultSetMetaData meta = rs.getMetaData();
		    	
				while(rs.next()) {
					int hsum = rs.getInt("hsum");
					int dsum = rs.getInt("dsum");
					int total = rs.getInt("total");

					labels.get(0).setText("◇ 현금 매출 합계 ◇");
					labels.get(1).setText("" + fm.format(hsum) + "원　");
					labels.get(2).setText("◇ 카드 매출 합계 ◇");
					labels.get(3).setText(""+ fm.format(dsum) + "원　");
					labels.get(4).setText("▶ cafe 매출 총 합계 ◀");
					labels.get(5).setText("" + fm.format(total) + "원　");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void updateDB(String date_s, String date_e) {
		sql = "SELECT sum(credit) AS dsum, sum(cash) AS hsum, sum(total) AS total "
				+ "FROM history_payment WHERE state = 'complete' "
				+ "AND datetime BETWEEN " + date_s + " AND " + date_e + " + 1";
		selDB();
		
	}
	
	public ArrayList<JLabel> getLabels(){
		return labels;
	}
}
