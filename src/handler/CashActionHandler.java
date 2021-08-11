package handler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

// 금액 계산 버튼 (현금 + 카드 합침, 현금 먼저 입력)

public class CashActionHandler implements ActionListener {

	JTextField textField;
	JTable calcTable;


	
	public CashActionHandler(JTable calcTable) {
		super();
		this.calcTable = calcTable;
	}



	@Override
	public void actionPerformed(ActionEvent e) {

		JDialog dialog = null;

		String[] commands = {"1", "2", "3", "4","5", "6", "7", "8", "9", "0", ">", "E"};
		String input = "";

		JPanel panel = new JPanel();
		JLabel label = new JLabel("받은 현금을 입력해 주세요");
		panel.add(label);
		textField = new JTextField(10);
		panel.add(textField);

		JOptionPane op = new JOptionPane
				(
						panel, // Prompt message
						JOptionPane.QUESTION_MESSAGE, // Message type
						JOptionPane.YES_NO_CANCEL_OPTION, // Option type
						null, // Icon
						commands, // List of commands
						commands[commands.length - 1]
						);

		List<JButton> buttons = SwingUtils.getDescendantsOfType(JButton.class, op, true);
		Container parent = buttons.get(0).getParent();
		parent.setLayout( new GridLayout(4, 0, 5, 5) );
		dialog = op.createDialog(null, "");

		while(true) {
			dialog = op.createDialog(null, "현금 결제");
			dialog.setVisible(true);
			System.out.println(op.getValue());


			if(op.getValue() == null) {
				dialog.dispose();
				break;
			} else if (op.getValue() == "E" ) {
				calcTable.setValueAt(textField.getText(), 2, 1);
				input = "";
				dialog.dispose();
				break;
			} else if (isNumeric((String)op.getValue())) {
				input += op.getValue();
				textField.setText(input);
			} else if (op.getValue() == ">") {
				int len = textField.getText().length();
				String num = textField.getText();
				String finnum = "";
				char[] numChar = num.toCharArray();
				for(int i = 0; i < len-1 ; i++) {
					finnum += numChar[i];
				}
				input = finnum;
				textField.setText(input);
			}

		}
		
		label.setText("카드 결제 금액을 입력해주세요");
		
		
		while(true) {
			dialog = op.createDialog(null, "카드 결제");
			dialog.setVisible(true);
			System.out.println(op.getValue());

			if(op.getValue() == null) {
				dialog.dispose();
				break;
			} else if (op.getValue() == "E" ) {
				
				int cash = Integer.parseInt((String) calcTable.getValueAt(2, 1));
				int card = Integer.parseInt((String)textField.getText());
				calcTable.setValueAt(""+(card+cash), 2, 1);
				dialog.dispose();
				break;
			} else if (isNumeric((String)op.getValue())) {
				input += op.getValue();
				textField.setText(input);
				
			} else if (op.getValue() == ">") {
				int len = textField.getText().length();
				String num = textField.getText();
				String finnum = "";
				char[] numChar = num.toCharArray();
				for(int i = 0; i < len-1 ; i++) {
					finnum += numChar[i];
				}
				input = finnum;
				textField.setText(input);
			}

		}



	}
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}

