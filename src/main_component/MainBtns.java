package main_component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainBtns extends JButton{

	   private ArrayList<JButton> btns1;
	   private ArrayList<JButton> btns2;
	   private ArrayList<JButton> btns3;
	   public MainBtns() {
		  btns1 = new ArrayList<>();
	      btns2 = new ArrayList<>();
	      btns3 = new ArrayList<>();
	      
	      BasicButton a1 = new BasicButton("커피",540,10,160,100,0xb0e8f7,20);
	      btns1.add(a1);
	      
	      BasicButton a2 = new BasicButton("음료",715,10,160,100,0xb0e8f7,20);
	      btns1.add(a2);
	      
	      BasicButton a3 = new BasicButton("블랜디드",890,160,100,100,0xb0e8f7,20);
	      btns1.add(a3);
	      
	      BasicButton a4 = new BasicButton("MD",1065,10,160,100,0xb0e8f7,20);
	      btns1.add(a4);
	      
	      BasicButton a5 = new BasicButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>",540,125,160,100,0xfafeff,15);
	      btns1.add(a5);
	      
	      BasicButton a6 = new BasicButton("<HTML><body style='text-align:center'>바닐라라떼(R)<br>4500</body></HTML>",715,125,160,100,0xfafeff,15);
	      btns1.add(a6);
	      
	      BasicButton a7 = new BasicButton("<HTML><body style='text-align:center'>카페라떼(R)<br>4000</body></HTML>",890,125,160,100,0xfafeff,15);
	      btns1.add(a7);
	      
	      BasicButton a8 = new BasicButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>",1065,125,160,100,0xfafeff,15);
	      btns1.add(a8);

	      BasicButton a9 = new BasicButton("<HTML><body style='text-align:center'>아이스<br>아메리카노(R)<br>3500</body></HTML>",540,240,160,100,0xfafeff,17);
	      btns2.add(a9);
	      
	      BasicButton a10 = new BasicButton("<HTML><body style='text-align:center'>아이스<br>바닐라라떼(R)<br>4500</body></HTML>",715,240,160,100,0xfafeff,17);
	      btns2.add(a10);
	      
	      BasicButton a11 = new BasicButton("<HTML><body style='text-align:center'>카페라떼(L)<br>4500</body></HTML>",890,240,160,100,0xfafeff,17);
	      btns2.add(a11);
	      
	      BasicButton a12 = new BasicButton("<HTML><body style='text-align:center'>카페모카(L)<br>4300</body></HTML>",1065,240,160,100,0xfafeff,17);
	      btns2.add(a12);
	      
	      BasicButton a13 = new BasicButton("<HTML><body style='text-align:center'>콜드브루<br>4000</body></HTML>",540,355,160,100,0xfafeff,17);
	      btns2.add(a13);
	      
	      BasicButton a14 = new BasicButton("<HTML><body style='text-align:center'>콜드브루라떼(R)<br>4500</body></HTML>",715,355,160,100,0xfafeff,17);
	      btns2.add(a14);
	      
	      BasicButton a15 = new BasicButton("<HTML><body style='text-align:center'>시럽추가<br>500</body></HTML>",890,355,160,100,0xfafeff,17);
	      btns2.add(a15);
	      
	      BasicButton a16 = new BasicButton("<HTML><body style='text-align:center'>개인컵<br>-300</body></HTML>",1065,355,160,100,0xfafeff,17);
	      btns2.add(a16);
	      
	      BasicButton a17 = new BasicButton("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>",540,355,160,100,0xe2d4fc,25);
	      btns3.add(a17);
	      
	      BasicButton a18 = new BasicButton("<HTML><body style='text-align:center'>맴버쉽</body></HTML>",715,355,160,100,0xe3aada,25);
	      btns3.add(a18);
	      
	      BasicButton a19 = new BasicButton("<HTML><body style='text-align:center'>결제</body></HTML>",890,355,160,100,0xb5f5c8,25);
	      btns3.add(a19);
	      
	      
	      
	      for (int i = 0; i < btns1.size(); i++) {
	    	  
			btns1.get(i).addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JButton la = (JButton)e.getSource();
					String str = la.getText();
					System.out.println(str);
				}
			});
		}
	      //////////////////////////////////////////////////////
	      for (int i = 0; i < btns2.size(); i++) {
			btns2.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					JButton la = (JButton)e.getSource();
					String str = la.getText();
					System.out.println(str);
				}
			});
		}
	      ///////////////////////////////////////////////////////
	      for (int i = 0; i < btns3.size(); i++) {
			btns3.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					JButton la = (JButton)e.getSource();
					String str = la.getText();
					System.out.println(str);
				}
			});
		}
	      
	   }//생성자
	   
	   
	   
	   public ArrayList<JButton> getMainbtns1(){
	      return btns1;
	   }//get ArrayList
	   
	   public ArrayList<JButton> getMainbtns2(){
		      return btns2;
		   }//get ArrayList
	   
	   public ArrayList<JButton> getMainbtns3(){
		      return btns3;
		   }//get ArrayList
	   
	   

}
