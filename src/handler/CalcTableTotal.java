package handler;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
