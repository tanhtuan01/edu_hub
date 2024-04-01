package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.School;

public interface IndustryService {

	Industry save(Industry industry);
	
	List<Industry> list();
	
	List<Industry> listIndustryBySchool(School school, String sort);
	
	Industry get(Long id);
}
