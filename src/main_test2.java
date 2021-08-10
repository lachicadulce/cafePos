
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import handler.AllCancelActionListener;
import handler.CashActionHandler;
import handler.MemberShipActionListener;
import handler.OrderTableListSelectionListener;
import handler.QuantityDecreaseActionListener;
import handler.QuantityIncreaseActionListener;
import handler.SafeOpenActionListener;
import handler.SelectCancelActionListener;
import main_component.MainBtns;
import main_component.OrderButton;

public class main_test2 extends PosFrame {

	private JSplitPane jsp1 = new JSplitPane();
	private JSplitPane jsp2 = new JSplitPane();
	
	   public main_test2() {
	      super();
	      mainScreenInit();
	      
	      
	   }
	   
	   
	   private void mainScreenInit() {
  
		  jsp1.setResizeWeight(0.9);
		  jsp2.setResizeWeight(0.8);
		  
		  Container con = this.getContentPane();
		  con.setLayout(new BorderLayout());
		  
	      MainBtns btns = new MainBtns();
	      
	   // 왼쪽 화면
	      JPanel leftScreen = new JPanel();
			leftScreen.setLayout(null);

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
			OrderTableListSelectionListener OTLSL =  new OrderTableListSelectionListener(orderTable);
			orderTableSelection.addListSelectionListener(OTLSL);

			orderTable.setDefaultEditor(Object.class, null); // 수정 불가
			JScrollPane orderScrollPanel = new JScrollPane(orderTable);
			orderScrollPanel.setBounds(10, 10, 400, 400);
			add(orderScrollPanel);

			// 주문리스트 버튼 변수
			JButton allCancel = new JButton("전체취소");
			JButton selectCancel = new JButton("선택취소");
			JButton quantityPlus = new JButton("수량 +");
			JButton quantityMinus = new JButton("수량 -");

			allCancel.setLocation(10, 430);
			allCancel.setSize(100, 30);
			allCancel.addActionListener(new AllCancelActionListener(orderTable));
			add(allCancel);

			selectCancel.setLocation(110, 430);
			selectCancel.setSize(100, 30);
			selectCancel.addActionListener(new SelectCancelActionListener(orderTableModel, orderTable));
			add(selectCancel);

			quantityPlus.setLocation(210, 430);
			quantityPlus.setSize(100, 30);
			quantityPlus.addActionListener(new QuantityIncreaseActionListener(orderTable));
			add(quantityPlus);

			quantityMinus.setLocation(310, 430);
			quantityMinus.setSize(100, 30);
			quantityMinus.addActionListener(new QuantityDecreaseActionListener(orderTableModel, orderTable));
			add(quantityMinus);

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

			// 금액계산
			calcTable.setBounds(10, 480, 250, 250);
			calcTable.setRowHeight(65);
			calcTable.setEnabled(false);	// 수정 불가, 클릭표시 안나옴	
			add(calcTable);
			

			// 관리자 메뉴, 근태관리, 환전 메뉴 변수
			JButton managerMenu = new JButton("<html>관리자<br />&nbsp메뉴</html>");
			JButton absentManager = new JButton("<html>근태<br />관리</html>");
			JButton exchange = new JButton("환전");

			// 관리자 메뉴
			managerMenu.setLocation(300, 480);
			managerMenu.setSize(75,70);
			add(managerMenu);
			// 근태 관리
			absentManager.setLocation(300, 570);
			absentManager.setSize(75,70);
			absentManager.addActionListener(new AbsentManagerHandler());
			add(absentManager);

			// 환전
			exchange.setLocation(300, 660);
			exchange.setSize(75,70);
			exchange.addActionListener(new SafeOpenActionListener());
			add(exchange);
			
			JButton temptest = new JButton("test");
			temptest.setLocation(250, 660);
			temptest.setSize(50,50);
			temptest.addActionListener(new CashActionHandler(calcTable));
			add(temptest);
	      
			JButton memtest = new JButton("memtest");
			memtest.setLocation(250, 610);
			memtest.setSize(50,50);
			memtest.addActionListener(new MemberShipActionListener(calcTable));
			add(memtest);
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      
	      JPanel rightScreen = new JPanel(new BorderLayout());
	      
//	      JPanel rightL = new JPanel(new BorderLayout(8,6));
	      JPanel rightL = new JPanel(new GridLayout(3,1));
	      
	      GridLayout gl = new GridLayout(2, 4);
	      JPanel rightLUp = new JPanel(gl);
	      
	      JPanel rightLCenter = new JPanel(gl);
	      
	      GridLayout gl2 = new GridLayout(1, 4);
	      JPanel rightLDown = new JPanel(gl2);
	      
	      rightL.add(rightLUp);
	      rightL.add(rightLCenter);
	      rightL.add(rightLDown);
	      rightL.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

	      rightLUp.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
	      rightLCenter.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
	      rightLDown.setBorder(BorderFactory.createEmptyBorder(50 , 0 , 50 , 0));
	      
	      gl.setVgap(40);
	      gl.setHgap(40);
	      
	      gl2.setVgap(40);
	      gl2.setHgap(40);
	      JPanel rightR = new JPanel();
	      //"MD",1065,10,160,100,0xb0e8f7,20
	      rightR.setLayout(new GridLayout(2,1));
	      JButton up = new OrderButton("△",1240,125,10,100,0xb0e8f7,15);
	      JButton down = new OrderButton("▽",1240,240,10,100,0xb0e8f7,15);

	      rightR.setBorder(BorderFactory.createEmptyBorder(200 , 5 , 400 , 15));
	      rightR.add(up);
	      rightR.add(down);
	      
	      
	      rightScreen.add("West",rightL);
	      rightScreen.add("East",rightR);

		  jsp2.setLeftComponent(rightL);
		  jsp2.setRightComponent(rightR);
		  rightScreen.add("Center",jsp2);
		  
		  JPanel rightDown = new JPanel(new BorderLayout());
		  
		  
		  MainBtns btns1 = new MainBtns();
	      for(JButton btn:btns1.getMainbtns1()) {
	    	  rightLUp.add(btn);
	      }
	      MainBtns btns2 = new MainBtns();
	      for(JButton btn:btns2.getMainbtns2()) {
	    	  rightLCenter.add(btn);
	      }
	      
	      MainBtns btns3 = new MainBtns();
	      for(JButton btn:btns3.getMainbtns3()) {
	    	  rightLDown.add(btn);
	      }
		  
	      
	      jsp1.setLeftComponent(leftScreen);
	      jsp1.setRightComponent(rightScreen);
	      con.add("Center", jsp1);
	   }
	   
	   
	   
	   public static void main(String[] args) {

	      main_test2 m = new main_test2();
	      m.setDefaultOptions();
	   }
	   
	   
	   

}
