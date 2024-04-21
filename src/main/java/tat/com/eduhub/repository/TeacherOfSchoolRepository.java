package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;

public interface TeacherOfSchoolRepository extends JpaRepository<TeacherOfSchool, Long>{

	boolean existsByUserAndSchoolAndIsAdminTrue(User user, School school);
	
	Page<TeacherOfSchool> findBySchoolAndIsAdminFalse(School school, Pageable pageable);
	
	List<TeacherOfSchool> findBySchoolAndIsAdminFalse(School school);
	
	boolean existsByUserAndSchoolAndIsAdminFalse(User user, School school);
	
	TeacherOfSchool findByUser(User user);
}
