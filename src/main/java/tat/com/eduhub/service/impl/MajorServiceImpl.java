package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.repository.MajorRepository;
import tat.com.eduhub.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService{

	@Autowired
	private MajorRepository repository;
	
	@Override
	public Major save(Major major) {
		// TODO Auto-generated method stub
		return repository.save(major);
	}
	
	@Override
	public List<Major> listMajorByIdSchool(@Param("id_school")Long id) {
		// TODO Auto-generated method stub
		return repository.listMajorByIdSchool(id);
	}
	
	@Override
	public Page<Major> pageMajorByIdSchool(@Param("id_school") Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.pageMajorByIdSchool(id, pageable);
	}
	
	@Override
	public Major get(Long id) {
		// TODO Auto-generated method stub
		Optional<Major> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);;
	}
	
	@Override
	public boolean checkMajorWithIndustry(Long id, Industry industry) {
		// TODO Auto-generated method stub
		return repository.existsByIndustryAndIdEquals(industry, id);
	}
}
