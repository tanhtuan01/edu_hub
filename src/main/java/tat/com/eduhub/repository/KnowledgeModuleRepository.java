package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.KnowledgeModule;

public interface KnowledgeModuleRepository extends JpaRepository<KnowledgeModule, Long>{

	@Query("SELECT k FROM KnowledgeModule k WHERE k.programContent.trainingProgram.id = :id_tp")
	List<KnowledgeModule> listKnowledgeModuleByTrainingProgram(@Param("id_tp") Long id_tp);
	
}
