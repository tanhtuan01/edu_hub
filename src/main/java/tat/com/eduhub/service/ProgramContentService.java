package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.ProgramContent;
import tat.com.eduhub.entity.TrainingProgram;

public interface ProgramContentService {

	ProgramContent save(ProgramContent programContent);

	List<ProgramContent> listByTrainingProgram(TrainingProgram trainingProgram);
	
	ProgramContent get(Long id);
	
	void delete(Long id);
}
