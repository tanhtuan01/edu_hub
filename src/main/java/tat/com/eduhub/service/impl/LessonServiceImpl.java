package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.LessonRepository;
import tat.com.eduhub.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService{

	@Autowired
	private LessonRepository repository;
	
}
