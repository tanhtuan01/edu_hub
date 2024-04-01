package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.IndustryRepository;
import tat.com.eduhub.service.IndustryService;

@Service
public class IndustryServiceImpl implements IndustryService{

	@Autowired
	private IndustryRepository repository;
	
}
