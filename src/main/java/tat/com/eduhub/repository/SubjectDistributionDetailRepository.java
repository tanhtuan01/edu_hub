package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.SubjectDistributionDetail;
import tat.com.eduhub.entity.SubjectDistribution;


public interface SubjectDistributionDetailRepository extends JpaRepository<SubjectDistributionDetail, Long>{

	@Query("select s from SubjectDistributionDetail s where s.subjectDistribution.trainingProgram.id = :id_tp")
	List<SubjectDistributionDetail> findByTrainingProgram(@Param("id_tp") Long id);
	
	List<SubjectDistributionDetail> findBySubjectDistribution(SubjectDistribution subjectDistribution);
	
}