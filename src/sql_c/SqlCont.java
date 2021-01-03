package sql_c;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlCont {
	public static Connection con = null;
	public static PreparedStatement stmt = null;

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
	public static void ChooseBuy(PrintWriter out,String link) {
		String sql = "select count(distinct 分類) from 品名;";
		ResultSet rs = null;
		try {
			rs=st.executeQuery(sql);
			rs.next();
			int cn = rs.getInt(1);
			rs=st.executeQuery("select 品名.分類 from 品名 group by 分類;");
			String str[]=new String[cn];
			List<String> Obj[] = new ArrayList[cn];
			for(int i=0;i<cn;i++) {
				Obj[i] = new ArrayList<String>();
				rs.next();
				str[i] = rs.getString(1);
			}
			rs=st.executeQuery("select 品名.分類,品名.品名 from 品名;");
			while(rs.next()) {
				String cl=rs.getString(1);
				String na=rs.getString(2);

				for(int i=0;i<cn;i++) {
					if(cl.equals(str[i])) {
						Obj[i].add(na);
					}
				}
			}
			out.println("<script type=\"text/javascript\">\r\n" +
					"// ▼HTMLの読み込み直後に実行：\r\n" +
					"document.addEventListener('DOMContentLoaded', function() {\r\n" +
					"\r\n" +
					"   // ▼2階層目の要素を全て非表示にする\r\n" +
					"   var allSubBoxes = document.getElementsByClassName(\"subbox\");\r\n" +
					"   for( var i=0 ; i<allSubBoxes.length ; i++) {\r\n" +
					"      allSubBoxes[i].style.display = 'none';\r\n" +
					"   }\r\n" +
					"   // ▼全てのプルダウンメニューセットごとに処理\r\n" +
					"   var mainBoxes = document.getElementsByClassName('pulldownset');\r\n" +
					"   for( var i=0 ; i<mainBoxes.length ; i++) {\r\n" +
					"   \r\n" +
					"      var mainSelect = mainBoxes[i].getElementsByClassName(\"mainselect\");   // 1階層目(メイン)のプルダウンメニュー（※後でvalue属性値を参照するので、select要素である必要があります。）\r\n" +
					"      mainSelect[0].onchange = function () {\r\n" +
					"         // ▼同じ親要素に含まれているすべての2階層目(サブ)要素を消す\r\n" +
					"         var subBox = this.parentNode.getElementsByClassName(\"subbox\");   // 同じ親要素に含まれる.subbox（※select要素に限らず、どんな要素でも構いません。）\r\n" +
					"         for( var j=0 ; j<subBox.length ; j++) {\r\n" +
					"            subBox[j].style.display = 'none';\r\n" +
					"         }\r\n" +
					"   \r\n" +
					"         // ▼指定された2階層目(サブ)要素だけを表示する\r\n" +
					"         if( this.value ) {\r\n" +
					"            var targetSub = document.getElementById( this.value );   // 「1階層目のプルダウンメニューで選択されている項目のvalue属性値」と同じ文字列をid属性値に持つ要素を得る\r\n" +
					"            targetSub.style.display = 'inline';\r\n" +
					"         }\r\n" +
					"      }\r\n" +
					"   \r\n" +
					"   }\r\n" +
					"\r\n" +
					"});\r\n" +
					"</script>");


			out.println("<form action=\"/Inventory/"+link+"\" method=\"get\">");
			out.println("ID：<input type=\"text\" name=\"id\" value=\""+ID_isuue()+"\"><br><br>");
			out.println("<div class=\"pulldownset\">");
			out.println("分類を選択してください");
			out.println("	<select class=\"mainselect\" name=\"cla\">");
			out.println("	<option value=\"\">選択してください</option>");
			for(int i=0;i<Obj.length;i++) {
				out.println("		<option value=\""+str[i]+"\">"+str[i]+"</option>");
			}
			out.println("</select><br>");
			out.println("<br>品名を選択してください");
			for(int j=0;j<Obj.length;j++) {
				out.println("<select id=\""+str[j]+"\" name=\"shi_"+str[j]+"\" class=\"subbox\">");
				out.println("	<option value=\"\">選択してください</option>");
				for(int i = 0; i < Obj[j].size(); i++) {
					out.println("	<option value=\""+Obj[j].get(i)+"\">"+Obj[j].get(i)+"</option>");
				}
				out.println("</select>");

			}
			out.println("<br><br>個数を入力してください");
			out.println("<input type=\"number\" name=\"num\"><br>");
			out.println("<br>購入日を選択してください");
			out.println("<input type=\"date\" name=\"dt\" value=\""+Get_date()+"\"><br>");
			out.println("<p><input type=\"submit\" value=\"送信\" id=\"send\">");
			out.println("</form>");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println("</div>");
	}

	public static void ChooseCons(PrintWriter out,String link) {
		String sql = "select count(distinct 品名.分類) from 消費状況,品名 where 品名.品名=消費状況.品名;";
		ResultSet rs = null;
		try {
			rs=st.executeQuery(sql);
			rs.next();
			int cn = rs.getInt(1);
			rs=st.executeQuery("select 品名.分類 from 消費状況,品名 where 品名.品名=消費状況.品名 group by 品名.分類;");
			String str[]=new String[cn];
			List<String> Obj[] = new ArrayList[cn];

			for(int i=0;i<cn;i++) {
				Obj[i] = new ArrayList<String>();
				rs.next();
				str[i] = rs.getString(1);
			}
			rs=st.executeQuery("select 品名.分類,品名.品名 from 消費状況,品名 where 品名.品名=消費状況.品名 group by 品名.品名;");
			while(rs.next()) {
				String cl=rs.getString(1);
				String na=rs.getString(2);

				for(int i=0;i<cn;i++) {
					if(cl.equals(str[i])) {
						Obj[i].add(na);
					}
				}
			}
			out.println("<script type=\"text/javascript\">\r\n" +
					"// ▼HTMLの読み込み直後に実行：\r\n" +
					"document.addEventListener('DOMContentLoaded', function() {\r\n" +
					"\r\n" +
					"   // ▼2階層目の要素を全て非表示にする\r\n" +
					"   var allSubBoxes = document.getElementsByClassName(\"subbox\");\r\n" +
					"   for( var i=0 ; i<allSubBoxes.length ; i++) {\r\n" +
					"      allSubBoxes[i].style.display = 'none';\r\n" +
					"   }\r\n" +
					"   // ▼全てのプルダウンメニューセットごとに処理\r\n" +
					"   var mainBoxes = document.getElementsByClassName('pulldownset');\r\n" +
					"   for( var i=0 ; i<mainBoxes.length ; i++) {\r\n" +
					"   \r\n" +
					"      var mainSelect = mainBoxes[i].getElementsByClassName(\"mainselect\");   // 1階層目(メイン)のプルダウンメニュー（※後でvalue属性値を参照するので、select要素である必要があります。）\r\n" +
					"      mainSelect[0].onchange = function () {\r\n" +
					"         // ▼同じ親要素に含まれているすべての2階層目(サブ)要素を消す\r\n" +
					"         var subBox = this.parentNode.getElementsByClassName(\"subbox\");   // 同じ親要素に含まれる.subbox（※select要素に限らず、どんな要素でも構いません。）\r\n" +
					"         for( var j=0 ; j<subBox.length ; j++) {\r\n" +
					"            subBox[j].style.display = 'none';\r\n" +
					"         }\r\n" +
					"   \r\n" +
					"         // ▼指定された2階層目(サブ)要素だけを表示する\r\n" +
					"         if( this.value ) {\r\n" +
					"            var targetSub = document.getElementById( this.value );   // 「1階層目のプルダウンメニューで選択されている項目のvalue属性値」と同じ文字列をid属性値に持つ要素を得る\r\n" +
					"            targetSub.style.display = 'inline';\r\n" +
					"         }\r\n" +
					"      }\r\n" +
					"   \r\n" +
					"   }\r\n" +
					"\r\n" +
					"});\r\n" +
					"</script>");

			out.println("<form action=\"/Inventory/"+link+"\" method=\"get\">");
			out.println("ID：<input type=\"text\" name=\"id\" value=\""+ID_isuue2()+"\"><br><br>");
			out.println("<div class=\"pulldownset\">");
			out.println("分類を選択してください");
			out.println("	<select class=\"mainselect\" name=\"cla\">");
			out.println("	<option value=\"\">選択してください</option>");
			for(int i=0;i<Obj.length;i++) {
				out.println("		<option value=\""+str[i]+"\">"+str[i]+"</option>");
			}
			out.println("</select><br>");
			out.println("<br>品名を選択してください");
			for(int j=0;j<Obj.length;j++) {
				out.println("<select id=\""+str[j]+"\" name=\"shi_"+str[j]+"\" class=\"subbox\">");
				out.println("	<option value=\"\">選択してください</option>");
				for(int i = 0; i < Obj[j].size(); i++) {
					out.println("	<option value=\""+Obj[j].get(i)+"\">"+Obj[j].get(i)+"</option>");
				}
				out.println("</select>");
			}



			out.println("<br><br>個数を入力してください");
			out.println("<input type=\"number\" name=\"num\"><br>");
			out.println("<br>消費日を選択してください");
			out.println("<input type=\"date\" name=\"dt\" value=\""+Get_date()+"\"><br><br>");
			out.println("<p><input type=\"submit\" value=\"送信\" id=\"send\">");
			out.println("</form>");


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		out.println("</div>");
	}
	public static void BuyObject(int id, String ob,String date,int n) {
		ResultSet rst=null;
		String sql1="select * from 購入履歴 where id ="+id+";";
		System.out.println(sql1);
		try {

			rst= st.executeQuery(sql1);

			if(rst.next()==true)return;
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		String sql = "insert into 購入履歴 values("+id+",\""+ob+"\",\""+date+"\","+n+");";
		System.out.println(sql);
		int rs = 0;
		try {
			rs=st.executeUpdate(sql);
			sql = "insert into 消費状況 values(\""+ob+"\",\""+date+"\","+n+");";
			rs=st.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
	public static String ConsObject(int id, String ob,String date,int n) {
		ResultSet rst=null;
		int num=n;
		System.out.println("開始");
		String sql1="select * from 消費履歴 where id ="+id+";";
		System.out.println(sql1);
		try {
			rst= st.executeQuery(sql1);
			if(rst.next()==true)
				return "すでに存在します";
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		try {
			rst=st.executeQuery("select * from 消費状況 where 品名=\""+ob+"\" order by 購入日;");
			con.setAutoCommit(false);
			int za;
			while(rst.next()) {
				za=rst.getInt(4);
				System.out.println(za+" : "+num);
				if(za<=num) {
					st.executeUpdate("delete from 消費状況 where id="+rst.getInt(1)+";");//削除
					num-=za;
				}else {
					st.executeUpdate("update 消費状況 set 個数 = "+(za-num)+" where id = "+rst.getInt(1)+";");//アップデート
					num=0;
					break;
				}
			}
			if(num!=0) {
				con.rollback();
				return "在庫が足りません。在庫を確認してください";
			}

			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return "何か起きた";
	}
	public static String Get_date() {
		Date dt=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd");
		return sdf.format(dt);
	}
	public static int ID_isuue() {
		int id=0;
		while(true) {
			id=Math.abs(new java.util.Random().nextInt());
			String sql = "select * from 購入履歴 where id="+id+";";
			ResultSet rs = null;
			try {
				rs=st.executeQuery(sql);
				if(rs.next()==false)
					break;
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return id;
	}
	public static int ID_isuue2() {
		int id=0;
		while(true) {
			id=Math.abs(new java.util.Random().nextInt());
			String sql = "select * from 消費履歴 where id="+id+";";
			ResultSet rs = null;
			try {
				rs=st.executeQuery(sql);
				if(rs.next()==false)
					break;
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return id;
	}
}
