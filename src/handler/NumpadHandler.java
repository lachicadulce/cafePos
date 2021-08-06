package handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class NumpadHandler implements ActionListener {
	
	JTextArea numpadText;
	
	public NumpadHandler(JTextArea numpadText) {
		this.numpadText = numpadText;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = numpadText.getText() + e.getActionCommand();
		numpadText.setText(str);
	}

}
