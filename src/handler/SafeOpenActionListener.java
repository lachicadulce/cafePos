package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

// 환전 버튼 액션 리스너
// 누르면 팝업창으로 "금고가 열렸습니다" 나옴

public class SafeOpenActionListener implements ActionListener {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "금고가 열렸습니다.", "알림", 1);

	}

}
