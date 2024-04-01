package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.Major;

public interface MajorRepository extends JpaRepository<Major, Long>{

	@Query("select m from Major m where m.industry.school.id = :id_school")
	List<Major> listMajorByIdSchool(@Param("id_school")Long id);
	
}
