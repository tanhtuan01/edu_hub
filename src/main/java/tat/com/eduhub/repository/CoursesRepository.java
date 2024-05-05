package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;

public interface CoursesRepository extends JpaRepository<Courses, Long>{

	List<Courses> findByUser(User user);
	
	boolean existsByUserAndIdEquals(User user, Long id);
	
	List<Courses> findByStatusAndTypeEquals(String status, String type);
	
	boolean existsByIdEqualsAndStatusEquals(Long idCourses, String status);
}
