package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

// 환전계산 버튼 액션 리스너
// 주문서의 총금액과 금액계산 후 누르면 거스름돈 계산해줌

public class ChangeActionListener implements ActionListener {

	JTable calcTable;
	public ChangeActionListener(JTable calcTable) {
		this.calcTable = calcTable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int[] priceSaleReceived = new int[3];
		for(int i=0; i < calcTable.getRowCount() -1; i++) {
			if((String)calcTable.getValueAt(i, 1) == "") {
				priceSaleReceived[i] = 0;
			} else {
				priceSaleReceived[i] = Integer.parseInt((String)calcTable.getValueAt(i, 1));
			}
			
		}
		
		int change = priceSaleReceived[0] - priceSaleReceived[1]- priceSaleReceived[2];
		
		System.out.println(change);
		if(change <= 0)  {
			change = Math.abs(change);
		} else {
			
		}
		String changestr = Integer.toString(change);
		calcTable.setValueAt(changestr, 3, 1);
		

	}

}
