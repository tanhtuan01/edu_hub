package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.School;


public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>{

	List<TrainingProgram> findBySchool(School school);
	
	List<TrainingProgram> findBySchoolAndPostStatusTrue(School school);
}
