package Manager_file;

import java.awt.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import baseSettings.*;

public class TotalLabel extends JLabel {
	
	private ArrayList<JLabel> labels;
	
	public TotalLabel() { 
		labels = new ArrayList<>();
		String sql = "SELECT sum(credit) AS dsum, sum(cash) AS hsum, sum(total) AS total "
				+ "FROM history_payment WHERE state = 'complete'";

	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	ResultSetMetaData meta = rs.getMetaData();
	    	
			while(rs.next()) {
				int dsum = rs.getInt("dsum");
				int hsum = rs.getInt("hsum");
				int total = rs.getInt("total");
				labels.add(new JLabel("현금 매출 합계: " + hsum + "원") {
					{
					setFont(new Font("", Font.BOLD, 16));
					setPreferredSize(new Dimension(1000,35));
					setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("카드 매출 합계: " + dsum + "원") {
					{
					setFont(new Font("", Font.BOLD, 16));
				    setPreferredSize(new Dimension(1000,35));
				    setHorizontalAlignment(SwingConstants.RIGHT);
					}
				});
				labels.add(new JLabel("<cafe 매출 총 합계> : " + total + "원") {
					{
					setFont(new Font("", Font.BOLD, 20));
				    setPreferredSize(new Dimension(1000,35));
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
