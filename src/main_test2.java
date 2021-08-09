
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import baseSettings.PosFrame;
import main_component.MainBtns;

public class main_test2 extends PosFrame {

	private JSplitPane jsp1 = new JSplitPane();
	private JSplitPane jsp2 = new JSplitPane();
	
	   public main_test2() {
	      super();
	      mainScreenInit();
	      
	      
	   }
	   
	   
	   private void mainScreenInit() {
  
		  jsp1.setResizeWeight(0.9);
		  jsp2.setResizeWeight(0.8);
		  
		  Container con = this.getContentPane();
		  con.setLayout(new BorderLayout());
		  
	      MainBtns btns = new MainBtns();
	 
	      JPanel leftScreen = new JPanel();
	      JPanel rightScreen = new JPanel(new BorderLayout());
	      
//	      JPanel rightL = new JPanel(new BorderLayout(8,6));
	      JPanel rightL = new JPanel(new GridLayout(3,1));
	      
	      GridLayout gl = new GridLayout(2, 4);
	      JPanel rightLUp = new JPanel(gl);
	      
	      JPanel rightLCenter = new JPanel(gl);
	      
	      GridLayout gl2 = new GridLayout(1, 4);
	      JPanel rightLDown = new JPanel(gl2);
	      
	      rightL.add(rightLUp);
	      rightL.add(rightLCenter);
	      rightL.add(rightLDown);
	      rightL.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

	      rightLUp.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
	      rightLCenter.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
	      rightLDown.setBorder(BorderFactory.createEmptyBorder(50 , 0 , 50 , 0));
	      
	      gl.setVgap(40);
	      gl.setHgap(40);
	      
	      gl2.setVgap(40);
	      gl2.setHgap(40);
	      JPanel rightR = new JPanel();
	      
	      
	      rightScreen.add("West",rightL);
	      rightScreen.add("East",rightR);

		  jsp2.setLeftComponent(rightL);
		  jsp2.setRightComponent(rightR);
		  rightScreen.add("Center",jsp2);
		  
		  JPanel rightDown = new JPanel(new BorderLayout());
		  
		  
		  MainBtns btns1 = new MainBtns();
	      for(JButton btn:btns1.getMainbtns1()) {
	    	  rightLUp.add(btn);
	      }
	      MainBtns btns2 = new MainBtns();
	      for(JButton btn:btns2.getMainbtns2()) {
	    	  rightLCenter.add(btn);
	      }
	      
	      MainBtns btns3 = new MainBtns();
	      for(JButton btn:btns3.getMainbtns3()) {
	    	  rightLDown.add(btn);
	      }
		  
	      
	      jsp1.setLeftComponent(leftScreen);
	      jsp1.setRightComponent(rightScreen);
	      con.add("Center", jsp1);
	   }
	   
	   
	   
	   public static void main(String[] args) {

	      main_test2 m = new main_test2();
	      m.setDefaultOptions();
	   }
	   
	   
	   

}
