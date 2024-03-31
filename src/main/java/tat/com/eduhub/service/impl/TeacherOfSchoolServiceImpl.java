package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.repository.TeacherOfSchoolRepository;
import tat.com.eduhub.service.TeacherOfSchoolService;

@Service
public class TeacherOfSchoolServiceImpl implements TeacherOfSchoolService{

	@Autowired
	private TeacherOfSchoolRepository repository;
	
	@Override
	public TeacherOfSchool save(TeacherOfSchool teacherOfSchool) {
		// TODO Auto-generated method stub
		return repository.save(teacherOfSchool);
	}
}