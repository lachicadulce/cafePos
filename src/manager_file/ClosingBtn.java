package manager_file;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.*;
import java.sql.*;

import javax.swing.*;

import baseSettings.*;

// 마감 용지 출력 (txt파일 저장) ActionListener 설정
public class ClosingBtn implements ActionListener {
	// 오늘날짜 매출 기록 조회
	private String sql = "SELECT SUM(credit) AS card, "
			+ "SUM(cash) AS cash, SUM(total) AS total "
			+ "FROM history_payment WHERE state = 'complete' "
			+ "AND datetime > (select to_char(sysdate +1,'yyyy-mm-dd') from dual) ";
	//저장되는 txt 파일설정
	private File f = new File("sales_Slip.txt"); 
	
	public ClosingBtn() {
		super();
	}
	
	// 마감용지출력 버튼 클릭 시 [출력 완료] 팝업 출력 설정
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane end = new JOptionPane();				
		Font f1 = new Font("", Font.BOLD, 15);
		UIManager.put("OptionPane.messageFont", f1);
		UIManager.put("OptionPane.minimumSize",new Dimension(200,100)); 
		sumDB();
		end.showMessageDialog(null, "마감 용지 출력이 완료되었습니다.", "마감 용지 출력", JOptionPane.PLAIN_MESSAGE);
	}
	
	// 매출 합계 DB 불러오기 후 txt 파일에 저장
	public void sumDB() {
		try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
			BufferedWriter out = new BufferedWriter(new FileWriter(f, Charset.forName("UTF-8")));
	    	){
	    	ResultSetMetaData meta = rs.getMetaData();
	    	
			while(rs.next()) {
				int card = rs.getInt("card");
				int cash = rs.getInt("cash");
				int total = rs.getInt("total");
				System.out.println("◇ 현금 매출 합계 ◇ " + cash + " 원\n" 
						+ "◇ 카드 매출 합계 ◇ " + card + " 원\n" 
						+ "▶ cafe 매출 총 합계 ◀ " + total + " 원\n");
				
				out.write("◇ 현금 매출 합계 ◇");
				out.write(""+ cash);
				out.write("\n◇ 카드 매출 합계 ◇");
				out.write(""+ card);
				out.write("\n▶ cafe 매출 총 합계 ◀");
				out.write(""+ total);
				out.close();
			}
			System.out.println("매출 전표 저장 완료");

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
