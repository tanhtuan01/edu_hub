package tat.com.eduhub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Syllabus;

public interface SyllabusService {

	Syllabus save(Syllabus syllabus);

	Page<Syllabus> findByIdModule(Long idModule, Pageable pageable);
	
	Page<Syllabus> findByModuleCodeOrModuleName(String value, Pageable pageable);

	Page<Syllabus> findByIdModuleAndModuleCodeOrModuleName(Long id,String value,Pageable pageable);
}
