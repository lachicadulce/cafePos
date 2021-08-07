package Manager_file;

import java.awt.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

import baseSettings.*;

public class TotalLabel extends JLabel {
	
	private ArrayList<JLabel> labels;
	private DecimalFormat fm;
	
	public TotalLabel() { 
		labels = new ArrayList<>();
		// 금액에 , 표시되도록 설정
		fm = new DecimalFormat("###,###");
		String sql = "SELECT sum(credit) AS dsum, sum(cash) AS hsum, sum(total) AS total "
				+ "FROM history_payment WHERE state = 'complete'";

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
				labels.add(new JLabel("◇ 현금 매출 합계 ◇") {
					{
					setFont(new Font("", Font.BOLD, 18));
					setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("" + fm.format(hsum) + "원　") {
					{
					setFont(new Font("", Font.BOLD, 18));
				    setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("◇ 카드 매출 합계 ◇") {
					{
					setFont(new Font("", Font.BOLD, 18));
				    setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel(""+ fm.format(dsum) + "원　") {
					{
					setFont(new Font("", Font.BOLD, 18));
					setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("▶ cafe 매출 총 합계 ◀") {
					{
					setForeground(new Color(0x0202DE));
					setFont(new Font("", Font.BOLD, 19));
				    setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("" + fm.format(total) + "원　") {
					{
					setForeground(new Color(0x0202DE));
					setFont(new Font("", Font.BOLD, 19));
				    setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<JLabel> getLabels(){
		return labels;
	}
}
