package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import receipt.Receipt;

public class ReceiptActionListener implements ActionListener  {

	JFrame frame;
	public ReceiptActionListener(JFrame frame) {
		super();
		
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Receipt rc = new Receipt();
		rc.setDefaultOptions();
		frame.dispose();
	}
}
