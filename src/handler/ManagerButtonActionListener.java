package handler;

import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import manager_file.ManagerSales;

public class ManagerButtonActionListener implements ActionListener {

	Frame frame;
	public ManagerButtonActionListener(Frame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
//		ManagerSales ms = new ManagerSales();
//		ms.setDefaultOptions();

		frame.dispose();
		frame.validate();
		frame.invalidate();
	}

}
