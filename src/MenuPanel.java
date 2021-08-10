import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import main_component.MainBtns;
import main_component.OrderButton;

public class MenuPanel extends JPanel {
	
	JSplitPane jsp2;
	
	public MenuPanel(JSplitPane jsp2) {
		
		this.jsp2 = jsp2;
		
		JPanel pages1 = new JPanel(new CardLayout()); // rightScreenN번을 넣을 '카드레이아웃'틀.
		JPanel pages2 = new JPanel(new CardLayout());
		
		setLayout(new BorderLayout());
		
		JPanel rightL = new JPanel(new GridLayout(3,1));//rightScreen쪽에서 위아래 버튼을 제외한 왼쪽편에 속한 모든메뉴 담는 JPanel
		
		GridLayout gl = new GridLayout(2, 4);
		JPanel rightLUp = new JPanel(gl); //rightL에서 제일 위
		JPanel rightLUp2 = new JPanel(gl);
		JPanel rightLUp3 = new JPanel(gl);

		JPanel rightLCenter = new JPanel(gl); //rightL에서 가운데
		JPanel rightLCenter2 = new JPanel(gl);
		JPanel rightLCenter3 = new JPanel(gl);
		
		pages1.add("upcard1",rightLUp);
		pages1.add("upcard2",rightLUp2);
		pages1.add("upcard3",rightLUp3);

		pages2.add("upcard1",rightLCenter);
		pages2.add("upcard2",rightLCenter2);
		pages2.add("upcard3",rightLCenter3);

		GridLayout gl2 = new GridLayout(1, 4);
		JPanel rightLDown = new JPanel(gl2); ////rightL에서 제일 아래

		rightL.add(pages1);
		rightL.add(pages2);
		rightL.add(rightLDown);
		rightL.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

		rightLUp.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		rightLCenter.setBorder(BorderFactory.createEmptyBorder(20 , 0 , 20 , 0));
		
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gblc = new GridBagConstraints();
		rightLDown.setLayout(gbl);
		rightLDown.setBorder(BorderFactory.createEmptyBorder(50 , 0 , 50 , 0));

		gl.setVgap(40);
		gl.setHgap(40);

		gl2.setVgap(40);
		gl2.setHgap(40);
		JPanel rightR = new JPanel();
		//"MD",1065,10,160,100,0xb0e8f7,20
		rightR.setLayout(new GridLayout(2,1));
		JButton up = new OrderButton("△",1240,125,10,100,0xb0e8f7,15);
		JButton down = new OrderButton("▽",1240,240,10,100,0xb0e8f7,15);

		rightR.setBorder(BorderFactory.createEmptyBorder(200 , 5 , 400 , 15));
		rightR.add(up);
		rightR.add(down);

//		JPanel rightL2 = new JPanel(new GridLayout(3,1));
//		JPanel rightL3 = new JPanel(new GridLayout(3,1));




		add("West",rightL);
		add("East",rightR);

		jsp2.setLeftComponent(rightL);
		jsp2.setRightComponent(rightR);
		add("Center",jsp2);

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


		actionPrevAdd(up, pages1, pages2);
		actionNextAdd(down, pages1, pages2);
		
		
	}
	
	private void actionNextAdd(JButton a, JPanel p1, JPanel p2) {

		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
				CardLayout card_layout2 = ((CardLayout)p2.getLayout());
				card_layout1.next(p1);
				card_layout2.next(p2);
			}
		});

	}// 다음 페이지 넘기게 하는 액션리스너추가

	private void actionPrevAdd(JButton a, JPanel p1, JPanel p2) {

		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				CardLayout card_layout1 = ((CardLayout)p1.getLayout());
				CardLayout card_layout2 = ((CardLayout)p2.getLayout());
				card_layout1.previous(p1);
				card_layout2.previous(p2);
			}
		});

	}// 이전 페이지 넘기게 하는 액션리스너추가

}
