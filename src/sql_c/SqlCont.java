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

	static Statement st=null;
	public static void login() {
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/example?characterEncoding=UTF-8&serverTimezone=JST", "root", "KILLsqlMe0228jkjk");
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	public static void showdatabase() {
		ResultSet rs = null;
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
	public static void gettables() {
		String sql = "show tables;";
		ResultSet rs = null;
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
	public static ResultSet GetBuyLog() {
		String sql = "select 品名.品名,購入日,個数 from 購入,品名 where 購入.品名=品名.品名 order by 購入日;";
		ResultSet rs = null;
		try {
			rs=st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	public static ResultSet GetZaiko() {
		String sql = "select 品名.品名,SUM(個数) as 在庫 from 購入,品名 where 購入.品名=品名.品名 group by 品名.品名;";
		ResultSet rs = null;
		try {
			rs=st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
		return rs;
	}
}
