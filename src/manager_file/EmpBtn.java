package manager_file;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import baseSettings.*;

public class EmpBtn extends JDialog implements ActionListener {
	// 직원등록 텍스트필드
	JTextField emp_name = new JTextField(20);
	JTextField emp_tel = new JTextField(20);
	
	// 직원등급 리스트 저장 리스트
	ArrayList<String> position;
	
	// 저장된 직원등급 콤보박스
	JComboBox<String> cb_pos;
	JButton btn_add = new JButton("등록");
	JButton btn_can = new JButton("취소");
	
	public EmpBtn() {
		super();
		
		position = new ArrayList();
		// 직원등급 불러오기
		selTypes();
		// 직원등급을 콤보박스에 추가
		cb_pos = new JComboBox<String>(position.toArray(new String[position.size()]));
		cb_pos.setPreferredSize(new Dimension(210, 20));
		
		setTitle("직원 등록");
		setLayout(new FlowLayout());
		add(new JLabel("이름"));
		add(emp_name);
		add(new JLabel("Tel"));
		add(emp_tel);
		add(new JLabel("직급"));
		add(cb_pos);
		add(btn_add);
		add(btn_can);
		setResizable(false); // 창 크기 조절X
		setModal(true);
		// 화면 정중앙에 출력되도록 설정
		setSize(270, 160);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		
		// 직원 등록 버튼을 누르면 등록팝업창 출력
		btn_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 한글, 영문 이름, 휴대폰 형식에 대한 정규식
				String regHan = "^[가-힣]+";
				String regEng = "^[a-zA-Z\\ ]*";
				String regtel = "010-?[2-9]\\d{3}-?\\d{4}";
				String r_name;
				r_name = position.get(cb_pos.getSelectedIndex());
				if (emp_name.getText().equals("") || emp_tel.getText().equals("") || 
						!emp_name.getText().matches(regEng) && !emp_name.getText().matches(regHan) 
							|| !emp_tel.getText().matches(regtel)) {
					System.out.println("입력하신 정보가 올바르지 않습니다. 다시 확인해 주시기 바랍니다. "
							+ "\n▷ 한글, 영문 이름만 사용가능. \n▷ 휴대폰 번호에 '-' 포함 입력");
					return;	
				}
				InsertDB insert = new InsertDB();
				if(JOptionPane.showConfirmDialog(null, "정말 추가 하시겠습니까?", "추가", 0) == 0) {
					insert.dbinsert("INSERT INTO employees_info "
							+ "VALUES((SELECT MAX(emp_no) + 1 FROM employees_info), "
							+ "'" + emp_name.getText() + "', '" + emp_tel.getText() + "', "
							+ "TO_DATE(sysdate + 1, 'YY-MM-DD hh24:mi:ss'), " + "'" + r_name + "')");
					JOptionPane.showMessageDialog(null, "직원 등록을 완료했습니다.", "등록", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "직원 등록을 취소했습니다.", "취소", JOptionPane.OK_OPTION);
				}
				// 초기화
				emp_name.setText("");
				emp_tel.setText("");
				cb_pos.setSelectedIndex(0);
			}
		});
		btn_can.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				emp_name.setText("");
				emp_tel.setText("");
				cb_pos.setSelectedIndex(0);
				setVisible(false);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(true);
	}

	private void selTypes() {
		String sql = "SELECT DISTINCT emp_degree FROM employees_info";
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	
			while(rs.next()) {
				position.add(rs.getString("emp_degree"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
