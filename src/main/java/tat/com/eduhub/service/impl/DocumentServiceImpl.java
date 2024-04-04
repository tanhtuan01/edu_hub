package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Document;
import tat.com.eduhub.repository.DocumentRepository;
import tat.com.eduhub.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	private DocumentRepository repository;
	
	@Override
	public Document save(Document document) {
		// TODO Auto-generated method stub
		return repository.save(document);
	}
	
	@Override
	public Page<Document> findDocumentWithIdModuleAndNameModuleAndDocumentType(@Param("id") Long id, @Param("name") String name, @Param("type") String type, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findDocumentWithIdModuleAndNameModuleAndDocumentType(id, name, type, pageable);
	}
}
