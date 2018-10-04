package Package1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	HashMap<String,String> data = new HashMap<String,String>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<body>");
				out.println();
				for(Map.Entry<String, String> entry : data.entrySet()) {
					out.println(entry.getKey() + " -> " + entry.getValue());
					out.println("<br>\r\n");
				}
				out.println("<form method=\"post\">\r\n");
				out.println("input name  : \r\n");
				out.println("<input type=\"text\" name=\"key\">\r\n");
				out.println("<br>\r\n");
				out.println("input number: \r\n");
				out.println("<input type=\"text\" name=\"value\">\r\n");
				out.println("<br><br>\r\n");
				out.println("<input type=\"submit\" value=\"Submit\">\r\n");
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Enumeration nums = request.getParameterNames();
		while(nums.hasMoreElements()) {
			Object elem = nums.nextElement();
			String val = request.getParameter(elem.toString());		
			Object elem2 = nums.nextElement();
			String key = request.getParameter(elem2.toString());
			data.put(val,key);
		}
		doGet(request,response);
		
	}

}
