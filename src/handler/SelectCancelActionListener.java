package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
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
		try {
			orderTableModel.removeRow(orderTable.getSelectedRow());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "지울 메뉴를 선택하세요.","Message",JOptionPane.ERROR_MESSAGE);
		}

	}

}
