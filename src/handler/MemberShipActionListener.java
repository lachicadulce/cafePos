package handler;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// 멤버쉽 버튼 액션 리스너
// 번호를 조회해서 포인트 확인 후 사용가능

public class MemberShipActionListener implements ActionListener {



	JTable calcTable;
	
	int usePoint;
	int cus_no;
	
	public MemberShipActionListener(JTable calcTable) {
		this.calcTable = calcTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String memTel = JOptionPane.showInputDialog(null, "전화번호를 입력하세요", "멤버쉽", 1);
		String memFindsql = "select * FROM customer_info where tel LiKE ?" ;
		String memPointDeductionSql = "UPDATE customer_info SET last_visit = sysdate,  point = point - ? WHERE tel LIKE ?" ;
		String name;
		String tel;
		int point = 0;	

		DefaultTableModel memshipTableModel = new DefaultTableModel(); 
		JTable memshipTable = new JTable(memshipTableModel);
		JScrollPane memshipScrollPanel = new JScrollPane(memshipTable);
		memshipScrollPanel.setPreferredSize(new Dimension(300, 100));

		memshipTableModel.addColumn("이름");
		memshipTableModel.addColumn("전화번호");
		memshipTableModel.addColumn("포인트");

		if(memTel != null) {


			try (
					Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL", "cafe", "!!22Qorthdud");	
					PreparedStatement memFindpstmt  = conn.prepareStatement(memFindsql);
					PreparedStatement memPointpstmt  = conn.prepareStatement(memPointDeductionSql);
					) {

				memFindpstmt.setString(1, "%"+memTel+"%");
				ResultSet rs = memFindpstmt.executeQuery();
				JPanel panel = new JPanel();
				JLabel label = new JLabel("사용할 포인트를 입력해 주세요\n");

				panel.add(label);
				panel.add(memshipScrollPanel);


				while(rs.next()) {
					name = rs.getString("name");
					tel = rs.getString("tel");
					point = rs.getInt("point");
					cus_no = rs.getInt(cus_no);

					memshipTableModel.addRow(new Object[] {name, tel, point}); //행추가
				}

				String usePointstr = JOptionPane.showInputDialog(null, panel, "멤버쉽", 1);
				usePoint = Integer.parseInt(usePointstr);
				if( usePoint > point) {
					JOptionPane.showMessageDialog(null, "포인트가 부족합니다.", "오류", 0);
				} else {
					calcTable.setValueAt(usePointstr, 1, 1);
					memPointpstmt.setInt(1, usePoint);
					memPointpstmt.setString(2, "%"+memTel+"%");
					memPointpstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "포인트사용이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(cus_no == 0) {
					cus_no = 1000;
				}
				
				
				rs.close();



			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {

		}

	}

}
