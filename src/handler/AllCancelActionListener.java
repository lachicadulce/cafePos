package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 전체취소 버튼 액션리스너
// 버튼을 누르면 주문서에 있는 모든 항목 삭제

public class AllCancelActionListener implements ActionListener {

	DefaultTableModel orderTableModel;
	JTable calcTable;

	public AllCancelActionListener(DefaultTableModel orderTableModel, JTable calcTable) {
		this.orderTableModel = orderTableModel;
		this.calcTable = calcTable;  
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i= 0; i < orderTableModel.getRowCount(); i++) {
			orderTableModel.removeRow(i);
		}

		calcTable.setValueAt("", 0, 1);
	}

}
