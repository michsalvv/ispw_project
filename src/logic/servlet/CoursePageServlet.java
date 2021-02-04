package logic.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.bean.WeeklyLessonBean;
import logic.controller.CourseController;
import logic.exceptions.RecordNotFoundException;

@WebServlet("/CoursePageServlet")
public class CoursePageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("loggedUser") == null) {
	        response.sendRedirect("/ispw_project/LoginServlet"); // Not logged in, redirect to login page.
	        return;
		}
		
		String c = request.getParameter("course");
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(c);
		
		CourseController controller = new CourseController();
		
		CourseBean course = null;
		LessonBean lesson = null;
		List<ProfessorBean> professors = null;
		List<WeeklyLessonBean> weeklyLessons = null;
		
		try {
			course = controller.getCourse(courseBean);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (RecordNotFoundException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		try {
			lesson = controller.getNextLesson(courseBean);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			//e.printStackTrace();
		}
		
		try {
			professors = controller.getCourseProfessors(courseBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
		}
		
		try {
			weeklyLessons = controller.getWeeklyLessons(courseBean);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
		}
		
		request.setAttribute("courseBean", course);
		request.setAttribute("lesson", lesson);
		request.setAttribute("listOfProfessor", professors);
		request.setAttribute("listOfWeekly", weeklyLessons);
		request.getRequestDispatcher("/WEB-INF/CoursePage.jsp").forward(request, response);
	}
}