package manager_file;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import baseSettings.InsertDB;

public class MenuDialog extends JDialog {

	JTextField tf_name = new JTextField(20);
	JTextField tf_price = new JTextField(20);
	
	String types[] = {"coffee", "tea", "MD"};
	
	JComboBox<String> cb_type = new JComboBox<String>(types);
	JButton btn_add = new JButton("추가");
	
	public MenuDialog(JFrame frame, String title) {
		
		super(frame, title);
		setLayout(new FlowLayout());
		add(new JLabel("이름"));
		add(tf_name);
		add(new JLabel("가격"));
		add(tf_price);
		add(new JLabel("분류"));
		cb_type.setPreferredSize(new Dimension(210, 20));
		add(cb_type);
		btn_add.setSize(new Dimension(150, 20));
		add(btn_add);
		setSize(270, 200);
		
		btn_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("name: " + tf_name.getText() + ", price: " + tf_price.getText() + ", type: " + cb_type.getSelectedIndex());
				String type_name;
				if(cb_type.getSelectedIndex() == 0) type_name = "coffee";
				else type_name = "drink";
				InsertDB insert = new InsertDB();
				insert.dbinsert("INSERT INTO menu VALUES((SELECT MAX(menu_no) + 1 FROM menu), '" + tf_name.getText() + "', " + tf_price.getText() + ", '" + type_name + "', (SELECT MAX(display_order) + 1 FROM menu WHERE type = '" + type_name + "'), 'Y')");
				tf_name.setText("");
				tf_price.setText("");
				cb_type.setSelectedIndex(0);
			}
		});
	}
}
