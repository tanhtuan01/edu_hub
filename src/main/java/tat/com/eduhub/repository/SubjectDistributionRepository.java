package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TrainingProgram;


public interface SubjectDistributionRepository extends JpaRepository<SubjectDistribution, Long>{

	List<SubjectDistribution> findByTrainingProgram(TrainingProgram trainingProgram);
	
	List<SubjectDistribution> findByTrainingProgramAndSemesterEquals(TrainingProgram trainingProgram, int semester);
}
