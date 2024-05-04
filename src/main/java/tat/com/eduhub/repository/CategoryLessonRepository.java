package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;

public interface CategoryLessonRepository extends JpaRepository<CategoryLesson, Long>{

	List<CategoryLesson> findByCourses(Courses courses);
	
	boolean existsByCoursesAndIdEquals(Courses courses, Long idCategoryLesson);
}
