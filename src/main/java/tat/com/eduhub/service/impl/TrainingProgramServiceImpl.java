package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.repository.TrainingProgramRepository;
import tat.com.eduhub.service.TrainingProgramService;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService{

	@Autowired
	private TrainingProgramRepository repository;
	
	@Override
	public TrainingProgram save(TrainingProgram tp) {
		// TODO Auto-generated method stub
		return repository.save(tp);
	}
	
	@Override
	public Long saveAndGetId(TrainingProgram tp) {
		// TODO Auto-generated method stub
		TrainingProgram tpSave = repository.saveAndFlush(tp);
		return tpSave.getId();
	}
	
	@Override
	public TrainingProgram get(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}
}