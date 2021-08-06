package handler;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OrderTableListSelectionListener implements ListSelectionListener {

	
	JTable orderTable;
	
	public OrderTableListSelectionListener(JTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
//		System.out.println("사용자가 다른 칸을 선택하면 발생하는 이벤트.");
//		System.out.println(orderTable.getSelectedRow() + "행이 선택됨");
	}

	
}
