package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 수량+ 액션 리스너
// 주문서에서 선택하고 누르면 수량 증가

public class QuantityIncreaseActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable orderTable;
	JTable calcTable; 
	static int quantity;
	
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
//		int quantity;
		String quantitystr = null; 
		int selected = -1;
		selected = orderTable.getSelectedRow(); // selected는 선택된 row값이 들어감(0부터 시작)
		System.out.println("선택:" + selected);
		try {
			if(selected >= 0) {
				// 선택된row, 수량을 나타내는 컬럼인 인덱스1 
				/// quantity는 선택된 row에서의 물품갯수를 저장한다.
				quantity = (int) orderTableModel.getValueAt(selected, 1);
			}else {
				JOptionPane.showMessageDialog(null, "수량을 증가시킬 목차를 선택하세요.","Message",JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (Exception e2) {

			quantitystr = (String) orderTableModel.getValueAt(selected, 1);
			quantity = Integer.parseInt(quantitystr);

		}
		quantityCheck(quantity, quantitystr, selected);// 수량한개 증가시키고 그에 맞는 가격을 set시킨다!!
		new CalcTableTotal(calcTable, orderTableModel, orderTable);
		
		
	}//actionPerformed

	private void quantityCheck(int quantity, String quantitystr, int selected) {

		int price = Integer.parseInt((String)orderTableModel.getValueAt(selected, 2)) / quantity; // price는 한개당 가격.
		quantitystr = Integer.toString(++quantity);

		orderTableModel.setValueAt(quantitystr, selected, 1);

		int total = quantity * price;
		String totalstr = Integer.toString(total);
		orderTableModel.setValueAt(totalstr, selected, 2);

	}
}

