package receipt;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;

import baseSettings.PosFrame;

public class Receipt extends PosFrame {
	public Receipt() {
		super();
		super.setTitle("영수증 관리");
		
		setLayout(null);
		
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.add(new JButton("전표반품"));
		buttons.add(new JButton("재인쇄"));
		buttons.add(new JButton("현금영수증"));
		buttons.add(new JButton("결제변경"));
		buttons.add(new JButton("전체"));
		buttons.add(new JButton("현금"));
		buttons.add(new JButton("신용카드"));
//		frame.add(new JButton("Button 3") {
//			{
//				setLocation(30, 30);
//				setSize(150, 80);
//			}
//		});
		buttons.get(0).setBounds(20, 30, 150, 80);
		buttons.get(1).setBounds(190, 30, 150, 80);
		buttons.get(2).setBounds(360, 30, 150, 80);
		buttons.get(3).setBounds(530, 30, 150, 80);
		
		buttons.get(4).setBounds(870, 30, 120, 80);
		buttons.get(5).setBounds(1010, 30, 120, 80);
		buttons.get(6).setBounds(1150, 30, 120, 80);
		
		add(buttons.get(0));
		add(buttons.get(1));
		add(buttons.get(2));
		add(buttons.get(3));
		
		add(buttons.get(4));
		add(buttons.get(5));
		add(buttons.get(6));
		
		
		
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
//		
//		// 3. 버튼에는 이벤트도 추가할 수 있다.
//		btns[0].addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int random = (int)(Math.random() * 10 + 2);
//				
//				for (int i = 1; i < random; ++i) {
//					System.err.print("왜 눌러?");
//				}
//				System.out.println();
//			}
//		});
//		
//		// 4. 버튼 비활성화
//		btns[4].setEnabled(false);
		
		
		
		

	}
	
	public static void main(String[] args) {
		 Receipt frame = new Receipt();	
		 frame.setDefaultOptions();
	}
}
