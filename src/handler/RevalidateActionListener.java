package handler;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

// 주문서와 주문금액 초기화 버튼
// 전제취소 버튼 액션

public class RevalidateActionListener implements ActionListener {
	
	Frame frame;
	JTable calcTable;
	DefaultTableModel orderTableModel;
	
	
	public RevalidateActionListener(Frame frame, JTable calcTable, DefaultTableModel orderTableModel) {
		this.frame = frame;
		this.calcTable = calcTable;
		this.orderTableModel = orderTableModel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		orderTableModel.setRowCount(0);
		for(int i= 0; i < calcTable.getRowCount(); i++) {
			calcTable.setValueAt("", i, 1);	
		}
		frame.validate();
		

	}

}
