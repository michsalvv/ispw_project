package logic.controller;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.exceptions.DatePriorTodayException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Exam;
import logic.model.dao.ExamDAO;
import logic.utilities.DateUtils;

public class ScheduleExamController {

	public boolean scheduleExam(ExamBean examBean) throws DatePriorTodayException {
		
		DateUtils.checkPriorDate(examBean.getDate());
		
		CourseBean courseBean = examBean.getCourse();
		Course course = new Course(courseBean.getName(), courseBean.getAbbreviation(), courseBean.getYear(),
									courseBean.getSemester(), courseBean.getCredits(), courseBean.getPrerequisites(),
									courseBean.getGoal(), courseBean.getReception());
		
		ClassroomBean classroomBean = examBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		return ExamDAO.insertExam(exam);
	}
}
