package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TrainingProgram;

public interface SubjectDistributionService {

	SubjectDistribution save(SubjectDistribution subjectDistribution);
	
	List<SubjectDistribution> findByTrainingProgram(TrainingProgram trainingProgram);
	
	void delete(Long id);
}
