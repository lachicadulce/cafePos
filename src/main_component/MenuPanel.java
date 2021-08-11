package main_component;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import handler.CashActionHandler;
import handler.MemberShipActionListener;
import handler.MenuButtonActionListener;
import main_component.BasicButton;
import main_component.ButtonSetting;
import main_component.MainBtns;
import main_component.OrderButton;


public class MenuPanel extends JPanel {
	String sort;
	int count;
	
	public MenuPanel(String sort, RightPanelBasic rpb, ActionListener al) {
		this.sort = sort;
		
		JPanel rightCenter = rpb.getRightLCenter();
				
		rightCenter.removeAll();
		
		for(int i = 0; i < 12; i++) {
			++count;
			rightCenter.add(new ButtonSetting(sort+count, 15, 0xfafeff, al));  
		}
		
		
		
	}

//	private void actionNextAdd(JButton a, JPanel p1) {
//
//		a.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
//				
//				card_layout1.next(p1);
//				
//			}
//		});
//
//	}// 다음 페이지 넘기게 하는 액션리스너추가
//
//	private void actionPrevAdd(JButton a, JPanel p1) {
//
//		a.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
//				
//				card_layout1.previous(p1);
//				
//			}
//		});
//
//	}// 이전 페이지 넘기게 하는 액션리스너추가

}
