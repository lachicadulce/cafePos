package manager_file;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import main_component.main_test;

// 관리자메뉴 버튼 설정
public class Manager_Btns {

	private ArrayList<JButton> btns;
	final public static int MAX_BUTTON = 6;
	
	public Manager_Btns(JFrame frame, int selected) {
		btns = new ArrayList<>();
		
		// 버튼 폰트, 배경색 설정
		for(int i = 0; i < MAX_BUTTON; i++) {
			JButton jb = new JButton();
			jb.setFont(new Font("default", Font.BOLD, 16));
			jb.setHorizontalAlignment(SwingConstants.LEFT); // 좌측 정렬되도록 설정
			btns.add(jb);
		}
		setBtnCol(selected);
		// 버튼 이미지아이콘 추가
		ImageIcon main = new ImageIcon("manager/main.png");
		ImageIcon total = new ImageIcon("manager/total.png");
		ImageIcon closing = new ImageIcon("manager/closing.png");
		ImageIcon emp = new ImageIcon("manager/emp.png");
		ImageIcon work = new ImageIcon("manager/work.png");
		ImageIcon menu = new ImageIcon("manager/menu.png");
		btns.get(0).setIcon(main);
		btns.get(1).setIcon(total);
		btns.get(2).setIcon(closing);
		btns.get(3).setIcon(emp);
		btns.get(4).setIcon(work);
		btns.get(5).setIcon(menu);
				
		// 버튼 이름 설정
		btns.get(0).setText("메인으로");
		btns.get(1).setText("매출 현황");
		btns.get(2).setText("마감 용지 출력");
		btns.get(3).setText("직원 등록");
		btns.get(4).setText("출퇴근 기록 열람");
		btns.get(5).setText("메뉴 관리");
	
		// get(1) : 마감용지출력, get(2) : 직원등록
		btns.get(2).addActionListener(new ClosingBtn());
		btns.get(3).addActionListener(new EmpBtn());
		
		// 메인화면 연결 필요
		btns.get(0).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main_test mt = new main_test();
				mt.setDefaultOptions();
				frame.dispose();
			}
		});
		
		btns.get(1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerSales ms = new ManagerSales();
				ms.setDefaultOptions();
				frame.dispose();
			}
		});
		
		btns.get(4).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerPage mp = new ManagerPage();
				mp.setDefaultOptions();
				frame.dispose();
			}
		});
		btns.get(5).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerMenu mm = new ManagerMenu();
				mm.setDefaultOptions();
				frame.dispose();
			}
		});

	}
	
	public void setBtnCol(int selectedIndex) {
		for(int i = 0; i < btns.size(); i++) {
			if(i != selectedIndex) {
				btns.get(i).setBackground(new Color(0xD7E7F7));
			}
		}
		btns.get(selectedIndex).setBackground(new Color(0xA9BCF5));
	}
	
	public ArrayList<JButton> getJBtns() {
		return btns;	
	}
}