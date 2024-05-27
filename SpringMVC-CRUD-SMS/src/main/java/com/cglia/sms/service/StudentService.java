package com.cglia.sms.service;

import java.util.List;

import com.cglia.sms.model.Student;

public interface StudentService {
	
	public Integer saveStd(Student student);

	public Student getStdById(Integer id);

	public int updateStd(Student student);

	public int deleteStdById(Integer id);

	public List<Student> getAllStudent();
}
