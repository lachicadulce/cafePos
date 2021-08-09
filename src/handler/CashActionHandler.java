package handler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CashActionHandler implements ActionListener {
	
	Frame[] frames;

	public CashActionHandler(Frame[] frames) {
		this.frames = frames;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JDialog dialog = null;
		
        String[] commands = {"1", "2", "3", "4","5", "6", "7", "8", "9", "0", ">", "E"};

    	JPanel panel = new JPanel();
		panel.add(new JLabel("받은 현금을 입력해 주세요"));
		JTextField textField = new JTextField(10);
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
		
        dialog = op.createDialog(null, "현금을 입력해 주세요");
        dialog.setVisible(true);
		


	}

}
