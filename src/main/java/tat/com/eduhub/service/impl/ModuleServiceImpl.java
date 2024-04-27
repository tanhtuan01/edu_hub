package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.repository.ModuleRepository;
import tat.com.eduhub.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService{

	@Autowired
	private ModuleRepository repository;
	
	@Override
	public Modules save(Modules module) {
		// TODO Auto-generated method stub
		return repository.save(module);
	}
	
	@Override
	public Modules get(Long id) {
		// TODO Auto-generated method stub
		Optional<Modules> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public Page<Modules> pageModuleBySchool(School school, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school, pageable);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public List<Modules> findBySchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school);
	}

	

}
