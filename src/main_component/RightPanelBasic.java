package main_component;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import handler.SortMenuActionListener;
import main_component.BasicButton;


public class RightPanelBasic extends JPanel {
	
	JPanel rightLUp;
	JPanel rightLCenter;
	JPanel rightLDown; 
	JPanel rightL;
	JPanel rightR;
	
	ButtonSetting sortCoffee;
	ButtonSetting sortDrink;
	ButtonSetting sortBlended;
	ButtonSetting sortMD;
	MenuPanel coffee;
	
	public RightPanelBasic(JSplitPane jsp2, ActionListener ...als) {
		// als[0] = mbal, als[1] = msal, als[2] = cah
		SortMenuActionListener smal = new SortMenuActionListener(this, als[0]);
		// als[0]는 MenuButtonActionListener내용이다.
		setLayout(new BorderLayout());
		
		GridLayout gl1 = new GridLayout(1, 4);
//		GridLayout gl2 = new GridLayout(3, 4);
		GridLayout gl3 = new GridLayout(1, 3);
		
		gl1.setVgap(40);
		gl1.setHgap(40);
		
//		gl2.setVgap(40);
//		gl2.setHgap(40);
		
		gl3.setVgap(40);
		gl3.setHgap(40);
		
//		JPanel pages1 = new JPanel(new CardLayout());
//		JPanel pages2 = new JPanel(new CardLayout());
				
		rightLUp = new JPanel(gl1);
		rightLCenter = new JPanel();
		rightLDown = new JPanel(gl3); 
		rightL = new JPanel(new BorderLayout());
				
		rightL.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		rightLUp.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		rightLCenter.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		rightLDown.setBorder(BorderFactory.createEmptyBorder(50 , 10 , 50 , 10));
		
//		pages1.add("upcard1",rightLUp);
//		pages2.add("upcard1",rightLCenter);
		
		// 메뉴 윗버튼(종류 고르는 버튼들)
		// smal은 MenuButtonActionListener리스너자료형인데 매개변수로 MenuButtonActionListener내용을 받아서 전달해준다. 
		sortCoffee = new ButtonSetting ("Coffee",20, 0xb0e8f7, smal);
		rightLUp.add(sortCoffee);
		
		sortDrink = new ButtonSetting ("Drink",20, 0xb0e8f7, smal);
		rightLUp.add(sortDrink);
		
		sortBlended = new ButtonSetting ("blended",20, 0xb0e8f7, smal);
		rightLUp.add(sortBlended);
		
		sortMD = new ButtonSetting ("MD",20, 0xb0e8f7, smal);
		rightLUp.add(sortMD);
		
		
		// 맨 아래 버튼(영수증관리, 멤버쉽, 결제 버튼)
		ButtonSetting ManageReceipts = new ButtonSetting("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>",25 ,0xe2d4fc, null);
		rightLDown.add(ManageReceipts);
		
		ButtonSetting MemberShip = new ButtonSetting("<HTML><body style='text-align:center'>멤버쉽</body></HTML>",25,0xe3aada, als[1] );
		rightLDown.add(MemberShip);

		ButtonSetting Payment = new ButtonSetting("<HTML><body style='text-align:center'>결제</body></HTML>", 25, 0xb5f5c8, als[2]);
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
	
//	private void actionNextAdd(JButton a, JPanel p1) {
//
//		a.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
//				
//				card_layout1.next(p1);
//				
//			}
//		});
//
//	}// 다음 페이지 넘기게 하는 액션리스너추가
//
//	private void actionPrevAdd(JButton a, JPanel p1) {
//
//		a.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
//				
//				card_layout1.previous(p1);
//				
//			}
//		});
//
//	}// 이전 페이지 넘기게 하는 액션리스너추가
	
	public JPanel getRightLCenter() {
		return rightLCenter;
	}

	public JPanel getRightR() {
		return rightR;
	}

	public void setRightLCenter(JPanel rightLCenter) {
		this.rightLCenter = rightLCenter;
	}


	public ButtonSetting getSortCoffee() {
		return sortCoffee;
	}


	public ButtonSetting getSortDrink() {
		return sortDrink;
	}


	public ButtonSetting getSortBlended() {
		return sortBlended;
	}


	public ButtonSetting getSortMD() {
		return sortMD;
	}
	
	


	
}
