package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;


public interface StudentCoursesRepository extends JpaRepository<StudentCourses, Long>{

	boolean existsByCoursesAndUser(Courses courses, User user);
	
	List<StudentCourses> findByUser(User user);
	
	List<StudentCourses> findByCourses(Courses courses);
}
