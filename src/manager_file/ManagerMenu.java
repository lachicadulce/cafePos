package manager_file;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import baseSettings.DBConnector;
import baseSettings.InsertDB;
import baseSettings.PosFrame;

public class ManagerMenu extends PosFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	private String sql = "SELECT * FROM menu ORDER BY type, display_order";
	private JTable tb;
	private DefaultTableModel model;
	private JButton selBtn = new JButton("조회");
	private JButton addBtn = new JButton("추가");
	private JButton modBtn = new JButton("수정");
	private JButton delBtn = new JButton("삭제");
	private String header[] = {"NO", "이름", "가격", "분류", "노출순서"};
	private String selStr;
	private JTextField tf_name;
	
	JComboBox<String> cb_type;
	
	private MenuDialog addMenuDialog;	
	final public static int MAX_BUTTON = 6;
	
	public ManagerMenu() {
		super();

		init();
		setTB();
	}
	
	// db에서 table에 띄울 데이타 가져오기.
	private void setTB() {
		// delete model
		while(model.getRowCount() > 0) {
			model.removeRow(0);
		}
	    try (
	    	Connection conn = DBConnector.getConnection();
	    	PreparedStatement pstmt = conn.prepareStatement(sql);
	    	ResultSet rs = pstmt.executeQuery();
	    	){
	    	if(rs.next()) {
				while(rs.next()) {
					int no = rs.getInt("menu_no");
					String name = rs.getString("mname");
					int price = rs.getInt("price");
					String type = rs.getString("type");
					String display_order = rs.getString("display_order");
	//				String stock = rs.getString("stock_chk");
					Object data[] = {no, name, price, type, display_order};
					model.addRow(data);
				}
	    	} else {
	    		JOptionPane.showMessageDialog(this, "조회 결과가 없습니다.");
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 화면 구성
	private void init() {
		addMenuDialog = new MenuDialog(this, "Menu Add", selBtn);
		model = new DefaultTableModel(header, 0);

		tb = new JTable(model);
		tb.setFont(new Font("", Font.PLAIN, 14));
		JTableHeader tbheader = tb.getTableHeader();
		tbheader.setFont(new Font("", Font.BOLD, 15));
		TableColumnModel colModel = tb.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(20);
		colModel.getColumn(1).setPreferredWidth(60);
		colModel.getColumn(2).setPreferredWidth(30);
		colModel.getColumn(3).setPreferredWidth(30);
		colModel.getColumn(4).setPreferredWidth(20);
	
		scrollpane = new JScrollPane(tb);
		
		tbheader.setBackground(new Color(0xEFF8FB)); // Header 컬러 설정
		jsp.setResizeWeight(1.0);
		jsp.setEnabled(false); // 테이블 <> 버튼 사이에 사이즈 조정 불가능하게 설정
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(MAX_BUTTON, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		JLabel lb = new JLabel("메뉴 이름");
		tf_name = new JTextField(20);
		cb_type = new JComboBox(addMenuDialog.types.toArray(new String[addMenuDialog.types.size()]));
		cb_type.removeItemAt(cb_type.getItemCount() - 1);
		cb_type.addItem("전체");
		cb_type.setSelectedIndex(cb_type.getItemCount() - 1);
//		lb.setSize(WIDTH, HEIGHT);
		
		// p3에 검색 라인, 조회, 추가, 수정, 삭제 버튼 추가
		p3.add(lb);
		p3.add(tf_name);
		p3.add(new JLabel("분류"));
		p3.add(cb_type);
		p3.add(selBtn);
		p3.add(addBtn);
		p3.add(modBtn);
		p3.add(delBtn);
		
		// 조회버튼 이벤트
		selBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selStr = tf_name.getText();
				int index = cb_type.getSelectedIndex();

				if(index != cb_type.getItemCount() - 1) {
					sql = "SELECT * FROM menu WHERE LOWER(mname) LIKE LOWER('%" + selStr + "%') AND type = '" + addMenuDialog.types.get(cb_type.getSelectedIndex()) + "' ORDER BY type, display_order";
				} else {
					sql = "SELECT * FROM menu WHERE LOWER(mname) LIKE LOWER('%" + selStr + "%') ORDER BY type, display_order";
				}
				setTB();
			}
		});
		
		// 추가버튼 이벤트
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addMenuDialog.setVisible(true);
			}
		});
		
		// 수정버튼 이벤트
		modBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int cnt = tb.getSelectedRowCount();
				if(cnt == 0) {
					JOptionPane.showMessageDialog(p1, "선택된 컬럼이 없습니다.");
				} else if(cnt == 1) {
					String menu_no = String.valueOf(model.getValueAt(tb.getSelectedRow(), 0));
					String menu_name = String.valueOf(model.getValueAt(tb.getSelectedRow(), 1));
					String menu_price = String.valueOf(model.getValueAt(tb.getSelectedRow(), 2));
					String menu_type = String.valueOf(model.getValueAt(tb.getSelectedRow(), 3));
					String menu_display_order = String.valueOf(model.getValueAt(tb.getSelectedRow(), 4));
					
					// 수정 화면 띄우기.
					MenuDialog mmd = new MenuDialog(menu_no, menu_name, menu_price, menu_type, menu_display_order, selBtn);
					mmd.setModal(true);
					mmd.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(p1, "하나의 컬럼만 선택해주세요.");					
				}
			}
		});
		
		// 삭제버튼 이벤트
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tb.getSelectedRowCount() > 0) {
					if(JOptionPane.showConfirmDialog(p1, "선택된 열들을 삭제하시겠습니까?", "삭제", 0) == 0) {
						InsertDB db = new InsertDB();
						for(int i : tb.getSelectedRows())
						{
							db.dbinsert("DELETE FROM menu WHERE menu_no = " + String.valueOf(model.getValueAt(i, 0)));
						}
						// 조회
						selBtn.doClick();
					}
				}
			}
		});
		
		p1.add(p3, BorderLayout.NORTH);
		p1.add(scrollpane, BorderLayout.CENTER);
		p1.setBorder(null);
		
		// 왼쪽 구성요소 추가
		jsp.setLeftComponent(p1);
		
			// Mac에서도 똑같이 작용하기 위한 버튼 색상 변경을 위한 설정
//			btn.setOpaque(true);
//		    btn.setBorderPainted(false);
			
//		    btn.setBackground(new Color(0x66CCFF));

		Manager_Btns mb = new Manager_Btns(this);
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
//	public static void main(String[] args) {
//		ManagerMenu mp = new ManagerMenu();
//		mp.setDefaultOptions();
//	}
}
