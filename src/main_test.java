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
		
		//
		CashActionHandler cah = new CashActionHandler(calcTable);
		MemberShipActionListener msal = new MemberShipActionListener(calcTable);
		
		
		// 주문LIST
		DefaultTableModel orderTableModel = new DefaultTableModel(); 
		JTable orderTable = new JTable(orderTableModel);

		orderTableModel.addColumn("메뉴이름");
		orderTableModel.addColumn("수량");
		orderTableModel.addColumn("가격");

		//			orderTableModel.addRow(new Object[] {"v1", "1", "4500"}); //행추가
		//			orderTableModel.addRow(new Object[] {"v2", "1", "4500"}); //행추가
		//			orderTableModel.addRow(new Object[] {"v3", "1", "4500"}); //행추가
		//			orderTable.setValueAt("", 0, 0); //행수정

		ListSelectionModel orderTableSelection =  orderTable.getSelectionModel();
		orderTableSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		orderTable.setDefaultEditor(Object.class, null); // 수정 불가
		JScrollPane orderScrollPanel = new JScrollPane(orderTable);
		orderScrollPanel.setBounds(5, 10, 350, 400);
		add(orderScrollPanel);

		// 주문리스트 버튼 변수
		JButton allCancel = new JButton("전체취소");
		JButton selectCancel = new JButton("선택취소");
		JButton quantityPlus = new JButton("수량 +");
		JButton quantityMinus = new JButton("수량 -");

		allCancel.setLocation(10, 430);
		allCancel.setSize(85, 30);
		allCancel.addActionListener(new RevalidateActionListener(this.getFrames()[0], calcTable, orderTableModel));
		add(allCancel);

		selectCancel.setLocation(95, 430);
		selectCancel.setSize(85, 30);
		selectCancel.addActionListener(new SelectCancelActionListener(orderTableModel, orderTable));
		add(selectCancel);

		quantityPlus.setLocation(180, 430);
		quantityPlus.setSize(85, 30);
		quantityPlus.addActionListener(new QuantityIncreaseActionListener(calcTable, orderTableModel, orderTable));
		add(quantityPlus);

		quantityMinus.setLocation(265, 430);
		quantityMinus.setSize(85, 30);
		quantityMinus.addActionListener(new QuantityDecreaseActionListener(calcTable, orderTableModel, orderTable));
		add(quantityMinus);

		// 금액계산
		calcTable.setBounds(5, 480, 250, 250);
		calcTable.setRowHeight(65);
		calcTable.setEnabled(false);	// 수정 불가, 클릭표시 안나옴	
		add(calcTable);

		// 관리자 메뉴, 근태관리, 환전 메뉴 변수
		JButton managerMenu = new JButton("<html>관리자<br />&nbsp메뉴</html>");
		JButton absentManager = new JButton("<html>근태<br />관리</html>");
		JButton exchange = new JButton("환전");

		// 관리자 메뉴
		managerMenu.setLocation(280, 470);
		managerMenu.setSize(75,70);
		add(managerMenu);
		// 근태 관리
		absentManager.setLocation(280, 540);
		absentManager.setSize(75,70);
		absentManager.addActionListener(new AbsentManagerHandler());
		add(absentManager);

		// 환전
		exchange.setLocation(280, 610);
		exchange.setSize(75,70);
		exchange.addActionListener(new SafeOpenActionListener());
		add(exchange);
		
		// 환전계산
		JButton payment = new JButton("<html>환전<br />계산</html>");
		payment.setLocation(280, 680);
		payment.setSize(75,70);
		payment.addActionListener(new ChangeActionListener(calcTable, cah, msal));
		add(payment);
		
		
		
//		JButton test2 = new JButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>");
//		test2.setLocation(220, 610);
//		test2.setSize(75,70);
//		test2.addActionListener(new MenuButtonActionListener(calcTable, orderTableModel, orderTable));
//		add(test2);


/////////////////////////////////오르쪽 화면 ////////////////////////////////////////
		
		MenuButtonActionListener mbal = new MenuButtonActionListener(calcTable, orderTableModel, orderTable);
		
		
		
		
		rpb = new RightPanelBasic(jsp2, mbal, msal, cah);
		
		
		
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
