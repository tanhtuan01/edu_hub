package tat.com.eduhub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tat.com.eduhub.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long>{

	@Query("select s from School s where s.status = 'is_not_active'")
	Page<School> pageListSchoolIsNotActive(Pageable pageable);
	
}
