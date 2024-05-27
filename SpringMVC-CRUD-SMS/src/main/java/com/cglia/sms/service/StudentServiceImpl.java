package com.cglia.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglia.sms.dao.StudentDao;
import com.cglia.sms.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao dao;

	@Override
	public Integer saveStd(Student student) {
		Integer id = dao.save(student);
		return id;
	}

	@Override
	public Student getStdById(Integer id) {
		Student std = dao.getById(id);
		return std;
	}

	@Override
	public int updateStd(Student student) {
		int count = dao.update(student);
		return count;
	}

	@Override
	public int deleteStdById(Integer id) {
		int count = dao.deleteById(id);
		return count;
	}

	@Override
	public List<Student> getAllStudent() {
		List<Student> empList = dao.getAll();
		return empList;
	}

}
