package handler;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// 총금액 계산 (메뉴 버튼눌렀을떄나 수량 증감할때 바로 금액이 변경되게 하기 위해)

public class CalcTableTotal {

	JTable calcTable;
	DefaultTableModel orderTableModel;
	JTable orderTable;

	public CalcTableTotal(JTable calcTable, DefaultTableModel orderTableModel, JTable orderTable) {
		this.calcTable = calcTable;
		this.orderTableModel = orderTableModel;
		this.orderTable = orderTable;
		
		int total = 0;
		
		
		for(int i = 0; i < orderTableModel.getRowCount(); i++) {
			total += Integer.parseInt((String)orderTableModel.getValueAt(i, 2));
		}
		String totalstr = Integer.toString(total);
		calcTable.setValueAt(totalstr, 0, 1);

	}


}
