package logic.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Exam;
import logic.utilities.Queries;
import logic.utilities.SingletonDB;

public class ExamDAO {

	private ExamDAO() {
		
	}
	
	public static Exam getExamByDateAndTime(Date date, Time time) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		Exam exam;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectExam(stmt, date, time);

			if (!rs.first()) {
				throw new RecordNotFoundException("No exam found");
				
			} else {
				rs.first();
				Date d = rs.getDate("date");
				Time t = rs.getTime("time");
				Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
				Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
				String note = rs.getString("note");
				exam = new Exam(d, t, c, cl, note);
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return exam;
	}
	
	
	public static List<Exam> getExamsByCourse(Date date, Time time, String course) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;
		
		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectExamsByCourse(stmt, date, time, course);

			if (!rs.first()) {
				throw new RecordNotFoundException("No exam found");
				
			} else {
				exams = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					String n = rs.getString("note");
					Exam exam = new Exam(d, t, c, cl, n);
					exams.add(exam);
				} while (rs.next());
			}
			rs.close();
			
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
		return exams;
	}
	
	
	public static List<Exam> getNextExams(Date date, Time time) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExams(stmt, date, time);

			if (!rs.first()) {
				throw new RecordNotFoundException("No exam found");
				
			} else {
				exams = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					String n = rs.getString("note");
					Exam exam = new Exam(d, t, c, cl, n);
					exams.add(exam);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}
	
	public static List<Exam> getNextExamsStudent(Date date, String student) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExamsByStudent(stmt, date, student);

			if (!rs.first()) {
				throw new RecordNotFoundException("No exam found");
				
			} else {
				exams = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					String n = rs.getString("note");
					Exam exam = new Exam(d, t, c, cl, n);
					exams.add(exam);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}
	
	
	public static List<Exam> getNextExamsProfessor(Date date, String professor) throws SQLException, RecordNotFoundException {
		
		Statement stmt = null;
		Connection conn = null;
		List<Exam> exams;

		try {
			conn = SingletonDB.getDbInstance().getConnection();
			if (conn == null) {
				throw new SQLException();
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = Queries.selectNextExamsByProfessor(stmt, date, professor);

			if (!rs.first()) {
				throw new RecordNotFoundException("No exam found");

			} else {
				exams = new ArrayList<>();
				rs.first();
				do {
					Date d = rs.getDate("date");
					Time t = rs.getTime("time");
					Course c = CourseDAO.getCourseByAbbrevation(rs.getString("course"));
					Classroom cl = ClassroomDAO.getClassroomByName(rs.getString("classroom"));
					String n = rs.getString("note");
					Exam exam = new Exam(d, t, c, cl, n);
					exams.add(exam);
				} while (rs.next());
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return exams;
	}
	
	
	public static boolean insertExam(Exam exam) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = (SingletonDB.getDbInstance()).getConnection();
			if (conn == null) {
				throw new SQLException();
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Date date = exam.getDate();
			Time time = exam.getTime();
			Course course = exam.getCourse();
			Classroom classroom = exam.getClassroom();
			String note = exam.getNote();
			
			Queries.insertExam(stmt, date, time, course.getAbbrevation(), classroom.getName(), note);
			
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
}
