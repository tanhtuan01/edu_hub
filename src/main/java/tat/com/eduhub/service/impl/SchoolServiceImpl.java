package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Override
	public Page<School> pageListSchoolIsNotActive(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.pageListSchoolIsNotActive(pageable);
	}
	
	@Override
	public School get(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}
	
	@Override
	public School save(School school) {
		// TODO Auto-generated method stub
		return repository.save(school);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public Long saveAndGetId(School school) {
		// TODO Auto-generated method stub
		School s = repository.saveAndFlush(school);
		return s.getId();
	}
}
