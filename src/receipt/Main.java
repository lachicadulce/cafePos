package receipt;

import java.util.ArrayList;

import javax.swing.JButton;

import baseSettings.PosFrame;

public class Main extends PosFrame {
	public Main() {
		super();
		super.setTitle("영수증 관리");
		
//		JButton[] btns = new JButton[5];
//		String[] directions = {"East", "West", "South", "North", "Center"};
//		
//		for (int i = 0, len = btns.length; i < len; ++i) {
//			btns[i] = new JButton("Button" + i);
//			add(btns[i], directions[i]);
//		}
//		// 1. 버튼의 배경색을 바꿀 수도 있다
//		btns[2].setBackground(new Color(0x42bdff));
//		
//		// 2. 버튼에는 글꼴도 추가할 수 있다
//		btns[1].setFont(new Font("MV Boli", Font.BOLD, 50));
		
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.add(new JButton("전표반품"));
		buttons.add(new JButton("재인쇄"));
		buttons.add(new JButton("현금영수증"));
		buttons.add(new JButton("결제변경"));
		buttons.add(new JButton("전체"));
		buttons.add(new JButton("현금"));
		buttons.add(new JButton("신용카드"));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.setDefaultOptions();
	}
}
