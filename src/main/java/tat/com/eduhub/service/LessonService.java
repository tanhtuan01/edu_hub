package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;

public interface LessonService {

	List<Lesson> listByCourses(Courses courses);
	
	Lesson save(Lesson lesson);
	
	boolean existsByCoursesAndId(Courses courses, Long idLesson);
	
	void delete(Long id);
	
	Lesson get(Long id);
}
