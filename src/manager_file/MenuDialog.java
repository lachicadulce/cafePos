package manager_file;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MenuDialog extends JDialog {

	JTextField tf_name = new JTextField();
	JTextField tf_price = new JTextField();
	
	String types[] = {"coffee", "tea", "MD"};
	
	JComboBox<String> cb_type = new JComboBox<String>(types);
	JButton btn_add = new JButton("추가");
	
	public MenuDialog(JFrame frame, String title) {
		super(frame, title);
		setLayout(new FlowLayout());
		add(tf_name);
		add(tf_price);
		add(cb_type);
		add(btn_add);
		setSize(200, 400);
		
		btn_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("name: " + tf_name.getText() + ", price: " + tf_price.getText() + ", type: " + cb_type.getSelectedIndex());
			}
		});
	}
}
