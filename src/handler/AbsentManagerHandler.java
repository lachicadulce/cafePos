package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class AbsentManagerHandler implements ActionListener {

	// 근태 관리(출퇴근관리)
	// 팝업창을 사번을 입력하면 그 대상자에 대한 출퇴근 가능

	@Override
	public void actionPerformed(ActionEvent e) {


		String emp_no = JOptionPane.showInputDialog(null, "사번을 입력하세요", "근태관리", 1);

		String noChecksql = "SELECT * FROM absent_info ORDER BY a_no" ;
		String sql = "SELECT emp_no, name FROM employees_info WHERE emp_no = ?" ;
		String workSql = "SELECT start_work, fin_work FROM absent_info WHERE emp_no = ? ORDER BY start_work" ;
		String workInsert = "INSERT INTO absent_info VALUES(?,?, sysdate, ?)";
		//String finUpdate = "UPDATE absent_info SET fin_work = sysdate WHERE emp_no = ? AND start_work LIKE ?" ;
		String finUpdate = "UPDATE absent_info SET fin_work = sysdate WHERE emp_no = ?" ;
		// 출근 인서트 , 근태번호 어떻게 설정되는지 물어보기

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:MI:SS");
		String name = null;

		Date work = new Date(99, 01, 01);
		String workstr = null;
		Date time = new Date(System.currentTimeMillis());
		String timestr = null;

		int absent;

		boolean check = false;
		if (emp_no != null) {
			try (
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "cafe", "!!22Qorthdud");
					PreparedStatement pstmt = conn.prepareStatement(sql);
					PreparedStatement noCheckpstmt = conn.prepareStatement(noChecksql);
					PreparedStatement workpstmt = conn.prepareStatement(workSql);
					PreparedStatement workInsertpstmt = conn.prepareStatement(workInsert);
					PreparedStatement finUpdatepstmt = conn.prepareStatement(finUpdate);
					){//try시작

				// 최근 번호 구하기
				int recentNo = 0;
				ResultSet noCheckRs = noCheckpstmt.executeQuery();
				while(noCheckRs.next()) {
					recentNo = noCheckRs.getInt("a_no") + 1;
				}


				// 최근 출근시간 구하기
				workpstmt.setString(1, emp_no);
				ResultSet workrs = workpstmt.executeQuery();//입력받은 사번에 맞춰서 출퇴근 기록중에서 start_work가 오래된 순부터 해서 보여준다.
				timestr = format1.format(time);//제일 최근 시간을 format형태로 구하기
				System.out.println("timestr의 형태 : " + timestr);
				while(workrs.next()) {
					Date worktemp = workrs.getDate("start_work");

					if(work.before(worktemp)) { // a.before(b) : a가 b보다 더 이전 맞지?
						System.out.println("-----------------------------------------");
						System.out.println("work좀 확인해보자 : " + work);
						System.out.println("worktemp좀 확인해보자 : " + worktemp);
						
						work = worktemp;
					}
					workstr = format1.format(work);
					
					System.out.println("^^^^^^^^");
					System.out.println("workstr의 형태 : " + workstr);
					
				}

				
				// 이름 확인
				pstmt.setString(1, emp_no);
				ResultSet rs = pstmt.executeQuery();

				while(rs.next()) {
					name = rs.getString("name");
					check = true;
				}



				if(!check) {
					JOptionPane.showMessageDialog(null, "사번을 잘못입력하셨습니다.", "오류", 0);
				} else {
					String[] tempabsent = {"출근", "퇴근"};
					absent = JOptionPane.showOptionDialog(null, name+"님", "근태관리", 0, JOptionPane.QUESTION_MESSAGE, null, tempabsent, tempabsent[1]);
					// 0 : 출근 버튼 클릭 , 1 : 퇴근 버튼 클릭
					if(absent==0) {
						// 출근 체크
						workInsertpstmt.setInt(1, recentNo);
						workInsertpstmt.setString(2, emp_no);
						workInsertpstmt.setString(3, "");
						workInsertpstmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "출근확인됐습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
					} else if (absent == 1){
						if(workstr.equals(timestr)) {
							// 퇴근 체크
							finUpdatepstmt.setString(1, emp_no);
							System.out.println("퇴근을 위한 시간체크1 : " + workstr);
							System.out.println("퇴근을 위한 시간체크2 : " + timestr);
							//finUpdatepstmt.setString(2, timestr);
							finUpdatepstmt.executeUpdate();
							JOptionPane.showMessageDialog(null, "퇴근확인됐습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "아직 출근기록이 없습니다.", "오류", 0);
						}
					}
				}
				
				noCheckRs.close();
				rs.close();
				workrs.close();
			//try끝
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}




	}

}
