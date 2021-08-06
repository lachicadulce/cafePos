package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SelectCancelActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable orderTable;
	
	public SelectCancelActionListener(DefaultTableModel orderTableModel, JTable orderTable) {
		this.orderTableModel = orderTableModel;
		this.orderTable = orderTable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		orderTableModel.removeRow(orderTable.getSelectedRow());
		
		

	}

}
