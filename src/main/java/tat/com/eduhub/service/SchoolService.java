package tat.com.eduhub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.entity.School;

public interface SchoolService {

	School signUp(SchoolDTO schoolDTO);
	
	Page<School> pageListSchoolIsNotActive(Pageable pageable);
	
	School get(Long id);
	
	School save(School school);
	
	void delete(Long id);
	
	Long saveAndGetId(School school);
	
	School findByDomain(String domain);
}
