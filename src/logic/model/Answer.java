package logic.model;

import java.sql.Date;

public class Answer {
	private int id;
	private String text;
	private Student student;
	private Date date;

	public Answer(int id, String text, Student student, Date date) {
		this.id = id;
		this.text = text;
		this.student = student;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}