package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.User;



public interface SubjectDistributionRepository extends JpaRepository<SubjectDistribution, Long>{

	List<SubjectDistribution> findByTrainingProgram(TrainingProgram trainingProgram);
	
	List<SubjectDistribution> findByTrainingProgramAndSemesterEquals(TrainingProgram trainingProgram, int semester);

	List<SubjectDistribution> findByUser(User user);
	
	boolean existsByTrainingProgramAndId(TrainingProgram trainingProgram, Long idSd);

}
