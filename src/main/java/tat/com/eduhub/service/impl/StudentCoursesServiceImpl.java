package tat.com.eduhub.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.StudentCoursesRepository;
import tat.com.eduhub.service.StudentCoursesService;

@Service
public class StudentCoursesServiceImpl implements StudentCoursesService{

	@Autowired
	private StudentCoursesRepository repository;
	
	@Override
	public boolean existsByCoursesAndUser(Courses courses, User user) {
		// TODO Auto-generated method stub
		return repository.existsByCoursesAndUser(courses, user);
	}
	
	@Override
	public StudentCourses save(StudentCourses studentCourses) {
		// TODO Auto-generated method stub
		return repository.save(studentCourses);
	}

	@Override
	public List<StudentCourses> findByUser(User user) {
		// TODO Auto-generated method stub
		return repository.findByUser(user);
	}
	
	@Override
	public List<StudentCourses> findByCourses(Courses courses) {
		// TODO Auto-generated method stub
		return repository.findByCourses(courses);
	}
	
	@Override
	public List<StudentCourses> findByCoursesReversed(Courses courses) {
		// TODO Auto-generated method stub
		List<StudentCourses> studentCourses = repository.findByCourses(courses);
		studentCourses.sort(Comparator.comparing(StudentCourses::getId).reversed());
		return studentCourses;
	}
}
