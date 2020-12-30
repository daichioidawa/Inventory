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
	public static ResultSet GetBuyLog() {//品名、購入日、個数
		String sql = "select 品名.品名,購入日,個数 from 購入履歴,品名 where 購入履歴.品名=品名.品名 order by 購入日;";
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
	public static ResultSet GetZaiko() {//品名、在庫
		String sql = "select 品名.品名,SUM(個数) as 在庫 from 消費状況,品名 where 消費状況.品名=品名.品名 group by 品名.品名;";
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
	public static ResultSet SoonBuy() {
		String sql = "select 品名.品名,品名.警告個数,sum(個数) as 合計 from 品名,消費状況 where 品名.品名=消費状況.品名 group by 品名.品名 having sum(個数)<=品名.警告個数;";
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

	public static ResultSet SoonCons() {
		String sql = "select 品名.品名,購入日+ interval 消費期限 day as 消費期限日\r\n" + 
				",個数\r\n" + 
				",DATEDIFF(購入日+ interval 消費期限 day,current_date()) as 期限日数\r\n" + 
				"from 消費状況,品名\r\n" + 
				"where 品名.品名=消費状況.品名 having 期限日数<=7;";
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
	public static void BuyObject(String ob,String date,int n) {
		
	}
}
