import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import baseSettings.PosFrame;
import handler.AbsentManagerHandler;
import handler.CashActionHandler;
import handler.ChangeActionListener;
import handler.ManagerButtonActionListener;
import handler.MemberShipActionListener;
import handler.MenuButtonActionListener;
import handler.QuantityDecreaseActionListener;
import handler.QuantityIncreaseActionListener;
import handler.RevalidateActionListener;
import handler.SafeOpenActionListener;
import handler.SelectCancelActionListener;
import handler.SortMenuActionListener;
import main_component.BasicButton;
import main_component.ButtonSetting;
import main_component.MenuPanel;
import main_component.OrderButton;
import main_component.RightPanelBasic;





public class main_test extends PosFrame {

	private JSplitPane jsp1 = new JSplitPane();
	private JSplitPane jsp2 = new JSplitPane();

	RightPanelBasic rpb;
	JTable calcTable;
	CashActionHandler cah;
	MemberShipActionListener msal;
	String[] calcColumn = { "", "" };
	String lumpSum;
	String discount;
	String received;
	String change;
	
	DefaultTableModel orderTableModel;
	JTable orderTable;
	ListSelectionModel orderTableSelection;
	JScrollPane orderScrollPanel;
	
	ManagerButtonActionListener managerAl;

	String[][] calcdata = { 
			{"총금액", lumpSum},
			{"할인금액", discount},
			{"받은금액", received},
			{"거스름돈", change},
	};
	
	
	JButton allCancel;
	JButton selectCancel;
	JButton quantityPlus;
	JButton quantityMinus;
	
	JButton managerMenu;
	JButton absentManager;
	JButton exchange;
	
	JButton payment;
	MenuButtonActionListener mbal;
	
	public main_test() {
		super();
		mainScreenInit();

	}

	private void mainScreenInit() {

		
		
		jsp1.setResizeWeight(0.9);
		jsp2.setResizeWeight(0.8);
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		// 왼쪽 화면
		JPanel leftScreen = new JPanel();
		leftScreen.setLayout(null);

		
		// 금액계산 변수
		
		lumpSum = "";
		discount = "";
		received = "";
		change = "";

		calcTable = new JTable(calcdata, calcColumn);
		
	
		// 주문LIST
		orderTableModel = new DefaultTableModel(); 
		orderTable = new JTable(orderTableModel);

		orderTableModel.addColumn("메뉴이름");
		orderTableModel.addColumn("수량");
		orderTableModel.addColumn("가격");
		

		//			orderTableModel.addRow(new Object[] {"v1", "1", "4500"}); //행추가
		//			orderTableModel.addRow(new Object[] {"v2", "1", "4500"}); //행추가
		//			orderTableModel.addRow(new Object[] {"v3", "1", "4500"}); //행추가
		//			orderTable.setValueAt("", 0, 0); //행수정

		orderTableSelection =  orderTable.getSelectionModel();
		orderTableSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		orderTable.setDefaultEditor(Object.class, null); // 수정 불가
		orderScrollPanel = new JScrollPane(orderTable);
		orderScrollPanel.setBounds(5, 10, 500, 400);
		add(orderScrollPanel);

		// 액션리스너 선언
		msal = new MemberShipActionListener(calcTable);
		cah = new CashActionHandler(calcTable, msal, orderTableModel, this.getFrames()[0]);
		managerAl = new ManagerButtonActionListener(this.getFrames()[0]);
		
		
		// 주문리스트 버튼 변수
		allCancel = new JButton("전체취소");
		selectCancel = new JButton("선택취소");
		quantityPlus = new JButton("수량 +");
		quantityMinus = new JButton("수량 -");
		
		
		// 전체취소버튼
		allCancel.setLocation(50, 430);
		allCancel.setSize(100, 30);
		allCancel.addActionListener(new RevalidateActionListener(this.getFrames()[0], calcTable, orderTableModel));
		add(allCancel);

		selectCancel.setLocation(150, 430);
		selectCancel.setSize(100, 30);
		selectCancel.addActionListener(new SelectCancelActionListener(orderTableModel, orderTable));
		add(selectCancel);

		quantityPlus.setLocation(250, 430);
		quantityPlus.setSize(100, 30);
		quantityPlus.addActionListener(new QuantityIncreaseActionListener(calcTable, orderTableModel, orderTable));
		add(quantityPlus);

		quantityMinus.setLocation(350, 430);
		quantityMinus.setSize (100, 30);
		quantityMinus.addActionListener(new QuantityDecreaseActionListener(calcTable, orderTableModel, orderTable));
		add(quantityMinus);


		
		// 금액계산
		calcTable.setBounds(20, 480, 300, 250);
		calcTable.setRowHeight(65);
		calcTable.setEnabled(false);	// 수정 불가, 클릭표시 안나옴	
		add(calcTable);

		// 관리자 메뉴, 근태관리, 환전 메뉴 변수
		managerMenu = new JButton("<html>관리자<br />&nbsp메뉴</html>");
		absentManager = new JButton("<html>근태<br />관리</html>");
		exchange = new JButton("환전");

		// 관리자 메뉴
		managerMenu.setLocation(420, 470);
		managerMenu.setSize(80,70);
		managerMenu.addActionListener(managerAl);
		add(managerMenu);
		// 근태 
		absentManager.setLocation(420, 540);
		absentManager.setSize(80,70);
		absentManager.addActionListener(new AbsentManagerHandler());
		add(absentManager);

		// 환전
		exchange.setLocation(420, 610);
		exchange.setSize(80,70);
		exchange.addActionListener(new SafeOpenActionListener());
		add(exchange);
		
		// 환전계산
		payment = new JButton("<html>환전<br />계산</html>");
		payment.setLocation(420, 680);
		payment.setSize(80,70);
		payment.addActionListener(new ChangeActionListener(calcTable, cah, msal, orderTableModel));
		add(payment);
		
		
		
//		JButton test2 = new JButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>");
//		test2.setLocation(220, 610);
//		test2.setSize(75,70);
//		test2.addActionListener(new MenuButtonActionListener(calcTable, orderTableModel, orderTable));
//		add(test2);


/////////////////////////////////오르쪽 화면 ////////////////////////////////////////
		
		mbal = new MenuButtonActionListener(calcTable, orderTableModel, orderTable);
		
		
		rpb = new RightPanelBasic(jsp2, mbal, msal, cah, managerAl);
		
		MenuPanel m = new MenuPanel("Coffee", rpb, mbal);
		
		//		MenuPanel drink = new MenuPanel("drink", rpb, mbal);
		
		jsp1.setLeftComponent(leftScreen);
		jsp1.setRightComponent(rpb);
		con.add("Center", jsp1);

	}//mainScreenInit함수 끝.

	public static void main(String[] args) {

		main_test m = new main_test();
		m.setDefaultOptions();
	}




}
