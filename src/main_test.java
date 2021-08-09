import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import baseSettings.PosFrame;
import main_component.MainBtns;

public class main_test extends PosFrame {

	private JSplitPane jsp1 = new JSplitPane();
	private JSplitPane jsp2 = new JSplitPane();
	
	   public main_test() {
	      super();
	      mainScreenInit();
	      
	      
	      
	      
	      
	   }
	   
	   
	   private void mainScreenInit() {
  
		  jsp1.setResizeWeight(0.5);
		  jsp2.setResizeWeight(0.8);
		  
		  Container con = this.getContentPane();
		  con.setLayout(new BorderLayout());
		  
	      MainBtns btns = new MainBtns();
	 
	      JPanel leftScreen = new JPanel();
	      JPanel righttScreen = new JPanel(new BorderLayout());
	      
	      JPanel rightL = new JPanel(new BorderLayout(8,6));
	      GridLayout gl = new GridLayout(1, 4);
	      JPanel rightLUp = new JPanel(gl);
	      JPanel rightLCenter = new JPanel();
	      JPanel rightLDown = new JPanel();
	      rightL.add("North",rightLUp);
	      rightL.add("North",rightLCenter);
	      rightL.add("North",rightLDown);
//	      rightLU.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

//	      gl.setVgap(20);
//	      gl.setHgap(20);
//	      
	      JPanel rightR = new JPanel();
	      
	      
	      righttScreen.add("West",rightL);
	      righttScreen.add("East",rightR);

		  jsp2.setLeftComponent(rightL);
		  jsp2.setRightComponent(rightR);
		  righttScreen.add("Center",jsp2);
		  
		  JPanel rightDown = new JPanel(new BorderLayout());
		  
		  
	      for(JButton btn:btns.getMainbtns()) {
	    	  rightLUp.add(btn);
	      }
	      
	      jsp1.setLeftComponent(leftScreen);
	      jsp1.setRightComponent(righttScreen);
	      con.add("Center", jsp1);
	   }
	   
	   
	   
	   public static void main(String[] args) {

	      main_test m = new main_test();
	      m.setDefaultOptions();
	   }
	   
	   
	   

}
