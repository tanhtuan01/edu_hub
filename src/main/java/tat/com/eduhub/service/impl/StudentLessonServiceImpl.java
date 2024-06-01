package tat.com.eduhub.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.StudentLessons;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.StudentLessonRepository;
import tat.com.eduhub.service.StudentLessonService;

@Service
public class StudentLessonServiceImpl implements StudentLessonService{

	@Autowired
	private StudentLessonRepository repository;
	
	@Override
	public boolean existsByUserAndCoursesAndLesson(User user, Courses courses, Lesson lesson) {
		// TODO Auto-generated method stub
		return repository.existsByUserAndCoursesAndLesson(user, courses, lesson);
	}
	
	@Override
	public StudentLessons save(StudentLessons studentLessons) {
		// TODO Auto-generated method stub
		return repository.save(studentLessons);
	}
	
	@Override
	public StudentLessons get(Long id) {
		// TODO Auto-generated method stub
		Optional<StudentLessons> optional = repository.findById(id);
		return optional.orElse(null);
	}
}
