package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class NumpadBackSpaceActionListener implements ActionListener {

	JTextArea numpadText;
	
	public NumpadBackSpaceActionListener(JTextArea numpadText) {
		this.numpadText = numpadText;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int len = numpadText.getText().length();
		String num = numpadText.getText();
		String finnum = "";
		char[] numChar = num.toCharArray();
		for(int i = 0; i < len-1 ; i++) {
			finnum += numChar[i];
		}
		numpadText.setText(finnum);

	}

}
