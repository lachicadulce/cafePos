package handler;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ChangeMenuDownActionListener implements ActionListener {
	
	JPanel rightCenter;
	
	public ChangeMenuDownActionListener(JPanel rightCenter) {
		
		this.rightCenter = rightCenter;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		CardLayout card_layout1 = ((CardLayout)rightCenter.getLayout());
		
		card_layout1.next(rightCenter);
		
	}

}
