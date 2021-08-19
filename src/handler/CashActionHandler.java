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
		textField.setText("0");
		panel.add(textField);
		
		int total = Integer.parseInt((String)calcTable.getValueAt(0, 1));
		int sale = 0;
        try {
            sale = Integer.parseInt((String)calcTable.getValueAt(1, 1));
        }
        catch (Exception e2) {
            sale = 0;
        }

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

		System.out.println(e.getActionCommand());
		// 현금 결제 입력 받기
		if( e.getActionCommand() != null) {

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
						cashReceiptCheck = JOptionPane.showOptionDialog(null, "현금영수증 하시나요", "현금영수증 확인", 0, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if(cashReceiptCheck == 0) {// 영수증한다의 yes == 0 / cancel == 1 / 
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
		
			
			int mustRecevie = total - sale - cashMoney;
			if(e.getActionCommand() != null) { // 팝업 닫기,취소 버튼 안눌러진 상황일때.
				if( mustRecevie > 0) {
					
					// 카드 결제 입력 받기
					label.setText("카드 결제 금액을 입력해주세요");
					textField.setText(Integer.toString(mustRecevie));

					while(true) {
						dialog = op.createDialog(null, "카드 결제");
						dialog.setVisible(true);
						

						if(op.getValue() == null) { // 결제팝업창에서 X버튼눌렀을때
							dialog.dispose(); // 결제팝업창 끄기.
							break;
						} else if (op.getValue() == "E" ) {
							
							cardMoney = Integer.parseInt((String)textField.getText());
							calcTable.setValueAt(""+(cardMoney+cashMoney), 2, 1);
							JOptionPane.showMessageDialog(null, "결제가 완료되었습니다.","Message",JOptionPane.INFORMATION_MESSAGE);
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
			}
		}



		int change = total - sale - cashMoney - cardMoney;


		if(change <= 0)  {
			change = Math.abs(change); //잔돈 거스름을 주기위한 과정
		} 

		String changestr = Integer.toString(change);
		calcTable.setValueAt(changestr, 3, 1); // 거스름돈 금액
		
		
		

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

