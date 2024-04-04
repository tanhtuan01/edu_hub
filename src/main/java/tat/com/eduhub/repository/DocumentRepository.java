package tat.com.eduhub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tat.com.eduhub.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

//	@Query("select d from Document d where d.module.id = :id AND d.module.name like %:name% AND d.type like %:type% OR ")
//	@Query("SELECT d FROM Document d WHERE (:id IS NULL OR d.module.id = :id) AND (:name IS NULL OR d.module.name LIKE %:name% and d.module.code LIKE %:name%) AND (:type IS NULL OR d.type LIKE %:type%)")
	@Query("SELECT d FROM Document d WHERE (:id IS NULL OR d.module.id = :id) AND (:name IS NULL OR d.module.name LIKE %:name% OR d.module.code LIKE %:name%) AND d.type LIKE %:type%")
	Page<Document> findDocumentWithIdModuleAndNameModuleAndDocumentType(@Param("id") Long id, @Param("name") String name, @Param("type") String type, Pageable pageable);
	
}
