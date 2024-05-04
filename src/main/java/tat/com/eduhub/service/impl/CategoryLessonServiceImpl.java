package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.repository.CategoryLessonRepository;
import tat.com.eduhub.service.CategoryLessonService;

@Service
public class CategoryLessonServiceImpl implements CategoryLessonService{

	@Autowired
	private CategoryLessonRepository repository;
	
	@Override
	public List<CategoryLesson> listByCourses(Courses courses) {
		// TODO Auto-generated method stub
		return repository.findByCourses(courses);
	}
	
	@Override
	public CategoryLesson save(CategoryLesson categoryLesson) {
		// TODO Auto-generated method stub
		return repository.save(categoryLesson);
	}
	
	@Override
	public boolean existsByCoursesAndId(Courses courses, Long idCategoryLesson) {
		// TODO Auto-generated method stub
		return repository.existsByCoursesAndIdEquals(courses, idCategoryLesson);
	}
}
