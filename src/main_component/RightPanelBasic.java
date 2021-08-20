package main_component;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import baseSettings.DBConnector;
import handler.SortMenuActionListener;


public class RightPanelBasic extends JPanel {

	JPanel rightLUp;
	JPanel rightLCenter;
	JPanel rightLDown; 
	JPanel rightL;
	JPanel rightR;

	ArrayList<ButtonSetting> sortMenu = new ArrayList<>();
//	ButtonSetting[] sortMenu = new ButtonSetting[8];

	MenuPanel coffee;

	public RightPanelBasic(JSplitPane jsp2, ActionListener ...als) {
		// als[0] = mbal, als[1] = msal, als[2] = cah, als[3] = managerActionListener als[4] = receiptActionListener
//		SortMenuActionListener smal = new SortMenuActionListener(this, als[0], sortMenu);
		// als[0]는 MenuButtonActionListener내용이다.
		setLayout(new BorderLayout());

		GridLayout gl1 = new GridLayout(2, 4);
		//		GridLayout gl2 = new GridLayout(3, 4);
		GridLayout gl3 = new GridLayout(1, 3);

		gl1.setVgap(40);
		gl1.setHgap(40);

		gl3.setVgap(40);
		gl3.setHgap(40);


		rightLUp = new JPanel(gl1);
		rightLCenter = new JPanel();
		rightLDown = new JPanel(gl3); 
		rightL = new JPanel(new BorderLayout());

		rightL.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		rightLUp.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		rightLCenter.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		rightLDown.setBorder(BorderFactory.createEmptyBorder(50 , 10 , 50 , 10));

		// 메뉴 윗버튼(종류 고르는 버튼들)
		// smal은 MenuButtonActionListener리스너자료형인데 매개변수로 MenuButtonActionListener내용을 받아서 전달해준다. 

		String sql = "select type FROM menu Group by type order by type";

		try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				
				sortMenu.add(new ButtonSetting (rs.getString("type"),20, 0xA9D0F5, null));
				rightLUp.add(sortMenu.get(cnt));
				cnt++;
			}
			
			for(int i = cnt; i < 8; i++) {
				rightLUp.add(new UnusedButtonSetting("", 20, 0xeeeeee, null));
			}
			
			for(int i = 0; i < sortMenu.size(); i++) {
				sortMenu.get(i).setActionListener(new SortMenuActionListener(this, als[0], sortMenu));
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 맨 아래 버튼(영수증관리, 멤버쉽, 결제 버튼)
		ButtonSetting ManageReceipts = new ButtonSetting("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>",25 ,0xCEE3F6, als[4]);
		rightLDown.add(ManageReceipts);

		ButtonSetting MemberShip = new ButtonSetting("<HTML><body style='text-align:center'>멤버쉽</body></HTML>",25,0xCED8F6, als[1] );
		rightLDown.add(MemberShip);

		ButtonSetting Payment = new ButtonSetting("<HTML><body style='text-align:center'>결제</body></HTML>", 25, 0xCECEF6, als[2]);
		rightLDown.add(Payment);



		rightL.add(rightLUp, "North");
		rightL.add(rightLCenter, "Center");
		rightL.add(rightLDown, "South");

		// 오른쪽 판넬의 위아래 버튼 있는쪽

		rightR = new JPanel();
		rightR.setLayout(new GridLayout(3,1));

		//		up = new ButtonSetting("△", 25 ,0xb0e8f7, null);
		//		down = new ButtonSetting("▽", 25 ,0xb0e8f7, null);

		rightR.setBorder(BorderFactory.createEmptyBorder(200 , 5 , 400 , 15));
		//		rightR.add(up);
		//		rightR.add(down);

		coffee = new MenuPanel("coffee", this, als[0]); 
		//als[0]은 "메뉴에 있는 문자를 읽어서 테이블목록에 추가하기" 기능의 액션이 들어있다.

		// 오른쪽 판넬

		add("West", rightL);
		add("East", rightR);
		jsp2.setLeftComponent(rightL);
		jsp2.setRightComponent(rightR);
		add("Center",jsp2);


	}// RightPanelBasic생성자 끝.


	public JPanel getRightLCenter() {
		return rightLCenter;
	}

	public JPanel getRightR() {
		return rightR;
	}

	public void setRightLCenter(JPanel rightLCenter) {
		this.rightLCenter = rightLCenter;
	}


//	public ButtonSetting getSortCoffee() {
//		return sortCoffee;
//	}
//
//
//	public ButtonSetting getSortDrink() {
//		return sortDrink;
//	}
//
//
//	public ButtonSetting getSortBlended() {
//		return sortBlended;
//	}
//
//
//	public ButtonSetting getSortMD() {
//		return sortMD;
//	}





}
