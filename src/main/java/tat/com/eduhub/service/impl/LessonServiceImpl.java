package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.repository.LessonRepository;
import tat.com.eduhub.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService{

	@Autowired
	private LessonRepository repository;
	
	@Override
	public List<Lesson> listByCourses(Courses courses) {
		// TODO Auto-generated method stub
		return repository.findByCourses(courses);
	}
	
	@Override
	public Lesson save(Lesson lesson) {
		// TODO Auto-generated method stub
		return repository.save(lesson);
	}
	
	@Override
	public boolean existsByCoursesAndId(Courses courses, Long idLesson) {
		// TODO Auto-generated method stub
		return repository.existsByCoursesAndIdEquals(courses, idLesson);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public Lesson get(Long id) {
		// TODO Auto-generated method stub
		Optional<Lesson> optional = repository.findById(id);
		return optional.orElse(null);
	}
}
