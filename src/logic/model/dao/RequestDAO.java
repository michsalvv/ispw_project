package logic.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Request;
import logic.model.Student;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class RequestDAO {

	private RequestDAO() {
		
	}
	
	public static Request getRequest(String student, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Request request;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequest(stmt, student, course);

			if (!rs.first()) {
				throw new RecordNotFoundException("No request found");
				
			} else {
				rs.first();
				Student s = StudentDAO.findStudentByUsername(rs.getString("student"));
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				request = new Request(s, c);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return request;
	}
	
	public static List<Request> getRequestsByProfessor(String professor) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Request> requests;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequestsByProfessor(stmt, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException("No request found");

			} else {
				requests = new ArrayList<>();
				rs.first();
				do {
					Student student = StudentDAO.findStudentByUsername(rs.getString("student"));
					Course course = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Request request = new Request(student, course);
					requests.add(request);
				} while (rs.next());
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return requests;
	}
	
	public static List<Request> getRequestsByProfessorAndCourse(String professor, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Request> requests;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectRequestsByProfessorAndCourse(stmt, professor, course);

			if (!rs.first()) {
				throw new RecordNotFoundException("No request found");
				
			} else {
				requests = new ArrayList<>();
				rs.first();
				do {
					Student s = StudentDAO.findStudentByUsername(rs.getString("student"));
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Request request = new Request(s, c);
					requests.add(request);
				} while (rs.next());
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return requests;
	}
	
	public static void insertRequest(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.insertRequest(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void deleteRequest(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;

		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.deleteRequest(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void insertFollow(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
		
		conn = (SingletonDB.getDbInstance()).getConnection();
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
		
		Queries.insertFollow(stmt, student.getUsername(), course.getAbbreviation());
	}
	
	public static void deleteFollow(Request request) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;

		conn = (SingletonDB.getDbInstance()).getConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
		Student student = request.getStudent();
		Course course = request.getCourse();
			
		Queries.deleteFollow(stmt, student.getUsername(), course.getAbbreviation());
	}
}
