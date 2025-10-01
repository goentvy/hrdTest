package entvy.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	// DB 접속 정보
	static final String DB_URL = "jdbc:mariadb://localhost:3306/hrdtest";
	static final String USER = "root";
	static final String PASSWORD = "9785";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			System.out.println("----- MariaDB 연결 성공 -----");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("===== DB 연결 실패 =====");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
