package tat.com.eduhub.service;

import tat.com.eduhub.entity.TrainingProgram;

public interface TrainingProgramService {

	TrainingProgram save(TrainingProgram tp);
	
	Long saveAndGetId(TrainingProgram tp);
	
	TrainingProgram get(Long id);
}
