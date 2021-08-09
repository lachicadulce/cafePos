package main_component;

import java.util.ArrayList;

import javax.swing.JButton;

public class MainBtns {

	   private ArrayList<JButton> btns;
	   
	   public MainBtns() {
	      btns = new ArrayList<>();
	      
	      OrderButton a1 = new OrderButton("커피",540,10,160,100,0xb0e8f7,20);
	      btns.add(a1);
	      
	      OrderButton a2 = new OrderButton("음료",715,10,160,100,0xb0e8f7,20);
	      btns.add(a2);
	      
	      OrderButton a3 = new OrderButton("블랜디드",890,160,100,100,0xb0e8f7,20);
	      btns.add(a3);
	      
	      OrderButton a4 = new OrderButton("MD",1065,10,160,100,0xb0e8f7,20);
	      btns.add(a4);
	      
	      OrderButton a5 = new OrderButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>",540,125,160,100,0xfafeff,17);
	      btns.add(a5);
	      
	      OrderButton a6 = new OrderButton("<HTML><body style='text-align:center'>바닐라라떼(R)<br>4500</body></HTML>",715,125,160,100,0xfafeff,17);
	      btns.add(a6);
	      
	      OrderButton a7 = new OrderButton("<HTML><body style='text-align:center'>카페라떼(R)<br>4000</body></HTML>",890,125,160,100,0xfafeff,17);
	      btns.add(a7);
	      
	      OrderButton a8 = new OrderButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>",1065,125,160,100,0xfafeff,17);
	      btns.add(a8);
//	      OrderButton up = new OrderButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>",1065,125,160,100,0xfafeff,17);
//	      btns.add(up);
	      OrderButton a9 = new OrderButton("<HTML><body style='text-align:center'>아이스<br>아메리카노(R)<br>3500</body></HTML>",540,240,160,100,0xfafeff,17);
	      btns.add(a9);
	      
	      OrderButton a10 = new OrderButton("<HTML><body style='text-align:center'>아이스<br>바닐라라떼(R)<br>4500</body></HTML>",715,240,160,100,0xfafeff,17);
	      btns.add(a10);
	      
	      OrderButton a11 = new OrderButton("<HTML><body style='text-align:center'>카페라떼(L)<br>4500</body></HTML>",890,240,160,100,0xfafeff,17);
	      btns.add(a11);
	      
	      OrderButton a12 = new OrderButton("<HTML><body style='text-align:center'>카페모카(L)<br>4300</body></HTML>",1065,240,160,100,0xfafeff,17);
	      btns.add(a12);
	      
	      OrderButton a13 = new OrderButton("<HTML><body style='text-align:center'>콜드브루<br>4000</body></HTML>",540,355,160,100,0xfafeff,17);
	      btns.add(a13);
	      
	      OrderButton a14 = new OrderButton("<HTML><body style='text-align:center'>콜드브루라떼(R)<br>4500</body></HTML>",715,355,160,100,0xfafeff,17);
	      btns.add(a14);
	      
	      OrderButton a15 = new OrderButton("<HTML><body style='text-align:center'>시럽추가<br>500</body></HTML>",890,355,160,100,0xfafeff,17);
	      btns.add(a15);
	      
	      OrderButton a16 = new OrderButton("<HTML><body style='text-align:center'>개인컵<br>-300</body></HTML>",1065,355,160,100,0xfafeff,17);
	      btns.add(a16);
	   }//생성자
	   
	   public ArrayList<JButton> getMainbtns(){
	      return btns;
	   }//get ArrayList

}
