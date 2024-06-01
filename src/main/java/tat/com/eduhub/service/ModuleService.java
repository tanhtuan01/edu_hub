package tat.com.eduhub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;

public interface ModuleService {

	Modules save(Modules module);
	
	Modules get(Long id);
	
	Page<Modules> pageModuleBySchool(School school, Pageable pageable);
	
	void delete(Long id);
	
	List<Modules> findBySchool(School school);
	
	List<Modules> findBySchoolAndMajor(School school, Major major);
}
