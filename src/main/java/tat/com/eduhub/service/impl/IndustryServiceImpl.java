package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return repository.getOne(id);
	}
}
