package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.SubjectDistributionDetail;

public interface SubjectDistributionDetailService {

	List<SubjectDistributionDetail> findByTrainingProgram(Long id);
	
	List<SubjectDistributionDetail> findBySubjectDistribution(SubjectDistribution subjectDistribution);

	SubjectDistributionDetail save(SubjectDistributionDetail subjectDistributionDetail);

	List<SubjectDistributionDetail> listSyllabusBySubjectDistribution(SubjectDistribution subjectDistribution);
	
	List<SubjectDistributionDetail> listDocumentBySubjectDistribution(SubjectDistribution subjectDistribution);

	void delete(Long id);
}
