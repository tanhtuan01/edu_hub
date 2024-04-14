package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.KnowledgeModule;
import tat.com.eduhub.repository.KnowledgeModuleRepository;
import tat.com.eduhub.service.KnowledgeModuleService;

@Service
public class KnowledgeModuleServiceImpl implements KnowledgeModuleService{

	@Autowired
	private KnowledgeModuleRepository repository;
	
	@Override
	public KnowledgeModule save(KnowledgeModule knowledgeModule) {
		// TODO Auto-generated method stub
		return repository.save(knowledgeModule);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public List<KnowledgeModule> listKnowledgeModuleByTrainingProgram(@Param("id_tp")Long id) {
		// TODO Auto-generated method stub
		return repository.listKnowledgeModuleByTrainingProgram(id);
	}
	
}
