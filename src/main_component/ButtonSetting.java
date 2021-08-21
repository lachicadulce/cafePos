package main_component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonSetting extends JButton {

	
	public ButtonSetting(String name, int Fontsize, int color, ActionListener al) {
		setText(name);
		setFont(new Font("System",Font.BOLD,Fontsize));
		setBackground(new Color(color));
		addActionListener(al);
	}
	
	public void setActionListener(ActionListener al) {
		addActionListener(al);
	}
	
	
}
