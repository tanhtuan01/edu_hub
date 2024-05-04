package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;

public interface CoursesService {

	Courses save(Courses courses);
	
	List<Courses> findByAuthor(User user);
	
	void delete(Long id);
	
	Courses get(Long id);
	
	boolean checkCoursesWithUser(User user, Long id);
}
