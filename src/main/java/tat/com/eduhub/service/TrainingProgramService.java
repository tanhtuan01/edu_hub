package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;

public interface TrainingProgramService {

	TrainingProgram save(TrainingProgram tp);
	
	Long saveAndGetId(TrainingProgram tp);
	
	TrainingProgram get(Long id);
	
	List<TrainingProgram> findBySchool(School school);
	
	void delete(Long id);

}
