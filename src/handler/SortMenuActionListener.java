package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main_component.MenuPanel;
import main_component.RightPanelBasic;

public class SortMenuActionListener implements ActionListener {

	RightPanelBasic rpb;
	ActionListener al;
	
	public SortMenuActionListener(RightPanelBasic rpb, ActionListener al) {
		this.rpb = rpb;
		this.al = al;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		rpb.getRightLCenter().removeAll();
		rpb.getRightLCenter().revalidate();
		rpb.getRightLCenter().repaint();
		new MenuPanel(e.getActionCommand(), rpb, al);

	}

}
