package manager_file;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.*;
import java.sql.*;
import java.text.*;
import java.util.Date;

import javax.swing.*;

import baseSettings.*;

// 마감 용지 출력 (txt파일 저장) ActionListener 설정
public class ClosingBtn implements ActionListener {
	// 오늘날짜 매출 기록 조회
	private String sql = "SELECT SUM(credit) AS card, "
			+ "SUM(cash) AS cash, SUM(total) AS total "
			+ "FROM history_payment WHERE state = 'complete' "
			+ "AND datetime > (select to_char(sysdate +1,'yyyy-mm-dd') from dual) ";
	
	public ClosingBtn() {
		super();
	}
	
	// 마감용지출력 버튼 클릭 시 [출력 완료] 팝업 출력 설정
	@Override
	public void actionPerformed(ActionEvent e) {
		if(JOptionPane.showConfirmDialog(null, "마감용지를 출력 하시겠습니까?", "출력", 0) == 0) {
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date showdate = new Date();
			dateFormat2.format(showdate);
			JOptionPane end = new JOptionPane();				
			Font f1 = new Font("", Font.BOLD, 15);
			UIManager.put("OptionPane.messageFont", f1);
			UIManager.put("OptionPane.minimumSize",new Dimension(220,100)); 
			sumDB();
			end.showMessageDialog(null, "[ " + dateFormat2.format(showdate)+ " ]" 
						+ "\n마감 용지 출력이 완료되었습니다.", "마감 용지 출력", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane cancel = new JOptionPane();
			Font f1 = new Font("", Font.BOLD, 15);
			UIManager.put("OptionPane.messageFont", f1);
			UIManager.put("OptionPane.minimumSize",new Dimension(220,100)); 
			cancel.showMessageDialog(null, "마감용지 출력을 취소했습니다.", "출력 취소", JOptionPane.OK_OPTION);
		}
	}
	
	// 매출 합계 DB 불러오기 후 txt 파일에 저장
	public void sumDB() {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date savedate = new Date();
		Date showdate = new Date();
		
		File dir = new File("sales");
		// sales폴더가 없으면 해당 디렉토리를 생성
		if (!dir.exists()) { 
			dir.mkdir();
		}
		
		// sales 폴더안에 출력한 로컬 날짜로 파일을 저장
		File f = new File("sales/"+ dateFormat1.format(savedate) + ".txt");
		
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
				
				// 출력할 마감용지에 저장될 내용을 txt 파일에 저장
				out.write("[ 출력 일시: "+ dateFormat2.format(showdate) + " ]\n");
				out.write("◇ 현금 매출 합계 ◇ ");
				out.write(""+ cash + " 원\n");
				out.write("◇ 카드 매출 합계 ◇ ");
				out.write(""+ card + " 원\n");
				out.write("▶ cafe 매출 총 합계 ◀ ");
				out.write(""+ total + " 원");
				out.close();
			}
			System.out.println("매출 전표 저장 완료 " + dateFormat2.format(showdate));

		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
