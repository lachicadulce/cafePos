package main_component;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import baseSettings.DBConnector;
import handler.ChangeMenuDownActionListener;
import handler.ChangeMenuUpActionListener;


public class MenuPanel extends JPanel {
	String sort;
	CardLayout card;
	int count;
	
	static String sql1 = "SELECT * FROM menu WHERE type= ? ORDER BY display_order";
	
	
	public MenuPanel(String sort, RightPanelBasic rpb, ActionListener al) {
		this.sort = sort;
		card = new CardLayout();
		JPanel rightCenter = rpb.getRightLCenter();
		JPanel rightR = rpb.getRightR();
		ButtonSetting up = new ButtonSetting("▲", 15 ,0xE0E6F8, null);
		ButtonSetting down  = new ButtonSetting("▼", 15 ,0xE0E6F8, null);
			
		rightCenter.setLayout(card);
		
		GridLayout gl2 = new GridLayout(4, 4);
		
		gl2.setVgap(10); 
		gl2.setHgap(10);
		
		JPanel panel1 = new JPanel(gl2);
		JPanel panel2 = new JPanel(gl2);
		JPanel panel3 = new JPanel(gl2);
		
		rightR.removeAll();
		rightCenter.removeAll();
		
		count = 1;
		try(
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				
		){		
			pstmt1.setString(1, sort);
			ResultSet rs = pstmt1.executeQuery();
			
			while(rs.next()) {
				String menuName = rs.getString("mname");
				int menuPrice = rs.getInt("price");
				String[] parsedName = menuName.split(" ");
				String htmlName = "<HTML><body style='text-align:center'>";
				for (int i = 0; i < parsedName.length; i++) {
					htmlName += parsedName[i] + "<br>";
				}
				htmlName += Integer.toString(menuPrice);
				htmlName += "</body></HTML>";
				if(count > 48) {
					JOptionPane.showMessageDialog(null,"각 분류별 최대 48개까지 등록이 가능합니다.","Message"
							,JOptionPane.ERROR_MESSAGE);
				}// type마다 48개초과로 등록될경우 에러메시지팝업을 띄우게 작성함.(만들어는 놨지만 테스트는 안해보고 다른 대체방안이 필요해보임)
				else if(count > 32) {
					panel3.add(new ButtonSetting(htmlName, 15, 0xEFF5FB, al));
				} else if (count > 16) {
					panel2.add(new ButtonSetting(htmlName, 15, 0xEFF5FB, al));
				} else{
					panel1.add(new ButtonSetting(htmlName, 15, 0xEFF5FB, al));
				}
				count++;
				
			}
			while(count <= 48) {
				
				if(count > 32) {
					panel3.add(new UnusedButtonSetting("", 15, 0xeeeeee, al));
				} else if (count > 16) {
					panel2.add(new UnusedButtonSetting("", 15, 0xeeeeee, al));
				} else {
					panel1.add(new UnusedButtonSetting("", 15, 0xeeeeee, al));
				}
				count++;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		up.addActionListener(new ChangeMenuUpActionListener(rightCenter));
		down.addActionListener(new ChangeMenuDownActionListener(rightCenter));
		
		rightR.add(up);
		rightR.add(down);
		
		rightCenter.add(panel1);
		rightCenter.add(panel2);
		rightCenter.add(panel3);
				
	}
}
