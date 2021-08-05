import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class ManagerPage extends JFrame {
	
	private JSplitPane jsp = new JSplitPane();
	private JScrollPane scrollpane;
	
	public ManagerPage() {
		super();
		setTB();
		init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 800);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = this.getSize();
        int xpos = (int)(screen.getWidth() / 2 - frm.getWidth() / 2);
        int ypos = (int)(screen.getHeight() / 2 - frm.getHeight() / 2);
        setLocation(xpos, ypos);
              
		setVisible(true);
	}
	
	// table 생성 및 컬럼 사이즈 조정
	private void setTB() {
		
		// TODO: DB에서 값 받아와서 뿌리기
		String header[] = {"No", "사번", "이름", "직위", "출근시간", "퇴근시간", "근무시간", "근무시작일"};
		String contents[][] = {
				{"1", "0001", "백길동", "사장", "2021-08-02 17:00", "2021-08-02 17:00", "02:00", "2021-07-02"},
				{"2", "0002", "강요한", "점장", "2021-08-02 15:10", "2021-08-02 17:00", "01:50", "2021-07-02"},
				{"3", "0003", "빈센조", "부점장", "2021-08-02 09:00", "2021-08-02 15:00", "06:00", "2021-07-02"}
		};
		
		JTable tb = new JTable(contents, header);
		tb.setFont(new Font("", Font.PLAIN, 10));
		JTableHeader tbheader = tb.getTableHeader();
		tbheader.setFont(new Font("", Font.PLAIN, 15));
//		tbheader.setPreferredSize(new Dimension(tbheader.getWidth(), 30));
		TableColumnModel colModel = tb.getColumnModel();
		colModel.getColumn(0).setPreferredWidth(40);
		colModel.getColumn(1).setPreferredWidth(50);
		colModel.getColumn(2).setPreferredWidth(50);
		colModel.getColumn(3).setPreferredWidth(50);
		colModel.getColumn(4).setPreferredWidth(150);
		colModel.getColumn(5).setPreferredWidth(150);
		colModel.getColumn(6).setPreferredWidth(100);
		colModel.getColumn(7).setPreferredWidth(100);
	
		scrollpane = new JScrollPane(tb);
	}
	
	// 화면 구성.. 나눠서 했어야하는데 한곳에 때려넣었어요..
	private void init() {
		jsp.setResizeWeight(0.9);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		JPanel p1  = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		JPanel p3 = new JPanel(new FlowLayout());
		
		// 달력 출력
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel);

		// p3에 달력 ~ 달력 조회버튼 추가
		p3.add(datePicker);
		p3.add(new JLabel("~"));
		p3.add(datePicker2);
		p3.add(new JButton("조회"));
		
		p1.add(p3, BorderLayout.NORTH);
		p1.add(scrollpane, BorderLayout.CENTER);
//		p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("근태기록"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		p1.setBorder(null);
		
		// 왼쪽 구성요소 추가
		jsp.setLeftComponent(p1);
		
		ArrayList<JButton> btns = new ArrayList<>();
		btns.add(new JButton("매출 현황"));
		btns.add(new JButton("마감 용지 출력"));
		btns.add(new JButton("직원 등록"));
		btns.add(new JButton("출퇴근 기록 열람"));
		btns.add(new JButton("메뉴 관리"));
		for(JButton btn : btns) {
			// Mac에서도 똑같이 작용하기 위한 버튼 색상 변경을 위한 설정
//			btn.setOpaque(true);
//		    btn.setBorderPainted(false);
			
//		    btn.setBackground(new Color(0x66CCFF));
			p2.add(btn);
		}
//		btns.get(3).setBackground(new Color(0x0066CC));
		
		// 오른쪽 구성요소 추가
		jsp.setRightComponent(p2);
		con.add("Center", jsp);
	}
	
	public static void main(String[] args) {
		ManagerPage mp = new ManagerPage();
	}
}
