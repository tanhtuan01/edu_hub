package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.School;

public interface IndustryRepository extends JpaRepository<Industry, Long>{

	List<Industry> findBySchool(School school, Sort sort);
	
	Page<Industry> findBySchool(School school, Pageable pageable);

}
