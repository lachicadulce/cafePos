import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import baseSettings.PosFrame;

public class MainMenu extends PosFrame implements ActionListener{

	public MainMenu() {
		super();
		
		setLayout(null);
		
//		ArrayList<String> columnNames =  
		
		String[] columnNames = {"ID","NAME","JOB","DEPT"};
		String[][] data = {
				{"1","Smith","IT_PROG","IT"},
				{"2","Allen","IT_PROG","IT"},
				{"3","Ki","IT_PROG","IT"},
				{"4","Zoey","IT_PROG","IT"},
				{"5","Warren","IT_PROG","IT"},
		};
		
	
		
//		JTextArea record = new JTextArea("기본값"); 
		JTable record = new JTable(data, columnNames);
		record.setBounds(10, 10, 500, 400);
		
		
		JScrollPane scrollPane = new JScrollPane(record);
		record.getTableHeader().setPreferredSize(new Dimension(
				scrollPane.getWidth(),50));
		record.getTableHeader().setFont(new Font("Small Fonts",Font.BOLD,22));
		record.setRowHeight(25);
		record.setAlignmentY(JTable.TOP_ALIGNMENT);
		record.setCellSelectionEnabled(true);
		add(record);
		
		
		JButton cof = new JButton("커피");
		cof.setBounds(540, 10, 160, 100);
		cof.setFont(new Font("System",Font.BOLD,20));
		add(cof);
		cof.setBackground(new Color(0xb0e8f7));
		JButton bev = new JButton("음료");
		bev.setBounds(715, 10, 160, 100);
		bev.setFont(new Font("System",Font.BOLD,20));
		add(bev);
		bev.setBackground(new Color(0xb0e8f7));
		JButton blendid = new JButton("블랜디드");
		blendid.setBounds(890, 10, 160, 100);
		blendid.setBackground(new Color(0xb0e8f7));
		blendid.setFont(new Font("System",Font.BOLD,20));
		add(blendid);
		JButton md = new JButton("MD");
		md.setBounds(1065, 10, 160, 100);
		md.setBackground(new Color(0xb0e8f7));
		md.setFont(new Font("System",Font.BOLD,20));
		add(md);
		///////////////////////////////////////
		JButton americano = new JButton("<HTML><body style='text-align:center'>아메리카노(R)<br>3500</body></HTML>");
		americano.setBounds(540, 125, 160, 100);
		americano.setBackground(new Color(0xfafeff));
		americano.setFont(new Font("System",Font.BOLD,17));
		add(americano);
		JButton banila = new JButton("<HTML><body style='text-align:center'>바닐라라떼(R)<br>4500</body></HTML>");
		banila.setBounds(715, 125, 160, 100);
		banila.setBackground(new Color(0xfafeff));
		banila.setFont(new Font("System",Font.BOLD,17));
		add(banila);
		JButton cafelatte = new JButton("<HTML><body style='text-align:center'>카페라떼(R)<br>4000</body></HTML>");
		cafelatte.setBounds(890, 125, 160, 100);
		cafelatte.setBackground(new Color(0xfafeff));
		cafelatte.setFont(new Font("System",Font.BOLD,17));
		add(cafelatte);
		JButton moca = new JButton("<HTML><body style='text-align:center'>카페모카(R)<br>4300</body></HTML>");
		moca.setBounds(1065, 125, 160, 100);
		moca.setBackground(new Color(0xfafeff));
		moca.setFont(new Font("System",Font.BOLD,17));
		add(moca);
		JButton up = new JButton("<HTML><body style='text-align:left'>△</body></HTML>");
		up.setBounds(1240, 125, 35, 100);
		up.setBackground(new Color(0xb0e8f7));
		up.setFont(new Font("System",Font.BOLD,10));
		add(up);
		///////////////////////////////////////
		JButton iceame = new JButton("<HTML><body style='text-align:center'>아이스<br>아메리카노(R)<br>3500</body></HTML>");
		iceame.setBounds(540, 240, 160, 100);
		iceame.setBackground(new Color(0xfafeff));
		iceame.setFont(new Font("System",Font.BOLD,17));
		add(iceame);
		JButton icebana = new JButton("<HTML><body style='text-align:center'>아이스<br>바닐라라떼(R)<br>4500</body></HTML>");
		icebana.setBounds(715, 240, 160, 100);
		icebana.setBackground(new Color(0xfafeff));
		icebana.setFont(new Font("System",Font.BOLD,17));
		add(icebana);
		JButton cafelatteL = new JButton("<HTML><body style='text-align:center'>카페라떼(L)<br>4500</body></HTML>");
		cafelatteL.setBounds(890, 240, 160, 100);
		cafelatteL.setBackground(new Color(0xfafeff));
		cafelatteL.setFont(new Font("System",Font.BOLD,17));
		add(cafelatteL);
		JButton mocaL = new JButton("<HTML><body style='text-align:center'>카페모카(L)<br>4300</body></HTML>");
		mocaL.setBounds(1065, 240, 160, 100);
		mocaL.setBackground(new Color(0xfafeff));
		mocaL.setFont(new Font("System",Font.BOLD,17));
		add(mocaL);
		JButton down = new JButton("<HTML><body style='text-align:left'>▽</body></HTML>");
		down.setBounds(1240, 240, 35, 100);
		down.setBackground(new Color(0xb0e8f7));
		down.setFont(new Font("System",Font.BOLD,10));
		add(down);
		///////////////////////////////////////
		JButton coldblue = new JButton("<HTML><body style='text-align:center'>콜드브루<br>4000</body></HTML>");
		coldblue.setBounds(540, 355, 160, 100);
		coldblue.setBackground(new Color(0xfafeff));
		coldblue.setFont(new Font("System",Font.BOLD,17));
		add(coldblue);
		JButton coldbluelt = new JButton("<HTML><body style='text-align:center'>콜드브루라떼(R)<br>4500</body></HTML>");
		coldbluelt.setBounds(715, 355, 160, 100);
		coldbluelt.setBackground(new Color(0xfafeff));
		coldbluelt.setFont(new Font("System",Font.BOLD,17));
		add(coldbluelt);
		JButton syrup = new JButton("<HTML><body style='text-align:center'>시럽추가<br>500</body></HTML>");
		syrup.setBounds(890, 355, 160, 100);
		syrup.setBackground(new Color(0xfafeff));
		syrup.setFont(new Font("System",Font.BOLD,17));
		add(syrup);
		JButton cup = new JButton("<HTML><body style='text-align:center'>개인컵<br>-300</body></HTML>");
		cup.setBounds(1065, 355, 160, 100);
		cup.setBackground(new Color(0xfafeff));
		cup.setFont(new Font("System",Font.BOLD,17));
		add(cup);
		
		///////////////////////////////////////
		JButton receipt = new JButton("<HTML><body style='text-align:center'>영수증<br>관리</body></HTML>");
		receipt.setBounds(540, 535, 160, 160);
		receipt.setBackground(new Color(0xe2d4fc));
		receipt.setFont(new Font("System",Font.BOLD,25));
		add(receipt);
		JButton membership = new JButton("<HTML><body style='text-align:center'>맴버쉽</body></HTML>");
		membership.setBounds(715, 535, 160, 160);
		membership.setBackground(new Color(0xe3aada));
		membership.setFont(new Font("System",Font.BOLD,25));
		add(membership);
		JButton card = new JButton("<HTML><body style='text-align:center'>신용카드</body></HTML>");
		card.setBounds(890, 535, 160, 160);
		card.setBackground(new Color(0xb5f5c8));
		card.setFont(new Font("System",Font.BOLD,25));
		add(card);
		JButton bill = new JButton("<HTML><body style='text-align:center'>현금</body></HTML>");
		bill.setBounds(1065, 535, 160, 160);
		bill.setBackground(new Color(0xfff7e0));
		bill.setFont(new Font("System",Font.BOLD,25));
		add(bill);		

		
		cof.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
	}
	
	
	public static void main(String[] args) {
		
		MainMenu m = new MainMenu();
		m.setDefaultOptions();
		/*
	 	setLocation(new Point(2020, 100));
		setSize(new Dimension(1300, 800)); //650 , 400
		 */
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
