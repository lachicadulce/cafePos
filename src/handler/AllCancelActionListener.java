package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

public class AllCancelActionListener implements ActionListener {

	JTable orderTable;
	
	public AllCancelActionListener(JTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i= 0; i < orderTable.getRowCount(); i++) {
			for(int j =0 ; j < orderTable.getColumnCount(); j++) {
				orderTable.setValueAt("", i, j);
			}
		}

	}

}
