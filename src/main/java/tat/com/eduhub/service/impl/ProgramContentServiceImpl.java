package tat.com.eduhub.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.ProgramContent;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.repository.ProgramContentRepository;
import tat.com.eduhub.service.ProgramContentService;

@Service
public class ProgramContentServiceImpl implements ProgramContentService{

	@Autowired
	private ProgramContentRepository repository;
	
	@Override
	public ProgramContent save(ProgramContent programContent) {
		// TODO Auto-generated method stub
		return repository.save(programContent);
	}
	
	@Override
	public List<ProgramContent> listByTrainingProgram(TrainingProgram trainingProgram) {
		// TODO Auto-generated method stub
		return repository.findByTrainingProgram(trainingProgram);
	}
	
	@Override
	public ProgramContent get(Long id) {
		// TODO Auto-generated method stub
		Optional<ProgramContent> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
}
