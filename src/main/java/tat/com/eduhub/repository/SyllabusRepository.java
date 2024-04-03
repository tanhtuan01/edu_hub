package tat.com.eduhub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.Syllabus;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long>{

	@Query("select s from Syllabus s  where s.module.id = :id_module")
	Page<Syllabus> findByIdModule(@Param("id_module") Long idModule, Pageable pageable);
	
	@Query("select s from Syllabus s where s.module.code like %:value% OR s.module.name like %:value%")
	Page<Syllabus> findByModuleCodeOrModuleName(@Param("value")String value, Pageable pageable);

	@Query("select s from Syllabus s where s.module.id = :id and s.module.code like %:value% or s.module.name like %:value%")
	Page<Syllabus> findByIdModuleAndModuleCodeOrModuleName(@Param("id")Long id,@Param("value")String value,Pageable pageable);
}
