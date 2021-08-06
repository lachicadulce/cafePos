package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

public class QuantityIncreaseActionListener implements ActionListener {

	JTable orderTable;

	public QuantityIncreaseActionListener(JTable orderTable) {
		this.orderTable = orderTable;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String quantityStr = (String) orderTable.getValueAt(orderTable.getSelectedRow(), 1);
		int quantity = Integer.parseInt(quantityStr);
		quantityStr = Integer.toString(++quantity);
		orderTable.setValueAt(quantityStr, orderTable.getSelectedRow(), 1);
		
		

	}

}
