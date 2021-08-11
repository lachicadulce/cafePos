package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 수량- 버튼 액션 리스너
// 주문서에 있는 것을 선택해서 누르면 수량 감소

public class QuantityDecreaseActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable orderTable;
	JTable calcTable; 
	
	
	
	public QuantityDecreaseActionListener(JTable calcTable, DefaultTableModel orderTableModel, JTable orderTable) {
		this.calcTable = calcTable;
		this.orderTableModel = orderTableModel;
		this.orderTable = orderTable;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

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
		quantitystr = Integer.toString(--quantity);
		
		if (quantity <= 0) {
			orderTableModel.removeRow(selected);
		} else {
			orderTableModel.setValueAt(quantitystr, selected, 1);
			
			int total = quantity * price;
			String totalstr = Integer.toString(total);
			orderTableModel.setValueAt(totalstr, selected, 2);
			
		}
	}

}
