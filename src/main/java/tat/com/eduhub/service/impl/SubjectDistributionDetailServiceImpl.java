package tat.com.eduhub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.SubjectDistributionDetail;
import tat.com.eduhub.repository.SubjectDistributionDetailRepository;
import tat.com.eduhub.service.SubjectDistributionDetailService;

@Service
public class SubjectDistributionDetailServiceImpl implements SubjectDistributionDetailService{

	@Autowired
	private SubjectDistributionDetailRepository repository;
	
	@Override
	public List<SubjectDistributionDetail> findByTrainingProgram(@Param("id_tp") Long id) {
		// TODO Auto-generated method stub
		return repository.findByTrainingProgram(id);
	}
	
	@Override
	public List<SubjectDistributionDetail> findBySubjectDistribution(SubjectDistribution subjectDistribution) {
		// TODO Auto-generated method stub
		return repository.findBySubjectDistribution(subjectDistribution);
	}
}
