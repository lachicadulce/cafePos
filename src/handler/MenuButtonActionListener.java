package handler;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MenuButtonActionListener implements ActionListener {

	JTable orderTable;
	DefaultTableModel orderTableModel;
	JTable calcTable;
	int count;

	public MenuButtonActionListener(JTable calcTable,DefaultTableModel orderTableModel, JTable orderTable) {
		this.orderTable = orderTable;
		this.orderTableModel = orderTableModel;
		this.calcTable = calcTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String str = e.getActionCommand();

		String menu = str.substring(str.indexOf("'>")+2, str.indexOf("<br>"));

		String money = str.substring(str.indexOf("<br>")+4, str.indexOf("</body>"));
		
		System.out.println(count);
		
		
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
