package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.repository.StudentLessonRepository;
import tat.com.eduhub.service.StudentLessonService;

@Service
public class StudentLessonServiceImpl implements StudentLessonService{

	@Autowired
	private StudentLessonRepository repository;
	
}
