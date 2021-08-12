package receipt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
	public static void main(String[] args) {
		
		// 1. Class.forName으로 jdbc 드라이버를 로드한다.
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 이름 틀림");
            System.out.println("해당 라이브러리를 찾을 수 없음");
        }

        // ============================================================================================================
        // ============================================================================================================

        // 2. DriverManager 클래스를 통해 DB에 연결한다.
        try {
//            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "hr", "1234");
            Connection conn = DriverManager.getConnection(
            		"jdbc:oracle:thin:@database-1.cxc98ia1oha4.us-east-2.rds.amazonaws.com:1521/ORCL",
            		"cafe",
            		"!!22Qorthdud");

            String sql = "select * from coffee";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("ID\tNAME\t\tPRICE");
            System.out.println("=====================");
            while (rs.next()) {
                System.out.print(rs.getInt("ID") + "\t");
                System.out.print(rs.getString("NAME") + "\t\t");
                System.out.print(rs.getInt("PRICE"));
                System.out.println();
            }

            // 6. 다 사용한 연결을 나중에 연 순서대로 닫아준다
            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("getConnection 하다가 문제 생김");
        }

//        System.out.println("main 끝");


    }



	
}
