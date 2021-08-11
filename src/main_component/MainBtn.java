package main_component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import handler.MenuButtonActionListener;

public class MainBtn extends JButton{

	String sort;
	int count;

	public MainBtn(String sort, MenuButtonActionListener mbal) {
		
		this.sort = sort;
		
	    setFont(new Font("System",Font.BOLD,15));
	    setBackground(new Color(0xfafeff));
	    addActionListener(mbal);
	    
	    setText(""+(++count));
	    
		
//		btns1 = new ArrayList<>();
//		btns2 = new ArrayList<>();
//		btns3 = new ArrayList<>();

		//	      BasicButton a1 = new BasicButton("커피",540,10,160,100,0xb0e8f7,20);
		//	      btns1.add(a1);
		//	      
		//	      BasicButton a2 = new BasicButton("음료",715,10,160,100,0xb0e8f7,20);
		//	      btns1.add(a2);
		//	      
		//	      BasicButton a3 = new BasicButton("블랜디드",890,160,100,100,0xb0e8f7,20);
		//	      btns1.add(a3);
		//	      
		//	      BasicButton a4 = new BasicButton("MD",1065,10,160,100,0xb0e8f7,20);
		//	      btns1.add(a4);
		
		

//		BasicButton a5 = new BasicButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>",540,125,160,100,0xfafeff,15);
//		btns1.add(a5);
//
//		BasicButton a6 = new BasicButton("<HTML><body style='text-align:center'>바닐라라떼(R)<br>4500</body></HTML>",715,125,160,100,0xfafeff,15);
//		btns1.add(a6);
//
//		BasicButton a7 = new BasicButton("<HTML><body style='text-align:center'>카페라떼(R)<br>4000</body></HTML>",890,125,160,100,0xfafeff,15);
//		btns1.add(a7);
//
//		BasicButton a8 = new BasicButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>",1065,125,160,100,0xfafeff,15);
//		btns1.add(a8);
//
//		BasicButton a9 = new BasicButton("<HTML><body style='text-align:center'>아이스<br>아메리카노(R)<br>3500</body></HTML>",540,240,160,100,0xfafeff,17);
//		btns2.add(a9);
//
//		BasicButton a10 = new BasicButton("<HTML><body style='text-align:center'>아이스<br>바닐라라떼(R)<br>4500</body></HTML>",715,240,160,100,0xfafeff,17);
//		btns2.add(a10);
//
//		BasicButton a11 = new BasicButton("<HTML><body style='text-align:center'>카페라떼(L)<br>4500</body></HTML>",890,240,160,100,0xfafeff,17);
//		btns2.add(a11);
//
//		BasicButton a12 = new BasicButton("<HTML><body style='text-align:center'>카페모카(L)<br>4300</body></HTML>",1065,240,160,100,0xfafeff,17);
//		btns2.add(a12);
//
//		BasicButton a13 = new BasicButton("<HTML><body style='text-align:center'>콜드브루<br>4000</body></HTML>",540,355,160,100,0xfafeff,17);
//		btns2.add(a13);
//
//		BasicButton a14 = new BasicButton("<HTML><body style='text-align:center'>콜드브루라떼(R)<br>4500</body></HTML>",715,355,160,100,0xfafeff,17);
//		btns2.add(a14);
//
//		BasicButton a15 = new BasicButton("<HTML><body style='text-align:center'>시럽추가<br>500</body></HTML>",890,355,160,100,0xfafeff,17);
//		btns2.add(a15);
//
//		BasicButton a16 = new BasicButton("<HTML><body style='text-align:center'>개인컵<br>-300</body></HTML>",1065,355,160,100,0xfafeff,17);
//		btns2.add(a16);

//		BasicButton a17 = new BasicButton("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>",540,355,160,100,0xe2d4fc,25);
//		btns3.add(a17);
//
//		BasicButton a18 = new BasicButton("<HTML><body style='text-align:center'>맴버쉽</body></HTML>",715,355,160,100,0xe3aada,25);
//		btns3.add(a18);
//
//		BasicButton a19 = new BasicButton("<HTML><body style='text-align:center'>결제</body></HTML>",890,355,160,100,0xb5f5c8,25);
//		btns3.add(a19);



	}
}
