package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.CoursesRepository;
import tat.com.eduhub.service.CoursesService;

@Service
public class CoursesServiceImpl implements CoursesService{

	@Autowired
	private CoursesRepository repository;
	
}
