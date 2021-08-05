package receipt;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import baseSettings.PosFrame;

public class Receipt extends PosFrame {
	public Receipt() {
		super();
		super.setTitle("영수증 관리");
		
		setLayout(null);
		
		// 시간을 문자열로 변경하기
		DateTimeFormatter my_date_format = 
				DateTimeFormatter.ofPattern("y년 M월 d일");
		
		String message = my_date_format.format(LocalDate.now());

		JButton date = new JButton(message);
		
		String string = 
				"<html><center>-------------------------------------------------------------------"
				+ "<br>*정부방침에 의해 교환/환불은 반드시 영수증을"
				+ "<br>지참하셔야 하며, 카드결제는 30일(09월08일)"
				+ "<br>이내 카드와 영수증 지참 시 가능합니다."
				+ "<br>-------------------------------------------------------------------"
				+ "</center>"
				+ ""
				+ "<table style='width:100%;'>"
				+ "<tr>"
				+ "<td>합계수량/금액</td>"
				+ "<td style='text-align:center;'>1</td>"
				+ "<td style='text-align:right;'>3000원</td>"
				+ "</tr>"
				+ "</table>"
				+ ""
				+ ""
				+ ""
				+ "<br>-------------------------------------------------------------------"
				+ "<br>과세매출"
				+ ""
				+ "<br>부가세"
				+ ""
				+ "<br>합&emsp계"
				+ ""
				+ "<br>현&emsp금"
				+ ""
				+ "</html>";
		
		
		
		// &nbsp 띄어쓰기
		// <html> +  + <br> + + <p>  + + </html> 줄 바꾸는 법
		
		JLabel receipt = new JLabel(string);
		
		ArrayList<JButton> buttons = new ArrayList<>();
		
		
		
		buttons.add(new JButton("전표반품")); // 0 
		buttons.add(new JButton("재인쇄"));
		buttons.add(new JButton("현금영수증"));
		
		buttons.add(new JButton("결제변경"));
		buttons.add(new JButton("전체"));
		buttons.add(new JButton("현금"));
		buttons.add(new JButton("신용카드")); // 6
		
		// 버튼 위치 조정
		buttons.get(0).setBounds(20, 30, 150, 80);
		buttons.get(1).setBounds(190, 30, 150, 80);
		buttons.get(2).setBounds(360, 30, 150, 80);
		buttons.get(3).setBounds(530, 30, 150, 80);
		
		buttons.get(4).setBounds(870, 30, 120, 80);
		buttons.get(5).setBounds(1010, 30, 120, 80);
		buttons.get(6).setBounds(1150, 30, 120, 80);
		
		for (int i = 0; i < buttons.size(); ++i) {
			buttons.get(i).setFont(new Font("MV Bold", Font.BOLD, 20));
		}
		
		date.setBounds(730, 30, 120, 80);
		date.setBackground(new Color(0xffffff));
		
		receipt.setFont(new Font("MV Bold", Font.BOLD, 20));
		receipt.setBounds(730, 140, 540, 500);
		receipt.setOpaque(true); // 백그라운드 색상 허용
		receipt.setBackground(new Color(0xffffff));
		receipt.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 표시 설정
		receipt.setVerticalAlignment(JLabel.NORTH);; // 세로 가운데 표시 설정
		
		
		// 좌측 위 버튼 추가
		add(buttons.get(0));
		add(buttons.get(1));
		add(buttons.get(2));
		add(buttons.get(3));
		
		add(date); // 날짜 출력
		
		// 우측 위 버튼
		add(buttons.get(4));
		add(buttons.get(5));
		add(buttons.get(6));
		
        add(receipt);

	}
	
	public static void main(String[] args) {
		 Receipt frame = new Receipt();	
		 frame.setDefaultOptions();
	}
}
