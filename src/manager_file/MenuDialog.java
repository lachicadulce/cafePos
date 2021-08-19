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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import baseSettings.DBConnector;
import baseSettings.InsertDB;

public class MenuDialog extends JDialog {

	/*
	 	# 메뉴 추가 팝업 창
	 		- 메뉴 이름, 가격, 분류 입력 후 추가 버튼을 누르면 menu 테이블에 insert.
	 		- display_order는 분류의 마지막 번호 + 1로 추가.
	 		- 분류가 추가되면 콤보박스에도 새로 추가, display_order는 1로 추가.
	 		- TODO: 추가 잘됐다는 확인 창.
	 		
	 	# 메뉴 수정 팝업 창
	 		- 메뉴 이름, 가격, 분류 입력 후 수정 버튼을 누르면 menu 테이블 update.
	 		- 수정 완료 시 팝업창이 사라짐.
	 		- TODO: 노출순서 분류내부에서 겹치면 에러 띄우기.
	 		
	 	TODO: 추가/수정 확인 창.
	 */
	
	JTextField tf_name = new JTextField(20);
	JTextField tf_price = new JTextField(20);
	JTextField tf_display_order = new JTextField(15);
	JTextField tf_type = new JTextField(10);
	
	ArrayList<String> types;
	
	JComboBox<String> cb_type;
	JButton btn_ok = new JButton("추가");
	JButton btn_can = new JButton("취소");
	
	
	public MenuDialog(JFrame frame, String title, JButton selBtn, JPanel panel) {		
		super(frame, title);

		types = new ArrayList();
		selTypes();
		cb_type = new JComboBox<String>(types.toArray(new String[types.size()]));
		cb_type.setPreferredSize(new Dimension(100, 20));
//		btn_ok.setSize(new Dimension(150, 20));
		
		setLayout(new FlowLayout());
		add(new JLabel("이름"));
		add(tf_name);
		add(new JLabel("가격"));
		add(tf_price);
		add(new JLabel("분류"));
		add(cb_type);
		add(tf_type);
		add(btn_ok);
		add(btn_can);
		setSize(270, 160);
		setLocation(100, 100);
		setResizable(false);
		setModal(true);
		
		btn_ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메뉴 이름, 가격 미입력 시 리턴.
				if(tf_name.getText().equals("") || tf_price.getText().equals("") || !tf_price.getText().matches("\\d+")) {
//					System.out.println("입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다.");
					JOptionPane.showMessageDialog(frame, "입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다.");
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
					if(JOptionPane.showConfirmDialog(panel, "정말 추가 하시겠습니까?", "추가", 0) == 0) {
						insert.dbinsert("INSERT INTO menu VALUES((SELECT MAX(menu_no) + 1 FROM menu), '" + tf_name.getText() + "', " + tf_price.getText() + ", '" + type_name + "', 1, 'Y')");
						
						// 분류 새로 가져오기.
						types.clear();
						selTypes();
						cb_type.removeAllItems();
						for(String type : types) {
							cb_type.addItem(type);
						}
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
				
				selBtn.doClick();
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
	
	public MenuDialog(String menu_no, String menu_name, String menu_price, String menu_type, String menu_display_order, JButton selBtn, JPanel panel) {
		super();
		
		types = new ArrayList();
		selTypes();
		cb_type = new JComboBox<String>(types.toArray(new String[types.size()]));
		cb_type.setPreferredSize(new Dimension(100, 20));
		btn_ok.setText("수정");
		
		// 가져온 값 넣어주기.
		tf_name.setText(menu_name);
		tf_price.setText(menu_price);
		for(int i = 0; i < types.size(); i++) {
			if(types.get(i).equals(menu_type)) {
				cb_type.setSelectedIndex(i);
				break;
			}
		}
		tf_display_order.setText(menu_display_order);
		
		setLayout(new FlowLayout());
		add(new JLabel("이름"));
		add(tf_name);
		add(new JLabel("가격"));
		add(tf_price);
		add(new JLabel("분류"));
		add(cb_type);
		add(tf_type);
		add(new JLabel("노출순서"));
		add(tf_display_order);
		add(btn_ok);
		add(btn_can);
		setSize(270, 170);
		setLocation(100, 100);
		setResizable(false);
		
		btn_ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 메뉴 이름, 가격 미입력 시 리턴.
				if(tf_name.getText().equals("") || tf_price.getText().equals("")) {
					System.out.println("입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다.");
					return;
				}

				if(JOptionPane.showConfirmDialog(panel, "정말 수정 하시겠습니까?", "수정", 0) == 0) {
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
					} else { // 분류 추가 없이 기존 분류를 선택한 경우
						type_name = types.get(cb_type.getSelectedIndex());
					}
					insert.dbinsert("UPDATE menu SET mname = '" + tf_name.getText() + "', price = " + tf_price.getText() + ", type = '" + type_name + "', display_order = " + tf_display_order.getText() + " WHERE menu_no = " + menu_no);
					selBtn.doClick();
					dispose();
				}
			}
		});
		
		btn_can.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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
