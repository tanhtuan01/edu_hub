package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;

public interface CategoryLessonService {

	List<CategoryLesson> listByCourses(Courses courses);

	CategoryLesson save(CategoryLesson categoryLesson);
	
	boolean existsByCoursesAndId(Courses courses, Long idCategoryLesson);
	
	void delete(Long id);
	
	CategoryLesson get(Long id);
}
