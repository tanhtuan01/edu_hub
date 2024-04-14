package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.repository.SubjectDistributionRepository;
import tat.com.eduhub.service.SubjectDistributionService;

@Service
public class SubjectDistributionServiceImpl implements SubjectDistributionService{

	@Autowired
	private SubjectDistributionRepository repository;
	
	@Override
	public SubjectDistribution save(SubjectDistribution subjectDistribution) {
		// TODO Auto-generated method stub
		return repository.save(subjectDistribution);
	}
	
	@Override
	public List<SubjectDistribution> findByTrainingProgram(TrainingProgram trainingProgram) {
		// TODO Auto-generated method stub
		return repository.findByTrainingProgram(trainingProgram);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
}
