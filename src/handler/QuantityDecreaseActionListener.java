package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class QuantityDecreaseActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable orderTable;

	public QuantityDecreaseActionListener(DefaultTableModel orderTableModel, JTable orderTable) {
		this.orderTableModel = orderTableModel;
		this.orderTable = orderTable;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String quantityStr = (String) orderTable.getValueAt(orderTable.getSelectedRow(), 1);
		int quantity = Integer.parseInt(quantityStr);
		quantityStr = Integer.toString(--quantity);
		if (quantity <= 0) {
			orderTableModel.removeRow(orderTable.getSelectedRow());
		} else {
			orderTable.setValueAt(quantityStr, orderTable.getSelectedRow(), 1);
		}
		

	}

}
