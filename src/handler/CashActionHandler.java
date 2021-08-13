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
	int cashMoney;
	int cardMoney;
	boolean cashReceipt;
	int cashReceiptCheck;

	
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

		// 현금 결제 입력 받기
		while(true) {
			dialog = op.createDialog(null, "현금 결제");
			dialog.setVisible(true);


			// 닫기 버튼 눌렸을때
			if(op.getValue() == null) {
				dialog.dispose();
				break;
				
			// E 버튼 눌렸을때
			} else if (op.getValue() == "E" ) {
				
				calcTable.setValueAt(textField.getText(), 2, 1);
				cashMoney = Integer.parseInt((String) calcTable.getValueAt(2, 1));
				
				input = "";
				dialog.dispose();
				
				if(cashMoney > 0 ) {					
					cashReceiptCheck = JOptionPane.showOptionDialog(null, "현금영수증 하시나요", "근태관리", 0, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(cashReceiptCheck == 0) {
						cashReceipt = true;
					} else {
						cashReceipt = false;
					}
				}

				break;
				
			// 숫자 인지 확인후 추가
			} else if (isNumeric((String)op.getValue())) {
				input += op.getValue();
				textField.setText(input);
				
			// 취소버튼 눌렀을때 한자리수 지우기
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
		
		// 카드 결제 입력 받기
		label.setText("카드 결제 금액을 입력해주세요");
		while(true) {
			dialog = op.createDialog(null, "카드 결제");
			dialog.setVisible(true);


			if(op.getValue() == null) {
				dialog.dispose();
				break;
			} else if (op.getValue() == "E" ) {
				
				cashMoney = Integer.parseInt((String) calcTable.getValueAt(2, 1));
				cardMoney = Integer.parseInt((String)textField.getText());
				calcTable.setValueAt(""+(cardMoney+cashMoney), 2, 1);
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
		
	
		
		
		
		
		


	} // end AL
	public static boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

}

