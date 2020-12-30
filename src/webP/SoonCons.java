package webP;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sql_c.SqlCont;

/**
 * Servlet implementation class SoonCons
 */
@WebServlet("/SoonCons")
public class SoonCons extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoonCons() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");response.setContentType("text/html; charset=UTF-8");

		if(SqlCont.con==null)SqlCont.login();
		else {
			try {
				if(SqlCont.con.isClosed()==false)SqlCont.login();
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				SqlCont.login();
			}
		}
		ResultSet rs=SqlCont.SoonCons();

		PrintWriter out=response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>消費期限が近い品物</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>消費期限が近い品物</h1>");
		//表をsqlから作成(from)
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<td>品名</td>");
		out.println("<td>残り日数</td>");
		out.println("</tr>");

		try {
			while(rs.next()) {
				out.println("<tr>");
				out.println("<td>"+rs.getString(1)+"</td>");
				out.println("<td>"+rs.getInt(2)+"</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック

		}
		out.println("</table>");
		//表をsqlから作成(until)
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
