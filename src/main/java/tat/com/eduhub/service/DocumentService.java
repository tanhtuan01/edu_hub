package tat.com.eduhub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Document;

public interface DocumentService {

	Document save(Document document);
	
	Page<Document> findDocumentWithIdModuleAndNameModuleAndDocumentType(Long id, String name, String type, Pageable pageable);
}
