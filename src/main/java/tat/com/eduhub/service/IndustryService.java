package tat.com.eduhub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.School;

public interface IndustryService {

	Industry save(Industry industry);
	
	List<Industry> list();
	
	List<Industry> listIndustryBySchool(School school, String sort);
	
	Industry get(Long id);
	
	Page<Industry> pageIndustryBySchool(School school, Pageable pageable);
	
	void delete(Long id);
	
	boolean checkIndustryWithSchool(Long id, School school);
	
	List<Industry> listIndustrySchool(School school);
}
