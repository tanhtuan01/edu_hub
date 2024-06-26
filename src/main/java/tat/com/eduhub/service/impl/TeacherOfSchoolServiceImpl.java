package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;
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
	
	@Override
	public boolean existsByUserAndSchoolAdmin(User user, School school) {
		// TODO Auto-generated method stub
		return repository.existsByUserAndSchoolAndIsAdminTrue(user, school);
	}
	
	@Override
	public Page<TeacherOfSchool> pageTeacherOfSchool(School school, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndIsAdminFalse(school, pageable);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public List<TeacherOfSchool> listTeacherOfSchools(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndIsAdminFalse(school);
	}
	
	@Override
	public boolean existsByUserAndSchoolLecturer(User user, School school) {
		// TODO Auto-generated method stub
		return repository.existsByUserAndSchoolAndIsAdminFalse(user, school);
	}
	
	@Override
	public TeacherOfSchool findByUser(User user) {
		// TODO Auto-generated method stub
		return repository.findByUser(user);
	}
	
	@Override
	public TeacherOfSchool findByUserAdminSchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndIsAdminTrue(school);
	}
}
