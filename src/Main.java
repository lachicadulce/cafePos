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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import baseSettings.PosFrame;
import handler.AbsentManagerHandler;
import handler.AllCancelActionListener;
import handler.NumpadBackSpaceActionListener;
import handler.NumpadHandler;
import handler.OrderTableListSelectionListener;
import handler.QuantityDecreaseActionListener;
import handler.QuantityIncreaseActionListener;
import handler.SafeOpenActionListener;
import handler.SelectCancelActionListener;

public class Main extends PosFrame {


	public Main() {
		super();


		setLayout(new BorderLayout());

		// 주문리스트 변수
		DefaultTableModel orderTableModel = new DefaultTableModel(); 
		JTable orderTable = new JTable(orderTableModel);

		// 주문리스트 버튼 변수
		JButton allCancel = new JButton("전체취소");
		JButton selectCancel = new JButton("선택취소");
		JButton quantityPlus = new JButton("수량 +");
		JButton quantityMinus = new JButton("수량 -");
		
		// 숫자패드 변수
		JPanel numpadPanel = new JPanel();
		JTextArea numpadText = new JTextArea("");
		JButton numpadCancel = new JButton(">");
		JButton numpadEnter = new JButton("E");
		
		// 관리자 메뉴, 근태관리, 환전 메뉴 변수
		JButton managerMenu = new JButton("<html>관리자<br />&nbsp메뉴</html>");
		JButton absentManager = new JButton("<html>근태<br />관리</html>");
		JButton exchange = new JButton("환전");
		
		// 금액계산 변수
		String[] calcColumn = { "", "" };
		String lumpSum = "";
		String discount = "";
		String received = "";
		String change = "";
		
		String[][] calcdata = { 
				{"총금액", lumpSum},
				{"할인금액", discount},
				{"받은금액", received},
				{"거스름돈", change},
		};
		JTable calcTable = new JTable(calcdata, calcColumn);

		
		// 주문LIST
		orderTableModel.addColumn("메뉴이름");
		orderTableModel.addColumn("수량");
		orderTableModel.addColumn("가격");
		
//		orderTableModel.addRow(new Object[] {"v1", "1", "4500"}); //행추가
//		orderTableModel.addRow(new Object[] {"v2", "1", "4500"}); //행추가
//		orderTableModel.addRow(new Object[] {"v3", "1", "4500"}); //행추가
//		orderTable.setValueAt("", 0, 0); //행수정
		
		ListSelectionModel orderTableSelection =  orderTable.getSelectionModel();
		orderTableSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		OrderTableListSelectionListener OTLSL =  new OrderTableListSelectionListener(orderTable);
		orderTableSelection.addListSelectionListener(OTLSL);
		
		orderTable.setDefaultEditor(Object.class, null); // 수정 불가
		JScrollPane orderScrollPanel = new JScrollPane(orderTable);
		orderScrollPanel.setBounds(10, 10, 500, 400);
		add(orderScrollPanel);
				
		// 금액계산
		calcTable.setBounds(10, 480, 250, 250);
		calcTable.setRowHeight(65);
		calcTable.setEnabled(false);	// 수정 불가, 클릭표시 안나옴	
		add(calcTable);

		// 숫자패드 입력창
		numpadText.setBounds(280, 480, 150, 50);
		numpadText.setEditable(false);
		numpadText.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		add(numpadText);
		
		// 전체취소,선택취소, 수량+, 수량-

		allCancel.setLocation(50, 430);
		allCancel.setSize(100, 30);
		allCancel.addActionListener(new AllCancelActionListener(orderTable));
		add(allCancel);

		selectCancel.setLocation(150, 430);
		selectCancel.setSize(100, 30);
		selectCancel.addActionListener(new SelectCancelActionListener(orderTableModel, orderTable));
		add(selectCancel);

		quantityPlus.setLocation(250, 430);
		quantityPlus.setSize(100, 30);
		quantityPlus.addActionListener(new QuantityIncreaseActionListener(orderTable));
		add(quantityPlus);

		quantityMinus.setLocation(350, 430);
		quantityMinus.setSize(100, 30);
		quantityMinus.addActionListener(new QuantityDecreaseActionListener(orderTableModel, orderTable));
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
