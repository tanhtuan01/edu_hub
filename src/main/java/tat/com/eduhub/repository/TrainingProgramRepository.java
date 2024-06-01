package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.Major;



public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>{

	List<TrainingProgram> findBySchool(School school);
	
	List<TrainingProgram> findBySchoolAndPostStatusTrue(School school);
	
	@Query("select t from TrainingProgram t where t.major.industry.id = :id")
	List<TrainingProgram> findByIdIndustry(@Param("id")Long idIndustry);
	
	List<TrainingProgram> findByMajor(Major major);
	
	List<TrainingProgram> findBySchoolAndStatusEquals(School school, String status);
	
	List<TrainingProgram> findBySchoolAndStatusNotLike(School school, String status);
	
	List<TrainingProgram> findBySchoolAndPostStatusFalse(School school);
	
	List<TrainingProgram> findBySchoolAndPostStatusTrueAndCohortContainingAndLevelContainingAndTypeContaining(School school, String cohort, String level, String type);

	List<TrainingProgram> findBySchoolAndPostStatusFalseAndCohortContainingAndLevelContainingAndTypeContaining(School school, String cohort, String level, String type);

	List<TrainingProgram> findBySchoolAndStatusEqualsAndCohortContainingAndLevelContainingAndTypeContaining(School school, String status, String cohort, String level, String type);
	
	List<TrainingProgram> findBySchoolAndStatusNotLikeAndCohortContainingAndLevelContainingAndTypeContaining(School school, String status, String cohort, String level, String type);

	List<TrainingProgram> findBySchoolAndCohortContainingAndLevelContainingAndTypeContaining(School school, String cohort, String level, String type);

	@Query("select t from TrainingProgram t where t.school.id = :s_id and t.major.industry.id = :idt_id ")
	List<TrainingProgram> findByIdIndustryAndIdSchool(@Param("s_id") Long idSchool, @Param("idt_id") Long idIndustry);

	List<TrainingProgram> findByMajorIsNull();
	
	@Query(nativeQuery =  true, value = "SELECT * FROM tb_training_program WHERE id_school = :s_id ORDER BY id desc LIMIT :limit")
	List<TrainingProgram> listBySchoolAndLimitDESC(@Param("s_id") Long idSchool, @Param("limit") int limit);
}


