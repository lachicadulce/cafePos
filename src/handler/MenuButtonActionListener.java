
package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 메뉴 버튼 액션 리스너
// 메뉴 버튼이 눌리면 왼쪽 주문서에 추가됨
// 아직 DB연동 안되서 수정해야함

public class MenuButtonActionListener implements ActionListener {

	
	
	//메뉴 버튼 눌렀을떄 액션 
	
	JTable orderTable;
	DefaultTableModel orderTableModel;
	JTable calcTable;
	int count;
	String menu;
	public MenuButtonActionListener(JTable calcTable,DefaultTableModel orderTableModel, JTable orderTable) {
		this.calcTable = calcTable;
		this.orderTable = orderTable;
		this.orderTableModel = orderTableModel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		String str = e.getActionCommand();
		
		String menuSub = str.substring(str.indexOf("'>")+2, str.indexOf("</body>") );
		
		String[] menulist = menuSub.split("<br>");
		menu = "";
		for(int i = 0; i < menulist.length -1; i++) {
			menu += menulist[i] + " ";
		}
		
		String money = menulist[menulist.length-1];
		
		int row = getRowByValue(menu);
		
		if (row == -1) {
			count = 0;
		}
		if(count <= 0) {
			count = 0;
			orderTableModel.addRow(new Object[] {menu, ++count, money}); //행추가
			
		} else {
			
			int quantity;
			String quantityStr;
			try {
				orderTable.clearSelection(); // 메뉴 클릭할때마다 테이블에 클릭내용 해제.
				quantity = (int) orderTableModel.getValueAt(row, 1);
				quantityStr = Integer.toString(++quantity);
				orderTableModel.setValueAt(quantityStr, row, 1);
						
				
			} catch (Exception e2) {
				
				quantityStr = (String) orderTableModel.getValueAt(row, 1);
				quantity = Integer.parseInt(quantityStr);
				quantityStr = Integer.toString(++quantity);
				orderTableModel.setValueAt(quantityStr, row, 1);
			}

			int total = quantity * Integer.parseInt(money);;
			String totalstr = Integer.toString(total);
			orderTableModel.setValueAt(totalstr, row, 2);
			
		}
		
		new CalcTableTotal(calcTable, orderTableModel, orderTable);


	}

	private int getRowByValue(String value) {
		int row = -1;
		for (int i = orderTableModel.getRowCount() - 1; i >= 0; --i) {
			for (int j = orderTableModel.getColumnCount() - 1; j >= 0; --j) {
				if (orderTableModel.getValueAt(i, j) != null && orderTableModel.getValueAt(i, j).equals(value)) {
					// what if value is not unique?
					row = i;
				}
			}
		}
		return row;
	}

}
