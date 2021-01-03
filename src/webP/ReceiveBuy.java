package webP;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sql_c.SqlCont;

/**
 * Servlet implementation class ReceiveBuy
 */
@WebServlet("/ReceiveBuy")
public class ReceiveBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiveBuy() {
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
		String class_name=request.getParameter("cla");
		String inst_name=request.getParameter("shi_"+class_name);
		int number=Integer.parseInt(request.getParameter("num"));
		String date=request.getParameter("dt");
		int id=Integer.parseInt(request.getParameter("id"));

		PrintWriter out=response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>購入履歴の入力が完了しました</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>完了しました</h1>");
		out.println("分類は"+class_name+"<br>");
		out.println("品名は"+inst_name+"<br>");
		out.println("数は"+number+"<br>");
		out.println("id:"+id+"<br>");
		SqlCont.BuyObject(id,inst_name, date, number);

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
