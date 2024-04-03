package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;


public interface ModuleRepository extends JpaRepository<Modules, Long>{

	Page<Modules> findBySchool(School school, Pageable pageable);
	
	List<Modules> findBySchool(School school);
	
}
