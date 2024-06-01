package tat.com.eduhub.base;

import java.lang.reflect.Field;
import java.util.List;

import tat.com.eduhub.entity.KnowledgeModule;
import tat.com.eduhub.entity.ProgramContent;
import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.SubjectDistributionDetail;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.service.KnowledgeModuleService;
import tat.com.eduhub.service.ProgramContentService;
import tat.com.eduhub.service.SubjectDistributionDetailService;
import tat.com.eduhub.service.SubjectDistributionService;

public class RateOfProcessTrainingProgram {

	public static int fromTrainingProgram(TrainingProgram trainingProgram) {
		int count = 0;

		count += countNonEmptyString(trainingProgram.getName(), 3);
		count += countNonEmptyString(trainingProgram.getLevel(), 3);
		count += countNonEmptyString(trainingProgram.getType(), 3);
		count += countNonEmptyString(trainingProgram.getCohort(), 3);

		if (trainingProgram.getMajor() != null && trainingProgram.getMajor().getId() > 0)
			count += 3;

		count += countNonEmptyString(trainingProgram.getGeneralObjective(), 5);
		count += countNonEmptyString(trainingProgram.getSpecificObjective(), 5);
		count += countNonEmptyString(trainingProgram.getOutputStandards(), 5);
		count += countNonEmptyString(trainingProgram.getJobProspects(), 5);

		if ( trainingProgram.getDuration() > 0)
			count += 3;
		if (trainingProgram.getTotalCredits() > 0)
			count += 3;
		if (trainingProgram.getNumberOfSemesters() > 0)
			count += 3;

		count += countNonEmptyString(trainingProgram.getTargetApplicants(), 5);
		count += countNonEmptyString(trainingProgram.getProcess(), 5);
		count += countNonEmptyString(trainingProgram.getGraduatingCohort(), 5);

		return count;
	}
	
	public static int fromListProgramContent(List<ProgramContent> programContents) {// khối kiến thức

		return (programContents.size() > 0) ? 5 : 0;
	}
	
	public static int fromListKnowledgeModule(List<KnowledgeModule> knowledgeModules) { // học phần khối kiến thức

		return (knowledgeModules.size() > 0) ? 5 : 0;
	}
	
	public static int fromListSubjectDistribution(
			SubjectDistributionService service, TrainingProgram trainingProgram) {// phân phối môn học

		List<SubjectDistribution> subjectDistributions = service.findByTrainingProgram(trainingProgram);
		
		if(subjectDistributions.size() == 0) {
			return 0;
		}
		return 10;

	}
	
	public static int fromListSubjectDistributionDetail(List<SubjectDistribution> subjectDistributions,
			SubjectDistributionDetailService service) {
		int count = 0, syllabus =0, document =0;

		if(subjectDistributions.size() == 0) {
			return 0;
		}
		
		for(SubjectDistribution sd : subjectDistributions) {
			for(int i =1; i <= sd.getSemester(); i ++) {
				List<SubjectDistributionDetail> sdd = service.findBySubjectDistribution(sd);
				if(sdd.size() == 0) {
					return 0;
				}else {
					for(SubjectDistributionDetail sds: sdd) {
						if(sds.getSyllabus() == null &&sds.getDocument() == null) {
							count ++;
						}
						if(sds.getSyllabus() == null && sds.getDocument() != null) {
							syllabus ++;
						}
						if(sds.getSyllabus() != null && sds.getDocument() == null) {
							document ++;
						}
					}
				}
			}
		}
		
		if(syllabus > 0 && document == 0 || syllabus == 0 && document > 0) {
			return 5;
		}
		
		return (count == 0) ? 10: 0;
	
	}
	
	public static int fromLecturerListSubjectDistribution(List<SubjectDistribution> subjectDistributions) {
		int count = 0;
		if(subjectDistributions.size() == 0) {
			return 0;
		}
		for(SubjectDistribution s : subjectDistributions) {
			if(s.getUser() == null) {
				count = 1;
			}
		}
		return (count == 0) ? 11 : 0;
	}

	private static int countNonEmptyString(String str, int value) {
		return (str != null && str.trim().length() > 0 ) ? value : 0;
	}

	public static int totalRateOfProcess(TrainingProgram trainingProgram,
			ProgramContentService programContentService,
			KnowledgeModuleService knowledgeModuleService,
			SubjectDistributionService subjectDistributionService,
			SubjectDistributionDetailService subjectDistributionDetailService) {
		
		Long idTP = trainingProgram.getId();
		int rateOfProcess = fromTrainingProgram(trainingProgram);
		//System.err.println("1___" + rateOfProcess);
		List<ProgramContent> programContents = programContentService.listByTrainingProgram(trainingProgram);
		if(programContents.size() > 0) {
			rateOfProcess += fromListProgramContent(programContents);
			//System.err.println("2___" + rateOfProcess);
			List<KnowledgeModule> knowledgeModules = knowledgeModuleService.listKnowledgeModuleByTrainingProgram(idTP);
			rateOfProcess += fromListKnowledgeModule(knowledgeModules);
			//System.err.println("3___" + rateOfProcess);
			List<SubjectDistribution> subjectDistributions = subjectDistributionService.findByTrainingProgram(trainingProgram);
			rateOfProcess += fromListSubjectDistribution( subjectDistributionService, trainingProgram);
			//System.err.println("4___" + rateOfProcess);
			rateOfProcess += fromListSubjectDistributionDetail(subjectDistributions, subjectDistributionDetailService);
			//System.err.println("5___" + rateOfProcess);
			rateOfProcess += fromLecturerListSubjectDistribution(subjectDistributions);
			//System.err.println("6___" + rateOfProcess);
		}
		
		return  rateOfProcess;
	}

}
