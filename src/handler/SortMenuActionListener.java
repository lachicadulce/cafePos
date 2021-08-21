package handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main_component.ButtonSetting;
import main_component.MenuPanel;
import main_component.RightPanelBasic;

public class SortMenuActionListener implements ActionListener {

	RightPanelBasic rpb;
	ActionListener al;
	//	ButtonSetting sortMenu;
	ArrayList<ButtonSetting> sortMenu;
	public SortMenuActionListener(RightPanelBasic rpb, ActionListener al, ArrayList<ButtonSetting> sortMenu) {
		this.rpb = rpb;
		this.al = al;
		this.sortMenu = sortMenu;
		setbg();
	}
	
	// 첫화면에 선택되어 있는 coffee 버튼 배경색 설정
	public void setbg() { 
		sortMenu.get(0).setBackground(new Color(0x819FF7));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i = 0 ; i < sortMenu.size(); i++) {
			if (sortMenu.get(i).getText().equals(e.getActionCommand())) {
				sortMenu.get(i).setFont(new Font("System",Font.BOLD, 20));
				sortMenu.get(i).setBackground(new Color(0x819FF7));
			} else {
				sortMenu.get(i).setFont(new Font("System",Font.BOLD,20));
				sortMenu.get(i).setBackground(new Color(0xA9D0F5));
			}
		}
		//		sortMenu.setFont(new Font("System",Font.BOLD, 30));
		rpb.getRightLCenter().removeAll();
		rpb.getRightLCenter().revalidate();
		rpb.getRightLCenter().repaint();
		new MenuPanel(e.getActionCommand(), rpb, al);

	}

}
