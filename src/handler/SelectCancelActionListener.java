package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 선택취소 버튼 액션 리스너
// 주문서에서 선택하고 누르면 그 주문 삭제

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
