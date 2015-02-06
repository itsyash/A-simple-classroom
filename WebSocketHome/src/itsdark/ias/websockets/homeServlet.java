package itsdark.ias.websockets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class homeServlet
 */
@WebServlet(name = "home", urlPatterns = { "/homeServlet" })
public class homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String loginType = request.getParameter("loginType");
		if (username == null || password == null || loginType == null) {
			System.out.println("invalid login in servlet");
			request.setAttribute("isInvalid", "yes");
			request.setAttribute("firstTime", "no");
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
			return;
		}
		System.out.println("homeServlet" + username + " " + password + " "
				+ loginType);
		if (loginType.equals("professor")) {
			if (username.equals(Globals.profUsername)
					&& password.equals(Globals.profPassword)) {
				getServletContext().getRequestDispatcher(
						"/WEB-INF/professor.jsp").forward(request, response);
				return;
			} else {
				System.out.println("invalid professor login in servlet");
				request.setAttribute("isInvalid", "yes");
				request.setAttribute("firstTime", "no");
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
						.forward(request, response);
				return;
			}
		}
		// authenticate student here
		boolean authenticate = true;
		if (authenticate) {
			if (Globals.classGoing) {
				getServletContext()
						.getRequestDispatcher("/WEB-INF/student.jsp").forward(
								request, response);
				return;
			} else {

				PrintWriter out = response.getWriter();
				try {
					
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Class Not going on</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<p>Class Not going on</p>");
					out.println("</body>");
					out.println("</html>");
				} finally {
					out.close();
				}
			}
		} else {
			// invalid credentials
			System.out.println("invalid student login in servlet");
			request.setAttribute("isInvalid", "yes");
			request.setAttribute("firstTime", "no");
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
					.forward(request, response);
			return;
		}
	}

}
