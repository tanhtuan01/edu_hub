package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.MajorRepository;
import tat.com.eduhub.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService{

	@Autowired
	private MajorRepository repository;
	
}
