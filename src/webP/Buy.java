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
 * Servlet implementation class Buy
 */
@WebServlet("/Buy")
public class Buy extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Buy() {
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

		PrintWriter out=response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>購入</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>購入情報を入力してください</h1>");
		SqlCont.ChooseBuy(out,"ReceiveBuy");
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
