package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.repository.IndustryRepository;
import tat.com.eduhub.service.IndustryService;

@Service
public class IndustryServiceImpl implements IndustryService{

	@Autowired
	private IndustryRepository repository;
	
	@Override
	public Industry save(Industry industry) {
		// TODO Auto-generated method stub
		return repository.save(industry);
	}
	
	@Override
	public List<Industry> list() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	@Override
	public List<Industry> listIndustryBySchool(School school, String sort) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school, Sort.by(Sort.Direction.fromString(sort), "id"));
	}
	
	@Override
	public Industry get(Long id) {
		// TODO Auto-generated method stub
		Optional<Industry> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public Page<Industry> pageIndustryBySchool(School school, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school, pageable);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public boolean checkIndustryWithSchool(Long id, School school) {
		// TODO Auto-generated method stub
		return repository.existsBySchoolAndIdEquals(school, id);
	}
	
	@Override
	public List<Industry> listIndustrySchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchool(school);
	}
}
