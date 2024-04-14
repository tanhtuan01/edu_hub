package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.KnowledgeModule;

public interface KnowledgeModuleService {

	KnowledgeModule save(KnowledgeModule knowledgeModule);
	
	void delete(Long id);
	
	List<KnowledgeModule> listKnowledgeModuleByTrainingProgram(Long id);
}
