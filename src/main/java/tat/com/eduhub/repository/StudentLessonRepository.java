package tat.com.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.StudentLessons;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.entity.Lesson;


public interface StudentLessonRepository extends JpaRepository<StudentLessons, Long>{

	boolean existsByUserAndCoursesAndLesson(User user, Courses courses, Lesson lesson);
	
}
