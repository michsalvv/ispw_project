package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/RequestPageServlet")
public class RequestPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (request.getSession().getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
	
		AcceptRequestController controller = new AcceptRequestController();
		
		List<RequestBean> requests = null;
		List<CourseBean> courses = null;
		
		try {
			requests = controller.getRequests((UserBean) request.getSession().getAttribute("loggedUser"));

		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
			
		} catch (RecordNotFoundException e) {
			requests = new ArrayList<>();
		}
		
		
		try {
			courses = controller.getCourses((UserBean) request.getSession().getAttribute("loggedUser"));
		
		} catch (SQLException e) {
			request.setAttribute("alertMsg", "An error as occured. Try later.");
			request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
			return;
		
		} catch (RecordNotFoundException e) {
			courses = new ArrayList<>();
		}

		session.setAttribute("listOfRequest", requests);
		session.setAttribute("listOfCourse", courses);
		request.getRequestDispatcher("/WEB-INF/RequestPage.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AcceptRequestController controller = new AcceptRequestController();
		
		String course = request.getParameter("course");
		String student = request.getParameter("student");
		
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(course);
		
		StudentBean studentBean = new StudentBean();
		studentBean.setUsername(student);
		
		RequestBean requestBean = new RequestBean();
		requestBean.setCourse(courseBean);
		requestBean.setStudent(studentBean);
		
		if (request.getParameter("btnAccept") != null) {
			try {
				controller.acceptRequest(requestBean);
				
			} catch (SQLException e) {
				request.setAttribute("alertMsg", "An error as occured. Try later.");
				request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
				return;
			}
		}
		
		else if (request.getParameter("btnDecline") != null) {	
			try {
				controller.declineRequest(requestBean);
				
			} catch (SQLException e) {
				request.setAttribute("alertMsg", "An error as occured. Try later.");
				request.getRequestDispatcher("/WEB-INF/LoginPage.jsp").forward(request, response);
				return;
			}
		}
		
		response.sendRedirect("/ispw_project/RequestPageServlet");
	}
}
