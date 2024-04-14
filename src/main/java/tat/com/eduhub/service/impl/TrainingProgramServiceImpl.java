package tat.com.eduhub.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
import tat.com.eduhub.entity.School;
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
	
	@Override
	public List<TrainingProgram> findBySchool(School school) {
		// TODO Auto-generated method stub
		List<TrainingProgram> list = repository.findBySchool(school);
		list.sort(Comparator.comparing(TrainingProgram::getId).reversed());
		return list;
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
}
