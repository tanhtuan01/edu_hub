package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.ModuleRepository;
import tat.com.eduhub.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService{

	@Autowired
	private ModuleRepository repository;
	
}
