package tat.com.eduhub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;

public interface TeacherOfSchoolRepository extends JpaRepository<TeacherOfSchool, Long>{

	boolean existsByUserAndSchoolAndIsAdminTrue(User user, School school);
	
	Page<TeacherOfSchool> findBySchoolAndIsAdminFalse(School school, Pageable pageable);
}
