package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.repository.SchoolRepository;
import tat.com.eduhub.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService{

	@Autowired
	private SchoolRepository repository;
	
	@Override
	public School signUp(SchoolDTO dto) {
		// TODO Auto-generated method stub
		School school = new School(
					dto.getName(), dto.getDomain(), dto.getEmail(), dto.getHotline(), dto.getLogo(), "is_not_active"
				);
		return repository.save(school);
	}
}
