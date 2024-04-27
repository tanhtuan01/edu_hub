package tat.com.eduhub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Document;
import tat.com.eduhub.entity.School;

public interface DocumentService {

	Document save(Document document);
	
	Page<Document> findDocumentWithIdModuleAndNameModuleAndDocumentType(Long id, String name, String type, Pageable pageable);

	Long saveAndGetID(Document document);
	
	Document get(Long id);

	List<Document> listBySchool(School school);
	
	List<Document> documentStudentbySchool(Long idSchool, String value);
}
