package sql_t;

import sql_c.SqlCont;

public class test {
	public static void main(String[] args) {
		SqlCont.login();
		System.out.println(SqlCont.ConsObject(1235, "ジャガイモ", "2021-01-04", 7));
	}
}
