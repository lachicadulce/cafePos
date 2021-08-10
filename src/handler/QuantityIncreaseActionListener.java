package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class QuantityIncreaseActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable orderTable;
	JTable calcTable; 

	public QuantityIncreaseActionListener(JTable calcTable,DefaultTableModel orderTableModel, JTable orderTable) {
		this.calcTable = calcTable;
		this.orderTable = orderTable;
		this.orderTableModel = orderTableModel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		//		int quantity = (int) orderTable.getValueAt(orderTable.getSelectedRow(), 1);
		//		String quantityStr = Integer.toString(++quantity);
		//		orderTable.setValueAt(quantityStr, orderTable.getSelectedRow(), 1);
		int quantity;
		String quantitystr = null; 
		int selected = orderTable.getSelectedRow();

		try {
			quantity = (int) orderTableModel.getValueAt(selected, 1);



		} catch (Exception e2) {

			quantitystr = (String) orderTableModel.getValueAt(selected, 1);
			quantity = Integer.parseInt(quantitystr);

		}

		quantityCheck(quantity, quantitystr, selected);
		new CalcTableTotal(calcTable, orderTableModel, orderTable);

	}

	private void quantityCheck(int quantity, String quantitystr, int selected) {

		int price = Integer.parseInt((String)orderTableModel.getValueAt(selected, 2)) / quantity;
		quantitystr = Integer.toString(++quantity);

		orderTableModel.setValueAt(quantitystr, selected, 1);

		int total = quantity * price;
		String totalstr = Integer.toString(total);
		orderTableModel.setValueAt(totalstr, selected, 2);

	}
}

