package main_component;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import baseSettings.PosFrame;
import handler.AbsentManagerHandler;
import handler.CashActionHandler;
import handler.ManagerButtonActionListener;
import handler.MemberShipActionListener;
import handler.MenuButtonActionListener;
import handler.QuantityDecreaseActionListener;
import handler.QuantityIncreaseActionListener;
import handler.ReceiptActionListener;
import handler.RevalidateActionListener;
import handler.SafeOpenActionListener;
import handler.SelectCancelActionListener;

public class MainPos extends PosFrame {

	private JSplitPane jsp1 = new JSplitPane();
	private JSplitPane jsp2 = new JSplitPane();

	RightPanelBasic rpb;
	JTable calcTable;
	CashActionHandler cah;
	MemberShipActionListener msal;
	ReceiptActionListener rcal;
	String[] calcColumn = { "", "" };

	String lumpSum = "0";
	String discount = "0";
	String received = "0";
	String change = "0";
	
	DefaultTableModel orderTableModel;
	JTable orderTable;
	ListSelectionModel orderTableSelection;
	JScrollPane orderScrollPanel;
	
	ManagerButtonActionListener managerAl;

	String[][] calcdata = { 
			{" 총 금액", lumpSum},
			{" 포인트 사용", discount},
			{" 받은 금액", received},
			{" 거스름돈", change},
	};
	
	
	JButton allCancel;
	JButton selectCancel;
	JButton quantityPlus;
	JButton quantityMinus;
	
	JButton managerMenu;
	JButton absentManager;
	JButton exchange;
	
	MenuButtonActionListener mbal;
	
	public MainPos() {
		super();
		mainScreenInit();
		setTitle("카페 메인화면");
	}

	private void mainScreenInit() {
	
		jsp1.setResizeWeight(0.8);
		jsp2.setResizeWeight(1.0);
		jsp1.setEnabled(false);
		jsp2.setEnabled(false);
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		// 왼쪽 화면
		JPanel leftScreen = new JPanel();
		leftScreen.setLayout(null);

		calcTable = new JTable(calcdata, calcColumn) { // 테이블 선택은 가능하지만 편집은 불가능하게 익명클래스 사용
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		calcTable.setFont(new Font("default", Font.BOLD, 20));
		
	
		// 주문LIST
		orderTableModel = new DefaultTableModel(); 
		orderTable = new JTable(orderTableModel);
		orderTable.getTableHeader().setReorderingAllowed(false); // 헤더가 움직이지 않도록 설정추가
		orderTable.getTableHeader().setResizingAllowed(false); // 셀 사이즈 조정 불가 설정

		orderTableModel.addColumn("메뉴이름");
		orderTableModel.addColumn("수량");
		orderTableModel.addColumn("가격");
		
		TableColumnModel colModel = orderTable.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(120);
		colModel.getColumn(1).setPreferredWidth(30);
		colModel.getColumn(2).setPreferredWidth(30);


		orderTableSelection =  orderTable.getSelectionModel();
		orderTableSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		orderTable.setDefaultEditor(Object.class, null); // 수정 불가
		orderScrollPanel = new JScrollPane(orderTable);
		orderScrollPanel.setBounds(10, 2, 500, 400);
		add(orderScrollPanel);

		// 액션리스너 선언
		msal = new MemberShipActionListener(calcTable);
		//int usedPoint = msal.getUsePoint();
		int usedPoint = Integer.parseInt((String)calcTable.getValueAt(1, 1));

		int usedCusNum = msal.getCus_no();

		String tel = msal.getMemTel();
		cah = new CashActionHandler(calcTable, msal, usedPoint, usedCusNum, tel, orderTableModel, this.getFrames()[0]);
		managerAl = new ManagerButtonActionListener(this);
		rcal = new ReceiptActionListener(this);
		
		
		// 주문리스트 버튼 변수
		allCancel = new JButton("전체취소");
		selectCancel = new JButton("선택취소");
		quantityPlus = new JButton(" 수량");
		quantityMinus = new JButton(" 수량");
		
		
		// 전체취소버튼
		allCancel.setLocation(10, 410);
		allCancel.setSize(125, 50);
		allCancel.addActionListener(new RevalidateActionListener(this.getFrames()[0], calcTable, orderTableModel));
		allCancel.setFont(new Font("default", Font.BOLD, 14));
		allCancel.setBackground(new Color(0xD8D8D8));
		ImageIcon removeall = new ImageIcon("mainpng/removeall.png");
		allCancel.setIcon(removeall);
		add(allCancel);
		
		// 선택 취소 버튼
		selectCancel.setLocation(135, 410);
		selectCancel.setSize(125, 50);
		selectCancel.addActionListener(new SelectCancelActionListener(orderTableModel, orderTable));
		selectCancel.setFont(new Font("default", Font.BOLD, 14));
		selectCancel.setBackground(new Color(0xF2F2F2));
		ImageIcon remove = new ImageIcon("mainpng/remove.png");
		selectCancel.setIcon(remove);
		add(selectCancel);

		// 수량 추가
		quantityPlus.setLocation(260, 410);
		quantityPlus.setSize(125, 50);
		quantityPlus.addActionListener(new QuantityIncreaseActionListener(calcTable, orderTableModel, orderTable));
		quantityPlus.setFont(new Font("default", Font.BOLD, 14));
		quantityPlus.setBackground(new Color(0xD8D8D8));
		ImageIcon add = new ImageIcon("mainpng/add.png");
		quantityPlus.setIcon(add);
		add(quantityPlus);

		// 수량 차감
		quantityMinus.setLocation(385, 410);
		quantityMinus.setSize(125, 50);
		quantityMinus.addActionListener(new QuantityDecreaseActionListener(calcTable, orderTableModel, orderTable));
		quantityMinus.setFont(new Font("default", Font.BOLD, 14));
		quantityMinus.setBackground(new Color(0xF2F2F2));
		ImageIcon sub = new ImageIcon("mainpng/sub.png");
		quantityMinus.setIcon(sub);
		add(quantityMinus);


		// 금액계산
		calcTable.setBounds(15, 480, 360, 260);
		calcTable.setRowHeight(65);
		//calcTable.setEnabled(false);// 수정 불가, 클릭표시 안나옴	
		add(calcTable);

		// 관리자 메뉴, 근태관리, 환전 메뉴 변수
		managerMenu = new JButton("관리자 메뉴");
		absentManager = new JButton(" 출퇴근");
		exchange = new JButton(" 환전");

		// 관리자 메뉴
		managerMenu.setLocation(386, 480);
		managerMenu.setSize(125,130);
		managerMenu.addActionListener(managerAl);
		managerMenu.setFont(new Font("default", Font.BOLD, 14));
		managerMenu.setBackground(new Color(0x81BEF7));
		add(managerMenu);
		
		// 근태 
		absentManager.setLocation(386, 610);
		absentManager.setSize(125,65);
		absentManager.addActionListener(new AbsentManagerHandler());
		absentManager.setFont(new Font("default", Font.BOLD, 14));
		absentManager.setBackground(new Color(0xA9D0F5));
		absentManager.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon clock = new ImageIcon("mainpng/clock.png");
		absentManager.setIcon(clock);
		add(absentManager);

		// 환전
		exchange.setLocation(386, 675);
		exchange.setSize(125,65);
		exchange.addActionListener(new SafeOpenActionListener(calcTable, orderTableModel));
		exchange.setFont(new Font("default", Font.BOLD, 14));
		exchange.setBackground(new Color(0xE0ECF8));
		exchange.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon money = new ImageIcon("mainpng/money.png");
		exchange.setIcon(money);
		add(exchange);
		

/////////////////////////////////이하 우측 화면 구성////////////////////////////////////////
		

		mbal = new MenuButtonActionListener(calcTable, orderTableModel, orderTable);
			
		rpb = new RightPanelBasic(jsp2, mbal, msal, cah, managerAl, rcal);
		
		MenuPanel m = new MenuPanel("Coffee", rpb, mbal);
		
		jsp1.setLeftComponent(leftScreen);
		jsp1.setRightComponent(rpb);
		con.add("Center", jsp1);

	}//mainScreenInit함수 끝.

}
