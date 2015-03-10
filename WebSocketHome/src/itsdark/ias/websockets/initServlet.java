/**
 * 
 */
package itsdark.ias.websockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yash
 *
 */
@WebServlet("/initServlet")
public class initServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2268108357367651563L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("isInvalid", "no");
		request.setAttribute("firstTime", "yes");
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(
				request, response);
	}
}
