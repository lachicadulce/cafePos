package manager_file;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import baseSettings.DBConnector;
import baseSettings.InsertDB;

public class MenuDialog extends JDialog {

	/*
	 	# 메뉴 추가 팝업 창
	 		- 메뉴 이름, 가격, 분류 입력 후 추가 버튼을 누르면 menu 테이블에 추가.
	 		- display_order는 분류의 마지막 번호 + 1로 추가.
	 		- 분류가 추가되면 콤보박스에도 새로 추가, display_order는 1로 추가.
	 */
	
	JTextField tf_name = new JTextField(20);
	JTextField tf_price = new JTextField(20);
	JTextField tf_type = new JTextField(10);
	
	ArrayList<String> types;
	
	JComboBox<String> cb_type;
	JButton btn_add = new JButton("추가");
	JButton btn_can = new JButton("취소");
	
	
	public MenuDialog(JFrame frame, String title) {		
		super(frame, title);

		types = new ArrayList();
		selTypes();
		cb_type = new JComboBox<String>(types.toArray(new String[types.size()]));
		cb_type.setPreferredSize(new Dimension(100, 20));
//		btn_add.setSize(new Dimension(150, 20));
		
		setLayout(new FlowLayout());
		add(new JLabel("이름"));
		add(tf_name);
		add(new JLabel("가격"));
		add(tf_price);
		add(new JLabel("분류"));
		add(cb_type);
		add(tf_type);
		add(btn_add);
		add(btn_can);
		setSize(270, 160);
		setLocation(100, 100);
		
		btn_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메뉴 이름, 가격 미입력 시 리턴.
				if(tf_name.getText().equals("") || tf_price.getText().equals("")) {
					System.out.println("입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다.");
					return;
				}

				InsertDB insert = new InsertDB();
				// 콤보 박스의 인덱스에 맞춰서 분류 이름 가져오기.
				String type_name;
				// 분류를 추가한 경우
				if(cb_type.getSelectedIndex() == cb_type.getItemCount() - 1) {
					type_name = tf_type.getText();
					if(type_name.equals("")) {
						System.out.println("입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다.");
						return;
					}
					insert.dbinsert("INSERT INTO menu VALUES((SELECT MAX(menu_no) + 1 FROM menu), '" + tf_name.getText() + "', " + tf_price.getText() + ", '" + type_name + "', 1, 'Y')");
					
					// 분류 새로 가져오기.
					types.clear();
					selTypes();
					cb_type.removeAllItems();
					for(String type : types) {
						cb_type.addItem(type);
					}
				} else { // 분류 추가 없이 기존 분류에 추가한 경우
					type_name = types.get(cb_type.getSelectedIndex());
					insert.dbinsert("INSERT INTO menu VALUES((SELECT MAX(menu_no) + 1 FROM menu), '" + tf_name.getText() + "', " + tf_price.getText() + ", '" + type_name + "', (SELECT MAX(display_order) + 1 FROM menu WHERE type = '" + type_name + "'), 'Y')");
				}
				
				// 초기화
				tf_name.setText("");
				tf_price.setText("");
				tf_type.setText("");
				cb_type.setSelectedIndex(0);
			}
		});
		
		btn_can.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf_name.setText("");
				tf_price.setText("");
				cb_type.setSelectedIndex(0);
				setVisible(false);
			}
		});
	}
	
	// 분류 db에서 가져오기.
	private void selTypes() {
		String sql = "SELECT DISTINCT type FROM menu";
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				types.add(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    types.add("추가 (기입)");
	}
	
}
