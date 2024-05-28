package publicDataTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	public static Connection makeConnection() {
		String filePath = "D:/doitjava/publicSchoolData/src/db.properties";
		Properties properties = new Properties();
		Connection con = null;
		try {
			properties.load(new FileReader(filePath));
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			// JDBC 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// DB 연결
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 연결 실패");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("db.properties 연결실패");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("db.properties 연결실패");
		} 
		return con; 
	}
}
