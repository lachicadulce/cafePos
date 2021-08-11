package manager_file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuBtns {

	/*
	 	Menu 조회, 수정, 삭제 버튼을 때놓고 싶은 클래스....
	 */
	
	JButton selBtn; // 조회 버튼
	JButton delBtn; // 삭제 버튼
	JButton modBtn; // 수정 버튼
	
	public MenuBtns() {
		// 버튼 생성
		selBtn = new JButton("조회");
		delBtn = new JButton("삭제");
		modBtn = new JButton("수정");
		
		// 버튼 이벤트 추가
		selBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		modBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public JButton getDelBtn() {
		return delBtn;
	}
	
	public JButton getModBtn() {
		return modBtn;
	}
	
	public JButton getSelBtn() {
		return selBtn;
	}
}
