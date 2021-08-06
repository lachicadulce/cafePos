import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer.Form;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import baseSettings.PosFrame;
import handler.AbsentManagerHandler;
import handler.NumpadBackSpaceActionListener;
import handler.NumpadHandler;
import handler.SafeOpenActionListener;

public class Main extends PosFrame {


	/*
	 	紐⑤뱺 �솕硫대뱾�쓣 �쓣�썙以� 湲곕낯 �봽�옒�엫.
	 */
	public Main() {
		super();


		setLayout(new BorderLayout());

		JTextArea orderList = new JTextArea("주문LIST");
		JTextArea calc = new JTextArea("");
		JPanel numpadPanel = new JPanel();

		JButton allCancel = new JButton("전체취소");
		JButton selectCancel = new JButton("선택취소");
		JButton quantityPlus = new JButton("수량 +");
		JButton quantityMinus = new JButton("수량 -");

		JTextArea numpadText = new JTextArea("");
		JButton numpadCancel = new JButton(">");
		JButton numpadEnter = new JButton("E");
		
		JButton managerMenu = new JButton("<html>관리자<br />&nbsp메뉴</html>");
		JButton absentManager = new JButton("<html>근태<br />관리</html>");
		JButton exchange = new JButton("환전");
		
		String[] calcColumn = { "", "" };
		JTextArea lumpSum = new JTextArea();
		JTextArea discount = new JTextArea();
		JTextArea received = new JTextArea();
		JTextArea change = new JTextArea();
		
		JTextArea[][] calcdata = { 
				{new JTextArea("총금액"), lumpSum},
				{new JTextArea("할인금액"), discount},
				{new JTextArea("받은금액"), received},
				{new JTextArea("거스름돈"), change},
		};
		
		JTable calcTable = new JTable(calcdata, calcColumn);

		// 주문LIST
		orderList.setBounds(10, 10, 500, 400);
		orderList.setEditable(false);
		
		add(orderList);
		
		// 금액계산
		calcTable.setBounds(10, 480, 250, 250);
		calcTable.setRowHeight(65);
		add(calcTable);
//		calc.setBounds(10, 480, 250, 250);
//		calc.setEditable(false);
//		calc.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//		add(calc);

		numpadText.setBounds(280, 480, 150, 50);
		numpadText.setEditable(false);
		numpadText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(numpadText);
		
		// 전체취소,선택취소, 수량+, 수량-

		allCancel.setLocation(50, 430);
		allCancel.setSize(100, 30);
		add(allCancel);

		selectCancel.setLocation(150, 430);
		selectCancel.setSize(100, 30);
		add(selectCancel);

		quantityPlus.setLocation(250, 430);
		quantityPlus.setSize(100, 30);
		add(quantityPlus);

		quantityMinus.setLocation(350, 430);
		quantityMinus.setSize(100, 30);
		add(quantityMinus);


		// 숫자패드 backspace
		numpadCancel.setSize(50, 50);
		numpadCancel.setLocation(330, 700);
		numpadCancel.addActionListener(new NumpadBackSpaceActionListener(numpadText));
		add(numpadCancel);
		
		
		
		// 숫자패드 enter
		numpadEnter.setSize(50, 50);
		numpadEnter.setLocation(380, 700);
		add(numpadEnter);
		// 관리자 메뉴
		managerMenu.setLocation(440, 480);
		managerMenu.setSize(75,70);
		add(managerMenu);
		// 근태 관리
		absentManager.setLocation(440, 570);
		absentManager.setSize(75,70);
		
		absentManager.addActionListener(new AbsentManagerHandler());
		add(absentManager);
		
		// 환전
		exchange.setLocation(440, 660);
		exchange.setSize(75,70);
				
		exchange.addActionListener(new SafeOpenActionListener());
			
		
		add(exchange);
		
		numpadPanel.setLayout(null);
		for(int i = 0; i < 10; i++) {
			JButton numpad = new JButton();
			if (i == 0 ) {
				numpad.setText("0");
				numpad.setSize(50,50);
				numpad.setLocation(280, 700);
			} else {
				numpad.setText(""+i);
				numpad.setSize(50,50);
				numpad.setLocation(280 + ((i-1) % 3 * 50), 650 - ((i -1 )/3 *50) );
			}
			numpad.addActionListener(new NumpadHandler(numpadText));
			numpadPanel.add(numpad);
		}
		add(numpadPanel);
		
		


	}

	public static void main(String[] args) {
		Main frame = new Main();
		frame.setDefaultOptions();
	}
}
