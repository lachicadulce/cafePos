package handler;

import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import manager_file.ManagerSales;

public class ManagerButtonActionListener implements ActionListener {

	JFrame frame;
	public ManagerButtonActionListener(JFrame frame) {
		super();
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ManagerSales ms = new ManagerSales();
		ms.setDefaultOptions();

		frame.dispose();
		frame.validate();
		frame.invalidate();

	}

}
