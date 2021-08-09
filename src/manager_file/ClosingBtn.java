package manager_file;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// 마감 용지 출력 액션 리스너 설정
public class ClosingBtn implements ActionListener {
	
	public ClosingBtn() {
		super();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane end = new JOptionPane();				
		Font f1 = new Font("", Font.BOLD, 15);
		UIManager.put("OptionPane.messageFont", f1);
		UIManager.put("OptionPane.minimumSize",new Dimension(200,100)); 
		end.showMessageDialog(null, "마감 용지 출력이 완료되었습니다.", "마감 용지 출력", JOptionPane.PLAIN_MESSAGE);
	}
}
