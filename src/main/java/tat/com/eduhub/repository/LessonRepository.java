package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.Courses;


public interface LessonRepository extends JpaRepository<Lesson, Long>{

	List<Lesson> findByCourses(Courses courses);
	
	boolean existsByCoursesAndIdEquals(Courses courses, Long idLesson);
}
