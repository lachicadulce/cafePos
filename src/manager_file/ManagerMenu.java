package manager_file;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import baseSettings.DBConnector;
import baseSettings.PosFrame;

public class ManagerMenu extends PosFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	private String sql = "SELECT * FROM menu ORDER BY type, display_order";
	private JTable tb;
	private DefaultTableModel model;
	private JButton selBtn = new JButton("조회");
	private JButton addBtn = new JButton("추가");
	private String header[] = {"NO", "이름", "가격", "분류", "노출순서"};
	private String selStr;
	private JTextField tf;
	
	private MenuDialog addMenuDialog;
	
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 화면 구성
	private void init() {
		addMenuDialog = new MenuDialog(this, "Menu Add");
		model = new DefaultTableModel(header, 0);

		tb = new JTable(model);
		tb.setFont(new Font("", Font.PLAIN, 14));
		JTableHeader tbheader = tb.getTableHeader();
		tbheader.setFont(new Font("", Font.PLAIN, 15));
		TableColumnModel colModel = tb.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(40);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel.getColumn(3).setPreferredWidth(30);
	
		scrollpane = new JScrollPane(tb);
		
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		JLabel lb = new JLabel("메뉴 이름");
		tf = new JTextField(20);
		
		lb.setSize(WIDTH, HEIGHT);
		
		// p3에 검색 라인, 조회버튼 추가
		p3.add(lb);
		p3.add(tf);
		p3.add(selBtn);
		p3.add(addBtn);
		
		selBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selStr = tf.getText();
				sql = "SELECT * FROM menu WHERE LOWER(mname) LIKE LOWER('%" + selStr + "%') ORDER BY type, display_order";
				setTB();
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addMenuDialog.setVisible(true);
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

		Manager_Btns mb = new Manager_Btns();
		for (JButton btns : mb.getJBtns()) {
			p2.add(btns);
		}
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
	public static void main(String[] args) {
		ManagerMenu mp = new ManagerMenu();
		mp.setDefaultOptions();
	}
}
