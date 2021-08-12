package main_component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import handler.MenuButtonActionListener;

public class OrderButton extends JButton {
	
	int seq;
	String sort;
//	JTable calcTable;
//	DefaultTableModel orderTableModel;
//	JTable orderTable;
	

	public OrderButton(int seq, String sort, MenuButtonActionListener mbal) {
		super();
		this.seq = seq;
		this.sort = sort;
//		this.calcTable = calcTable;
//		this.orderTableModel = orderTableModel;
//		this.orderTable = orderTable;

		
		setFont(new Font("System",Font.BOLD, 15));
		setBackground(new Color(0xfafeff));
		addActionListener(mbal);
		
	}
	
	
	



}