package tat.com.eduhub.controller.lecturer.school;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.dto.SubjectDistributionDTO;
import tat.com.eduhub.entity.Document;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.SubjectDistributionDetail;
import tat.com.eduhub.entity.Syllabus;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.DocumentService;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.SubjectDistributionDetailService;
import tat.com.eduhub.service.SubjectDistributionService;
import tat.com.eduhub.service.SyllabusService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/hoc-phan-chuong-trinh-dao-tao")
public class LecturerTrainingProgramController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private SubjectDistributionDetailService sddService;
	
	@Autowired
	private SubjectDistributionService sdService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private SyllabusService syllabusService;
	
	@Autowired
	private ModuleService moduleService;
	
	Long idSubjectDistribution = (long) 0;
	
	String cmAction = "career";
	
	@GetMapping(value = {"","/danh-sach"})
	@LecturerSchoolAccountCheck
	public String listTrainingProgram(@PathVariable(name = "domain") String domain,Model model,
			Authentication authentication) {
		BASE_METHOD.FragmentLecturerSchool("list_training_program", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		User user = userService.findByEmail(authentication.getName());
		List<SubjectDistribution> subjectDistributions = sdService.listByUser(user);
		List<SubjectDistributionDTO> subjectDistributionDTOs = new ArrayList<>();
		idSubjectDistribution = null;
		for(SubjectDistribution s : subjectDistributions) {
			SubjectDistributionDTO sDTO = new SubjectDistributionDTO(); 
			sDTO.setId(s.getId());
			sDTO.setTpName(s.getTrainingProgram().getName());
			sDTO.setStatus(sddService.listSyllabusBySubjectDistribution(s).size(), sddService.listDocumentBySubjectDistribution(s).size());
			sDTO.setModuleName(s.getModule().getName());
			subjectDistributionDTOs.add(sDTO);
		}
		model.addAttribute("list", subjectDistributionDTOs);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
	@GetMapping(value = "/tndhp")
	public String redirectToAddDocumentSyllabusOfTrainingProgramPage(@PathVariable(name = "domain") String domain,
			@RequestParam(name = "id") Long id) {
		idSubjectDistribution = id;
		this.cmAction = "career";
		return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan";
		
	}
	
	@GetMapping(value = "/them-noi-dung-hoc-phan")
	@LecturerSchoolAccountCheck
	public String addDocumentSyllabusOfTrainingProgramPage(Model model, Authentication authentication,
			@PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentLecturerSchool("document_syllabus", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		if(idSubjectDistribution == 0 || idSubjectDistribution == null) {
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/danh-sach";
		}
		School school = schoolService.findByDomain(domain);
		String moduleName = sdService.get(idSubjectDistribution).getModule().getName();
 		model.addAttribute("moduleName", moduleName);
 		
 		List<SubjectDistributionDetail> listSyllabus = sddService.listSyllabusBySubjectDistribution(sdService.get(idSubjectDistribution));
 		model.addAttribute("listSyllabus", listSyllabus);
// 		System.err.println("list Syllabus size: " + listSyllabus.size());
 		List<SubjectDistributionDetail> listDocument = sddService.listDocumentBySubjectDistribution(sdService.get(idSubjectDistribution));
 		model.addAttribute("listDocument", listDocument);
// 		System.err.println("list listDocument size: " + listDocument.size());
 		model.addAttribute("cmAction", cmAction);
 		
 		List<Document> listDocumentExistent = documentService.listBySchool(school);
 		List<Syllabus> listSyllabusExistent = syllabusService.listBySchool(school);
 		model.addAttribute("documentExist", listDocumentExistent);
 		model.addAttribute("syllabusExist", listSyllabusExistent);
 		
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
	@PostMapping(value = "/them-de-cuong-hoc-phan")
	public String addNewSyllabusOfModuleInTrainingProgram(@PathVariable(name = "domain") String domain,
			@RequestParam(name = "syllabusname") String syllabusName,
			@RequestParam(name = "syllabusfile") MultipartFile syllabusFile,
			@RequestParam(name = "cmAction") String cmAction) {
		this.cmAction = cmAction;
		
		String extension = BASE_METHOD.getExtensionFileName(syllabusFile);

		String fileName = BASE_METHOD.removeAccents(syllabusName + BASE_METHOD.createRandomFileName()) +"."+extension;

		String syllabusFilePath = BASE_METHOD.syllabusLecturerPathUpload(domain, fileName);
		
		SubjectDistribution sd = sdService.get(idSubjectDistribution);
		Modules modules = moduleService.get(sd.getModule().getId());
		
		Syllabus syllabus = new Syllabus();
		syllabus.setFileName(fileName);
		syllabus.setModule(modules);
		syllabus.setName(syllabusName);
		Long savedSyllabusID  = syllabusService.saveAndGetId(syllabus);
		
		Syllabus s = syllabusService.get(savedSyllabusID);
		
		try {
			Files.write(Paths.get(syllabusFilePath), syllabusFile.getBytes());
			SubjectDistributionDetail sdd = new SubjectDistributionDetail();
			sdd.setSyllabus(s);
			sdd.setSubjectDistribution(sd);
			sddService.save(sdd);
			
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?added" ;
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?failed" ;
		}	
	}
	
	@PostMapping(value = "/them-tai-lieu-hoc-phan")
	public String addNewDocumentOfModuleInTrainingProgram(@PathVariable(name = "domain") String domain,
			@RequestParam(name = "documentfile") MultipartFile documentFile,
			HttpServletRequest request
			) {
		
		try {
			this.cmAction = request.getParameter("cmAction");
			
			String documentName = request.getParameter("documentname");
			String type = request.getParameter("type");
			String share = request.getParameter("share");
			
			String extension = BASE_METHOD.getExtensionFileName(documentFile);
			String fileName = BASE_METHOD.removeAccents(documentName + BASE_METHOD.createRandomFileName()) +"."+extension;
			
			Document document  = new Document(type, share, documentName, fileName);
			
			String documentFilePath;
			
			if(share.equals("private")) {
				documentFilePath = BASE_METHOD.documentPathUploadPrivate(domain, fileName);
			}else {
				documentFilePath = BASE_METHOD.documentPathUploadPublic(fileName);
			}
			Long savedDocumentID = documentService.saveAndGetID(document);
			SubjectDistribution s = sdService.get(idSubjectDistribution);
			
			SubjectDistributionDetail sdd = new SubjectDistributionDetail();
			sdd.setDocument(documentService.get(savedDocumentID));
			sdd.setSubjectDistribution(s);
			sddService.save(sdd);
			Files.write(Paths.get(documentFilePath), documentFile.getBytes());
			
		} catch (Exception e) {
			// TODO: handle exception
	
			System.err.println("LỖI: " + e.getMessage());
		}
		return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?added" ;
	}
	
	@GetMapping(value = "/xoa-de-cuong")
	public String deleteSyllabusFromModuleOfTrainingProgram(@RequestParam(name = "id")Long id,
			@PathVariable(name = "domain") String domain ) {
		this.cmAction = "career";
		try {
			sddService.delete(id);
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?deleted";
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?failed";
		}
		
	}
	
	@GetMapping(value = "/xoa-tai-lieu")
	public String deleteDocumentFromModuleOfTrainingProgram(@RequestParam(name = "id")Long id,
			@PathVariable(name = "domain") String domain ) {
		this.cmAction = "major";
		try {
			sddService.delete(id);
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?deleted";
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?failed";
		}
		
	}
	
	@PostMapping(value = "/them-de-cuong")
	public String addSyllabusModuleOfTrainingProgram(@PathVariable(name = "domain") String domain,
			HttpServletRequest request) {
		
		try {
			this.cmAction = "career";
			String [] syllabusID = request.getParameterValues("syllabus");
			
			for(String id : syllabusID) {
				Long syllabusId = Long.parseLong(id);
				SubjectDistribution sd = sdService.get(idSubjectDistribution);
				Syllabus s = syllabusService.get(syllabusId);
				boolean existBySyllabus = sddService.existsBySyllabus(s);
				
				if(!existBySyllabus) {
					SubjectDistributionDetail sdd = new SubjectDistributionDetail();
					sdd.setSubjectDistribution(sd);
					sdd.setSyllabus(s);
					sddService.save(sdd);
				}
			}
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?added";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?failed";
	}
	
	@PostMapping(value = "/them-tai-lieu")
	public String addDocumentModuleOfTrainingProgram(@PathVariable(name = "domain") String domain,
			HttpServletRequest request) {
		try {
			this.cmAction = "major";
			String [] documentID = request.getParameterValues("document");
			
			for(String id : documentID) {
				Long documentId = Long.parseLong(id);
				SubjectDistribution sd = sdService.get(idSubjectDistribution);
				Document d = documentService.get(documentId);
				
				boolean existByDocument = sddService.existsByDocument(d);
				if(!existByDocument) {
					SubjectDistributionDetail sdd = new SubjectDistributionDetail();
					sdd.setSubjectDistribution(sd);
					sdd.setDocument(d);
					sddService.save(sdd);
				}
			}
			return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?added";
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/school-lecturer/" + domain + "/hoc-phan-chuong-trinh-dao-tao/them-noi-dung-hoc-phan?failed";
	}
}
