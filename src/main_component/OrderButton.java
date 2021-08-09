package main_component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class OrderButton extends JButton{

	   public OrderButton() {
		      super();
		   }
		   
		   public OrderButton(String exp,int cur_x,int cur_y,int wid,int height,int color,int size) {
		      
		      super(exp);
		      
		      super.setBounds(cur_x, cur_y, wid, height);
		      super.setFont(new Font("System",Font.BOLD,size));
		      super.setBackground(new Color(color));
		      
		   }
}
