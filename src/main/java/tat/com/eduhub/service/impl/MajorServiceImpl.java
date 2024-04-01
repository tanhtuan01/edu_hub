package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
}
