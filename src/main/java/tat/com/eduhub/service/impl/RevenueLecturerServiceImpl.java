package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.RevenueLecturer;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.RevenueLecturerRepository;
import tat.com.eduhub.service.RevenueLecturerService;

@Service
public class RevenueLecturerServiceImpl implements RevenueLecturerService{

	@Autowired
	private RevenueLecturerRepository repository;
	
	@Override
	public RevenueLecturer save(RevenueLecturer revenueLecturer) {
		// TODO Auto-generated method stub
		return repository.save(revenueLecturer);
	}
	
	@Override
	public List<RevenueLecturer> findByUser(User user) {
		// TODO Auto-generated method stub
		return repository.findByUser(user);
	}
}
