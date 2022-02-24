package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


// 환전 버튼 액션 리스너
// 누르면 팝업창으로 "금고가 열렸습니다" 나옴

public class SafeOpenActionListener implements ActionListener {

	JTable calcTable;
	DefaultTableModel orderTableModel;
	
	public SafeOpenActionListener(JTable calcTable, DefaultTableModel orderTableModel) {
		this.calcTable = calcTable;
		this.orderTableModel = orderTableModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//JOptionPane.showMessageDialog(null, "금고가 열렸습니다.", "알림", 1);
		int exchange = 0;
		int n = JOptionPane.showConfirmDialog(null, "열렸습니다", "확인", JOptionPane.YES_OPTION);
		switch(n) {
		case JOptionPane.YES_OPTION: exchange = 1; System.out.println("환전을 했습니다." + exchange); break;
		case JOptionPane.NO_OPTION: exchange = 0; System.out.println("환전을 취소했습니다." + exchange); break;
		}
		
		if(exchange == 1) {
			for (int i = 0; i < 4; i++) {
				orderTableModel.setRowCount(0);
				calcTable.setValueAt("0", i, 1);
			}
		}
	}// 끝 - actionPerformed

}
