package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;

public interface TrainingProgramService {

	TrainingProgram save(TrainingProgram tp);
	
	Long saveAndGetId(TrainingProgram tp);
	
	TrainingProgram get(Long id);
	
	List<TrainingProgram> findBySchool(School school);
	
	void delete(Long id);
	
	List<TrainingProgram> trainingProgramPostedBySchool(School school);

	List<TrainingProgram> findByIdIndustry(Long id);
	
	List<TrainingProgram> findByMajor(Major major);
	
	List<TrainingProgram> findBySchoolAndStatus(School school, String status);
	
	List<TrainingProgram> findBySchoolAndStatusNot(School school, String status);
	
	List<TrainingProgram> trainingProgramNotPostedBySchool(School school);
	
	List<TrainingProgram> listTPSChoolPostedCohortLevelType(School school, String cohort, String level, String type);

	List<TrainingProgram> listTPSChoolNotPostCohortLevelType(School school, String cohort, String level, String type);

	List<TrainingProgram> listTPSchoolFinishCohortLevelType(School school, String status, String cohort, String level, String type);

	List<TrainingProgram> listTPSchoolUnFinishCohortLevelType(School school, String status, String cohort, String level, String type);

	List<TrainingProgram> listTPSchoolCohortLevelType(School school, String cohort, String level, String type);

	List<TrainingProgram> listTPByIdIndustryAndIdSchool(Long idIndustry, Long idSchool);

	List<TrainingProgram> listTPByMajorNull();
	
	List<TrainingProgram> listBySchoolAndLimitDESC(Long idSchool, int limit);
}
