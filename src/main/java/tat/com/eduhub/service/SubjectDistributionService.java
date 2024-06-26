package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.User;

public interface SubjectDistributionService {

	SubjectDistribution save(SubjectDistribution subjectDistribution);
	
	List<SubjectDistribution> findByTrainingProgram(TrainingProgram trainingProgram);
	
	void delete(Long id);
	
	SubjectDistribution get(Long id);
	
	List<SubjectDistribution> findByTrainingProgramAndSemesterEquals(TrainingProgram trainingProgram, int semester);

	List<SubjectDistribution> listByUser(User user);
	
	boolean existsByTrainingProgramAndId(TrainingProgram trainingProgram, Long idSd);
}
