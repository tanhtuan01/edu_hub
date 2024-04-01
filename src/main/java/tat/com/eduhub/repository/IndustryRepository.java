package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.School;

public interface IndustryRepository extends JpaRepository<Industry, Long>{

	List<Industry> findBySchool(School school, Sort sort);
	
}
