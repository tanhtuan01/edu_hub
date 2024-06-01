package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;

public interface StudentCoursesService {

	boolean existsByCoursesAndUser(Courses courses, User user);
	
	StudentCourses save(StudentCourses studentCourses);
	
	List<StudentCourses> findByUser(User user);
	
	List<StudentCourses> findByCourses(Courses courses);
	
	List<StudentCourses> findByCoursesReversed(Courses courses);
}
