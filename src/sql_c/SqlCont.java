package sql_c;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlCont {
	static Connection con = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;
	static Statement st=null;
	public static void login() {
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306?characterEncoding=UTF-8&serverTimezone=JST", "root", "KILLsqlMe0228jkjk");
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	public static void showdatabase() {

		String sql = "show databases;";
		try {
			rs=st.executeQuery(sql);
			while(rs.next()) {
			String location = rs.getString(1);
			System.out.println
			(location);
		}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
