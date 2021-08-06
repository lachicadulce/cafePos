import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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

public class MainMerge extends PosFrame {


	public MainMerge() {
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

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
//		ArrayList<String> columnNames =  
		
//		String[] columnNames = {"ID","NAME","JOB","DEPT"};
//		String[][] data = {
//				{"1","Smith","IT_PROG","IT"},
//				{"2","Allen","IT_PROG","IT"},
//				{"3","Ki","IT_PROG","IT"},
//				{"4","Zoey","IT_PROG","IT"},
//				{"5","Warren","IT_PROG","IT"},
//		};
//		
//	
//		
//		JTextArea record = new JTextArea("�⺻��"); 
//		JTable record = new JTable(data, columnNames);
//		record.setBounds(10, 10, 500, 400);
//		
//		
//		JScrollPane scrollPane = new JScrollPane(record);
//		record.getTableHeader().setPreferredSize(new Dimension(
//				scrollPane.getWidth(),50));
//		record.getTableHeader().setFont(new Font("Small Fonts",Font.BOLD,22));
//		record.setRowHeight(25);
//		record.setAlignmentY(JTable.TOP_ALIGNMENT);
//		record.setCellSelectionEnabled(true);
//		add(record);
		
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(null);
		JButton cof = new JButton("커피");
		
		cof.setBounds(540, 10, 160, 100);
		cof.setFont(new Font("System",Font.BOLD,20));
		cof.setBackground(new Color(0xb0e8f7));
		tempPanel.add(cof);
		add(tempPanel);
		
		JButton bev = new JButton("음료");
		bev.setBounds(715, 10, 160, 100);
		bev.setFont(new Font("System",Font.BOLD,20));
		add(bev);
		bev.setBackground(new Color(0xb0e8f7));
		
		JButton blendid = new JButton("블랜디드");
		blendid.setBounds(890, 10, 160, 100);
		blendid.setBackground(new Color(0xb0e8f7));
		blendid.setFont(new Font("System",Font.BOLD,20));
		add(blendid);
		
		JButton md = new JButton("MD");
		md.setBounds(1065, 10, 160, 100);
		md.setBackground(new Color(0xb0e8f7));
		md.setFont(new Font("System",Font.BOLD,20));
		add(md);
		
		///////////////////////////////////////
		JButton americano = new JButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>");
		americano.setBounds(540, 125, 160, 100);
		americano.setBackground(new Color(0xfafeff));
		americano.setFont(new Font("System",Font.BOLD,17));
		add(americano);
		
		JButton banila = new JButton("<HTML><body style='text-align:center'>바닐라라떼(R)<br>4500</body></HTML>");
		banila.setBounds(715, 125, 160, 100);
		banila.setBackground(new Color(0xfafeff));
		banila.setFont(new Font("System",Font.BOLD,17));
		add(banila);
		
		JButton cafelatte = new JButton("<HTML><body style='text-align:center'>카페라떼(R)<br>4000</body></HTML>");
		cafelatte.setBounds(890, 125, 160, 100);
		cafelatte.setBackground(new Color(0xfafeff));
		cafelatte.setFont(new Font("System",Font.BOLD,17));
		add(cafelatte);
		
		JButton moca = new JButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>");
		moca.setBounds(1065, 125, 160, 100);
		moca.setBackground(new Color(0xfafeff));
		moca.setFont(new Font("System",Font.BOLD,17));
		add(moca);
		
		JButton up = new JButton("<HTML><body style='text-align:left'>△</body></HTML>");
		up.setBounds(1240, 125, 35, 100);
		up.setBackground(new Color(0xb0e8f7));
		up.setFont(new Font("System",Font.BOLD,10));
		add(up);
		
		///////////////////////////////////////
		JButton iceame = new JButton("<HTML><body style='text-align:center'>아이스<br>아메리카노(R)<br>3500</body></HTML>");
		iceame.setBounds(540, 240, 160, 100);
		iceame.setBackground(new Color(0xfafeff));
		iceame.setFont(new Font("System",Font.BOLD,17));
		add(iceame);
		
		JButton icebana = new JButton("<HTML><body style='text-align:center'>아이스<br>바닐라라떼(R)<br>4500</body></HTML>");
		icebana.setBounds(715, 240, 160, 100);
		icebana.setBackground(new Color(0xfafeff));
		icebana.setFont(new Font("System",Font.BOLD,17));
		add(icebana);
		
		JButton cafelatteL = new JButton("<HTML><body style='text-align:center'>카페라떼(L)<br>4500</body></HTML>");
		cafelatteL.setBounds(890, 240, 160, 100);
		cafelatteL.setBackground(new Color(0xfafeff));
		cafelatteL.setFont(new Font("System",Font.BOLD,17));
		add(cafelatteL);
		
		JButton mocaL = new JButton("<HTML><body style='text-align:center'>카페모카(L)<br>4300</body></HTML>");
		mocaL.setBounds(1065, 240, 160, 100);
		mocaL.setBackground(new Color(0xfafeff));
		mocaL.setFont(new Font("System",Font.BOLD,17));
		add(mocaL);
		
		JButton down = new JButton("<HTML><body style='text-align:left'>▽</body></HTML>");
		down.setBounds(1240, 240, 35, 100);
		down.setBackground(new Color(0xb0e8f7));
		down.setFont(new Font("System",Font.BOLD,10));
		add(down);
		
		///////////////////////////////////////
		JButton coldblue = new JButton("<HTML><body style='text-align:center'>콜드브루<br>4000</body></HTML>");
		coldblue.setBounds(540, 355, 160, 100);
		coldblue.setBackground(new Color(0xfafeff));
		coldblue.setFont(new Font("System",Font.BOLD,17));
		add(coldblue);
		
		JButton coldbluelt = new JButton("<HTML><body style='text-align:center'>콜드브루라떼(R)<br>4500</body></HTML>");
		coldbluelt.setBounds(715, 355, 160, 100);
		coldbluelt.setBackground(new Color(0xfafeff));
		coldbluelt.setFont(new Font("System",Font.BOLD,17));
		add(coldbluelt);
		
		JButton syrup = new JButton("<HTML><body style='text-align:center'>시럽추가<br>500</body></HTML>");
		syrup.setBounds(890, 355, 160, 100);
		syrup.setBackground(new Color(0xfafeff));
		syrup.setFont(new Font("System",Font.BOLD,17));
		add(syrup);
		
		JButton cup = new JButton("<HTML><body style='text-align:center'>개인컵<br>-300</body></HTML>");
		cup.setBounds(1065, 355, 160, 100);
		cup.setBackground(new Color(0xfafeff));
		cup.setFont(new Font("System",Font.BOLD,17));
		add(cup);
		
		///////////////////////////////////////
		JButton receipt = new JButton("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>");
		receipt.setBounds(540, 535, 160, 160);
		receipt.setBackground(new Color(0xe2d4fc));
		receipt.setFont(new Font("System",Font.BOLD,25));
		add(receipt);
		
		JButton membership = new JButton("<HTML><body style='text-align:center'>맴버쉽</body></HTML>");
		membership.setBounds(715, 535, 160, 160);
		membership.setBackground(new Color(0xe3aada));
		membership.setFont(new Font("System",Font.BOLD,25));
		add(membership);
		
		JButton card = new JButton("<HTML><body style='text-align:center'>신용카드</body></HTML>");
		card.setBounds(890, 535, 160, 160);
		card.setBackground(new Color(0xb5f5c8));
		card.setFont(new Font("System",Font.BOLD,25));
		add(card);
		
		JButton bill = new JButton("<HTML><body style='text-align:center'>현금</body></HTML>");
		bill.setBounds(1065, 535, 160, 160);
		bill.setBackground(new Color(0xfff7e0));
		bill.setFont(new Font("System",Font.BOLD,25));
		add(bill);	

		
	}
	
	


	public static void main(String[] args) {
		Main frame = new Main();
		frame.setDefaultOptions();
	}
}
