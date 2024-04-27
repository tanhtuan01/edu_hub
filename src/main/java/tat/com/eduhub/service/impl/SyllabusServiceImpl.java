package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.Syllabus;
import tat.com.eduhub.repository.SyllabusRepository;
import tat.com.eduhub.service.SyllabusService;

@Service
public class SyllabusServiceImpl implements SyllabusService{

	@Autowired
	private SyllabusRepository repository;
	
	@Override
	public Syllabus save(Syllabus syllabus) {
		// TODO Auto-generated method stub
		return repository.save(syllabus);
	}
	
	@Override
	public Page<Syllabus> findByIdModule(@Param("id_module") Long idModule, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByIdModule(idModule, pageable);
	}
	
	@Override
	public Page<Syllabus> findByModuleCodeOrModuleName(@Param("value")String value, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByModuleCodeOrModuleName(value, pageable);
	}
	
	@Override
	public Page<Syllabus> findByIdModuleAndModuleCodeOrModuleName(@Param("id")Long id,@Param("value")String value, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByIdModuleAndModuleCodeOrModuleName(id, value, pageable);
	}
	
	@Override
	public Long saveAndGetId(Syllabus syllabus) {
		// TODO Auto-generated method stub
		Syllabus s = repository.saveAndFlush(syllabus);
		return s.getId();
	}
	
	@Override
	public Syllabus get(Long id) {
		// TODO Auto-generated method stub
		Optional<Syllabus> sOptional = repository.findById(id);
		return sOptional.orElse(null);
	}
	
	@Override
	public List<Syllabus> listBySchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school);
	}
}
