package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.ProgramContent;
import tat.com.eduhub.entity.TrainingProgram;


public interface ProgramContentRepository extends JpaRepository<ProgramContent, Long>{

	List<ProgramContent> findByTrainingProgram(TrainingProgram trainingProgram);
	
	
}
