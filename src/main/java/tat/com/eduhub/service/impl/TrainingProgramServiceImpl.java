package tat.com.eduhub.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.repository.TrainingProgramRepository;
import tat.com.eduhub.service.TrainingProgramService;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService{

	@Autowired
	private TrainingProgramRepository repository;
	
	@Override
	public TrainingProgram save(TrainingProgram tp) {
		// TODO Auto-generated method stub
		return repository.save(tp);
	}
	
	@Override
	public Long saveAndGetId(TrainingProgram tp) {
		// TODO Auto-generated method stub
		TrainingProgram tpSave = repository.saveAndFlush(tp);
		return tpSave.getId();
	}
	
	@Override
	public TrainingProgram get(Long id) {
		// TODO Auto-generated method stub
		 Optional<TrainingProgram> optionalTrainingProgram = repository.findById(id);
		 return optionalTrainingProgram.orElse(null);
	}
	
	@Override
	public List<TrainingProgram> findBySchool(School school) {
		// TODO Auto-generated method stub
		List<TrainingProgram> list = repository.findBySchool(school);
		list.sort(Comparator.comparing(TrainingProgram::getId).reversed());
		return list;
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public List<TrainingProgram> trainingProgramPostedBySchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndPostStatusTrue(school);
	}
	
	@Override
	public List<TrainingProgram> findByIdIndustry(@Param("id")Long id) {
		// TODO Auto-generated method stub
		return repository.findByIdIndustry(id);
	}
	
	@Override
	public List<TrainingProgram> findByMajor(Major major) {
		// TODO Auto-generated method stub
		return repository.findByMajor(major);
	}
	
	@Override
	public List<TrainingProgram> findBySchoolAndStatus(School school, String status) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndStatusEquals(school, status);
	}
	
	@Override
	public List<TrainingProgram> findBySchoolAndStatusNot(School school, String status) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndStatusNotLike(school, status);
	}
	
	@Override
	public List<TrainingProgram> trainingProgramNotPostedBySchool(School school) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndPostStatusFalse(school);
	}
	
	@Override
	public List<TrainingProgram> listTPSChoolPostedCohortLevelType(School school, String cohort,
			String level, String type) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndPostStatusTrueAndCohortContainingAndLevelContainingAndTypeContaining(school, cohort, level, type);
	}
	
	@Override
	public List<TrainingProgram> listTPSChoolNotPostCohortLevelType(School school, String cohort,
			String level, String type) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndPostStatusFalseAndCohortContainingAndLevelContainingAndTypeContaining(school, cohort, level, type);
	}
	
	@Override
	public List<TrainingProgram> listTPSchoolFinishCohortLevelType(School school, String status, String cohort,
			String level, String type) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndStatusEqualsAndCohortContainingAndLevelContainingAndTypeContaining(school, status, cohort, level, type);
	}
	
	@Override
	public List<TrainingProgram> listTPSchoolUnFinishCohortLevelType(School school, String status, String cohort,
			String level, String type) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndStatusNotLikeAndCohortContainingAndLevelContainingAndTypeContaining(school, status, cohort, level, type);
	}
	
	@Override
	public List<TrainingProgram> listTPSchoolCohortLevelType(School school, String cohort, String level, String type) {
		// TODO Auto-generated method stub
		return repository.findBySchoolAndCohortContainingAndLevelContainingAndTypeContaining(school, cohort, level, type);
	}
	
	@Override
	public List<TrainingProgram> listTPByIdIndustryAndIdSchool(@Param("s_id") Long idIndustry,@Param("idt_id") Long idSchool) {
		// TODO Auto-generated method stub
		return repository.findByIdIndustryAndIdSchool(idSchool, idIndustry);
	}
	
	@Override
	public List<TrainingProgram> listTPByMajorNull() {
		// TODO Auto-generated method stub
		return repository.findByMajorIsNull();
	}
	
	@Override
	public List<TrainingProgram> listBySchoolAndLimitDESC(@Param("s_id") Long idSchool, @Param("limit") int limit) {
		// TODO Auto-generated method stub
		return repository.listBySchoolAndLimitDESC(idSchool, limit);
	}
}
