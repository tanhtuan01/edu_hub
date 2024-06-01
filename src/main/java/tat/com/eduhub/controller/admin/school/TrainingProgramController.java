package tat.com.eduhub.controller.admin.school;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.weaver.ast.And;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.RateOfProcessTrainingProgram;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.TrainingProgramDTO;
import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.KnowledgeModule;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.ProgramContent;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.EmailSenderService;
import tat.com.eduhub.service.IndustryService;
import tat.com.eduhub.service.KnowledgeModuleService;
import tat.com.eduhub.service.MajorService;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.ProgramContentService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.SubjectDistributionDetailService;
import tat.com.eduhub.service.SubjectDistributionService;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-admin/{domain}/chuong-trinh-dao-tao")
public class TrainingProgramController {

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private ProgramContentService programContentService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private MajorService majorService;
	
	@Autowired
	private KnowledgeModuleService knowledgeModuleService;
	
	@Autowired
	private SubjectDistributionService subjectDistributionService;
	
	@Autowired
	private TeacherOfSchoolService tosService;
	
	@Autowired
	private SubjectDistributionDetailService sddService;
	
	@Autowired
	private IndustryService industryService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	private ModelMapper mapper = new ModelMapper();
	
	private Long idTpSaved = (long) 0;
	private Long idTpAssignment = (long) 0;
	
	@GetMapping(value = {"/them-moi", "/create"})
	@SchoolAccountCheck
	public String createTrainingProgramPage(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication) {
		idTpSaved = (long) 0;
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.titleAndAction("Thêm mới chương trình đào tạo", "add-tp", model);
		BASE_METHOD.FragmentAdminSchool("create_training_program", model);
		School school = schoolService.findByDomain(domain);
		List<TrainingProgram> trainingPrograms = tpService.listBySchoolAndLimitDESC(school.getId(), 5);
		model.addAttribute("tp", trainingPrograms);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = {"/create", "/them-moi"})
	public String saveTrainingProgramName(Model model, Authentication authentication,@RequestParam(name = "name")String name, @ModelAttribute("domain")String domain) {
		
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);

		TrainingProgram tp = new TrainingProgram();
		tp.setName(name.trim());
		tp.setSchool(schoolService.findByDomain(domain));
		tp.setStatus("3%");
		tp.setPostStatus(false);
		idTpSaved = tpService.saveAndGetId(tp);
		
		
		return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/viet-noi-dung";
	}
	
	@GetMapping(value = {"/write", "/viet-noi-dung"})
	@SchoolAccountCheck
	public String writeTrainingProgramPage(Authentication authentication,Model model, @ModelAttribute(name = "domain")String domain,
			@RequestParam(name = "tpItem", required = false, defaultValue = "1")int tpItem) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		if(idTpSaved == null || idTpSaved == 0) {
			return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/them-moi";
		}
		model.addAttribute("major", majorService.listMajorByIdSchool(schoolService.findByDomain(domain).getId()));
		model.addAttribute("programContent", programContentService.listByTrainingProgram(tpService.get(idTpSaved)));
		model.addAttribute("module", moduleService.findBySchool(schoolService.findByDomain(domain)));
		List<KnowledgeModule> listKnowledgeModuleByTrainingProgram = knowledgeModuleService.listKnowledgeModuleByTrainingProgram(idTpSaved);
		model.addAttribute("knowledgeModule", listKnowledgeModuleByTrainingProgram);
		BASE_METHOD.titleAndAction("Chỉnh sửa nội dung", "add-tp", model);
		model.addAttribute("tpItem", tpItem);
		BASE_METHOD.FragmentAdminSchool("training_program", model);
		try {
			TrainingProgram tp = tpService.get(idTpSaved);
			if(tp == null) {
				return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/them-moi";
			}else {
				TrainingProgramDTO dto = mapper.map(tp, TrainingProgramDTO.class);

				model.addAttribute("tp", dto);

				List<SubjectDistribution> findByTrainingProgram = subjectDistributionService.findByTrainingProgram(tp);
				model.addAttribute("sd", findByTrainingProgram);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("Lỗi: " + e.getMessage());
		}
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = {"/luu", "/save"})
	public String saveTrainingProgram(@ModelAttribute(name = "tp")TrainingProgramDTO dto,
			@ModelAttribute(name = "domain")String domain,
			@RequestParam(name = "tpItemValue")int tpItemValue) {
		TrainingProgram tp = tpService.get(dto.getId());
		tp.setSchool(schoolService.findByDomain(domain));
		tp.setMajor(majorService.get(dto.getIdMajor()));
		tp.setCohort(dto.getCohort()); tp.setOutputStandards(dto.getOutputStandards());
		tp.setDuration(dto.getDuration()); tp.setGeneralObjective(dto.getGeneralObjective());
		tp.setJobProspects(dto.getJobProspects()); tp.setLevel(dto.getLevel()); 
		tp.setGraduatingCohort(dto.getGraduatingCohort()); tp.setName(dto.getName());
		tp.setNumberOfSemesters(dto.getNumberOfSemesters()); tp.setType(dto.getType());
		tp.setTotalCredits(dto.getTotalCredits()); tp.setSpecificObjective(dto.getSpecificObjective());
		tp.setProcess(dto.getProcess()); tp.setTargetApplicants(dto.getTargetApplicants());
		tp.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(tp, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
		idTpSaved = tpService.saveAndGetId(tp);
		TrainingProgram tp2 = tpService.get(idTpSaved);

		System.err.println("RATE OF PROCESS: " + RateOfProcessTrainingProgram.totalRateOfProcess(tp2, programContentService, knowledgeModuleService, subjectDistributionService, sddService));
		
		return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/viet-noi-dung?tpItem="+tpItemValue+"&updated";
	}
	
	@GetMapping(value = {"/danh-sach",""})
	@SchoolAccountCheck
	public String listTrainingProgram(Model model, Authentication authentication, @PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentAdminSchool("list_training_program", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		School school = schoolService.findByDomain(domain);
		List<TrainingProgram> list = tpService.findBySchool(school);
		model.addAttribute("list", list);
		idTpSaved = (long)0;
		model.addAttribute("kdt", "");
		List<Industry> industries = industryService.listIndustryBySchool(school, "desc");
		model.addAttribute("industries", industries);
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		model.addAttribute("majors", majors);
		BASE_METHOD.titleAndAction("Danh sách chương trình đào tạo", "list-tp", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/them-khoi-kien-thuc")
	public String addKnowledgeArea(@ModelAttribute(name = "domain") String domain, Model model,
			@RequestParam(name = "khoiKienThuc") String khoiKienThuc) {
		System.err.println("--------------"+khoiKienThuc);
		if(khoiKienThuc.trim().length() > 0) {
			ProgramContent programContent = new ProgramContent();
			programContent.setKnowledgeArea(khoiKienThuc);
			programContent.setTrainingProgram(tpService.get(idTpSaved));
			programContentService.save(programContent);
			
			TrainingProgram tp2 = tpService.get(idTpSaved);
			tp2.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(tp2, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
			tpService.save(tp2);
			List<ProgramContent> programContents = programContentService.listByTrainingProgram(tp2);
			if(programContents.size() > 0) {
				System.err.println("Program Content: " + programContents.size());
				for(ProgramContent p : programContents) {
					System.err.println("ID: " + p.getId());
				}
			}else {
				System.err.println("Program Content: 0");
			}
			
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=6&updated";
		}
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=6&failed";
	}
	
	@PostMapping(value = "/them-hp-khoi-kt")
	public String addModuleKnowledge(@ModelAttribute(name = "domain") String domain,
			HttpServletRequest request) {
		
		Long knowledgeID = Long.parseLong(request.getParameter("knowledgeID"));
		System.err.println("knowledgeID: " + knowledgeID);
		try {
			String[] moduleParam = request.getParameterValues("module");
			for(String id: moduleParam) {
				Long idModule = Long.parseLong(id);
				 ProgramContent programContent = programContentService.get(knowledgeID);
				 Modules modules = moduleService.get(idModule); 
				 KnowledgeModule knowledgeModule = new KnowledgeModule();
				 knowledgeModule.setProgramContent(programContent);
				 knowledgeModule.setModules(modules);
				 knowledgeModuleService.save(knowledgeModule);
			}
			TrainingProgram tp = tpService.get(idTpSaved);
			tp.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(tp, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
			tpService.save(tp);
		} catch (Exception e) {
			System.err.println("LỖI: " + e.getMessage());
		}
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=6&updated";
	}
	
	@GetMapping(value = "/pg-xoa-hp")
	public String deleteModuleFromKnowledgeArea(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id")Long id) {
//		System.err.println(id);
		knowledgeModuleService.delete(id);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=6&updated";
	}
	
	@GetMapping(value = "/pg-xoa")
	public String deleteKnowledgeArea(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id")Long id) {
//		System.err.println(id);
		programContentService.delete(id);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=6&updated";
	}
	
	@PostMapping(value = "/them-hocphan-hocky")
	public String addModuleOfSemester(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "idModule") Long idModule,
			@RequestParam(name = "semester") int semester) {
		SubjectDistribution subjectDistribution = new SubjectDistribution();
		subjectDistribution.setModule(moduleService.get(idModule));
		subjectDistribution.setTrainingProgram(tpService.get(idTpSaved));
		subjectDistribution.setSemester(semester);
		subjectDistributionService.save(subjectDistribution);
		TrainingProgram tp = tpService.get(idTpSaved);
		tp.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(tp, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
		tpService.save(tp);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=7&updated";
	}
	
	@GetMapping(value = "/chinh-sua")
	@SchoolAccountCheck
	public String editTrainingProgram(@RequestParam(name = "id")Long id,
			@ModelAttribute(name = "domain") String domain) {
		idTpSaved = id;
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=1";
	}
	
	@GetMapping(value = "/hocky-xoa-hocphan")
	public String deleteModuleInSemester(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id") Long id) {
		
		subjectDistributionService.delete(id);
		TrainingProgram tp = tpService.get(idTpSaved);
		tp.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(tp, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
		tpService.save(tp);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/viet-noi-dung?tpItem=7&updated";
	}
	
	@GetMapping(value = "/phan-cong-giang-vien")
	@SchoolAccountCheck
	public String setLecturerAddModuleSyllabusFile(Model model, @PathVariable(name = "domain") String domain, Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		if(idTpAssignment == 0) {
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
		}
		BASE_METHOD.FragmentAdminSchool("faculty_assignment", model);
		TrainingProgram tp = tpService.get(idTpAssignment);
		BASE_METHOD.titleAndAction("Phân công giảng viên", "list-tp", model);
		idTpSaved = tp.getId();
		model.addAttribute("tp", tp);
		School school = schoolService.findByDomain(domain);
		List<TeacherOfSchool> list = tosService.listTeacherOfSchools(school);
		model.addAttribute("list", list);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/pcgv")
	public String redirectToSetLecturerAddModuleSyllabusFile(@RequestParam(name = "id") Long id,
			@PathVariable(name = "domain") String domain) {
		idTpAssignment = id;
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/phan-cong-giang-vien";
	}
	
	@PostMapping(value = "/pcgv")
	public String addLecturerToSetLecturerAddModuleSyllabusFile(@PathVariable(name = "domain") String domain,
			HttpServletRequest request) {
//		System.err.println("ID TP: " + idTpAssignment);
//		System.err.println("ID idsubjectdistribution: " + request.getParameter("idsubjectdistribution"));
//		System.err.println("ID USER: " + request.getParameter("user"));
		User user = userService.get(Long.valueOf(request.getParameter("user")));
		TrainingProgram trainingProgram = tpService.get(idTpAssignment);
		SubjectDistribution subjectDistribution = subjectDistributionService.get(Long.valueOf(request.getParameter("idsubjectdistribution")));
		subjectDistribution.setUser(user);
		subjectDistribution.setTrainingProgram(trainingProgram);
		subjectDistributionService.save(subjectDistribution);
//		System.err.println("RECEIVE MAIL: " + user.getReceiveMail());
		String moduleName = subjectDistribution.getModule().getName();
		String toEmail = user.getReceiveMail();
		String subject = "Thông báo";
		String tpName = trainingProgram.getName();
		StringBuffer messages = new StringBuffer();
		messages.append("Bạn đã được thêm vào chương trình đào tạo: " + tpName );
		messages.append("\nCông việc: thêm đề cương và tài liệu cho học phần: " + moduleName);
		messages.append("\nĐường dẫn: http://localhost:2024/school-lecturer/hunre.edu.vn/hoc-phan-chuong-trinh-dao-tao/danh-sach");
		messages.append("\nĐược gửi từ: " + trainingProgram.getSchool().getName());
		emailSenderService.sendEmail(toEmail, subject, messages.toString());
		trainingProgram.setStatus(RateOfProcessTrainingProgram.totalRateOfProcess(trainingProgram, programContentService, knowledgeModuleService, subjectDistributionService, sddService) + "%");
		tpService.save(trainingProgram);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/phan-cong-giang-vien?updated";
	}
	
	@GetMapping(value = "/xoa")
	public String deleteTp(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id")Long id) {
		tpService.delete(id);
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach?deleted";
	}
	
	@GetMapping(value = "/trang-thai")
	public String updatePostStatus(@PathVariable(name = "domain") String domain,
			@RequestParam(name = "act") String act,
			@RequestParam(name = "id") Long id) {
		
		TrainingProgram trainingProgram = tpService.get(id);
		if(act.equals("post")) {
			trainingProgram.setPostStatus(true);
		}else {
			trainingProgram.setPostStatus(false);
		}
		tpService.save(trainingProgram);
		
		return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach?updated";
	}
	
	@GetMapping(value = "/chi-tiet")
	@SchoolAccountCheck
	public String viewTrainingProgram(@RequestParam(name = "id", required = false) Long id, Model model,
			Authentication authentication, @PathVariable(name = "domain") String domain) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		if(id != null) {
			idTpSaved = id;
		}
		if(idTpSaved == null || idTpSaved == 0) {
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
		}
		BASE_METHOD.titleAndAction("Chi tiết chương trình đào tạo", "list-tp", model);
		BASE_METHOD.FragmentAdminSchool("view_training_program", model);
		TrainingProgram tp = tpService.get(idTpSaved);
		model.addAttribute("tp", tp);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/xem-tep/{sdId}")
	@SchoolAccountCheck
	public String viewFileOfSubjectDistribution(Model model, Authentication authentication,
			@PathVariable(name = "sdId") Long sdId, 
			@PathVariable(name = "domain") String domain) {
		
		if(sdId == null) {
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao";
		}
		TrainingProgram trainingProgram = tpService.get(idTpSaved);
		boolean existsTpAndIdSd = subjectDistributionService.existsByTrainingProgramAndId(trainingProgram, sdId);
		if(!existsTpAndIdSd) {
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/pcgv?id=" + idTpSaved;
		}
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.titleAndAction("Tệp trong chương trình đào tạo", "list-tp", model);
		SubjectDistribution subjectDistribution = subjectDistributionService.get(sdId);
		model.addAttribute("tp", trainingProgram);
		model.addAttribute("sd", subjectDistribution);
		model.addAttribute("tpId", idTpSaved);
		BASE_METHOD.FragmentAdminSchool("view_file", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/danh-sach/trang-thai")
	@SchoolAccountCheck
	public String manageTrainingProgram(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication, HttpServletRequest request) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentAdminSchool("list_training_program", model);
		School school = schoolService.findByDomain(domain);
	
		try {
			String trangThai = request.getParameter("tt");
			String khoaDaoTao = request.getParameter("kdt");
			String trinhDoDaoTao = request.getParameter("tddt");
			String loaiHinhDaoTao = request.getParameter("lhdt");
			List<TrainingProgram> trainingPrograms = new ArrayList<>();
			int tt = 0;
			if(trangThai.equals("")) {
//				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
				trainingPrograms = tpService.listTPSchoolCohortLevelType(school, khoaDaoTao, trinhDoDaoTao, loaiHinhDaoTao);
			}else if(trangThai.equals("da-hoan-thanh")) {
				
				tt = 1;
				trainingPrograms = tpService.listTPSchoolFinishCohortLevelType(school, "100%", khoaDaoTao, trinhDoDaoTao, loaiHinhDaoTao);
			}else if(trangThai.equals("chua-hoan-thanh")) {
				tt = 2;
				trainingPrograms = tpService.listTPSchoolUnFinishCohortLevelType(school, "100%", khoaDaoTao, trinhDoDaoTao, loaiHinhDaoTao);
			}else if(trangThai.equals("da-dang")) {
				tt = 3;
				trainingPrograms = tpService.listTPSChoolPostedCohortLevelType(school, khoaDaoTao, trinhDoDaoTao, loaiHinhDaoTao);
			}else if(trangThai.equals("chua-dang")) {
				tt = 4;
				trainingPrograms = tpService.listTPSChoolNotPostCohortLevelType(school, khoaDaoTao, trinhDoDaoTao, loaiHinhDaoTao);
			}else if(trangThai.equals("") && khoaDaoTao.equals("") && trinhDoDaoTao.equals("") && loaiHinhDaoTao.equals("")) {
				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
			}
//			else {
////				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
//			}
			String txt = "Tìm theo: " + 
					"\n\t Trạng thái: " + str(trangThai) + 
					"\n\t Khóa đào tạo: " + str(khoaDaoTao) + 
					"\n\t Trình độ đào tạo: " + str(trinhDoDaoTao) + 
					"\n\t Loại hình đào tạo: " + str(loaiHinhDaoTao) 
					;
			model.addAttribute("txt", txt);
			model.addAttribute("list", trainingPrograms);
			model.addAttribute("tt", tt);
			model.addAttribute("kdt", khoaDaoTao);
			model.addAttribute("tddt", trinhDoDaoTao);
			model.addAttribute("lhdt", loaiHinhDaoTao);
			idTpSaved = (long)0;
		}catch (Exception e) {
			// TODO: handle exception
		}
		BASE_METHOD.titleAndAction("Quản lý chương trình đào tạo", "list-tp", model);
		List<Industry> industries = industryService.listIndustryBySchool(school, "desc");
		model.addAttribute("industries", industries);
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		model.addAttribute("majors", majors);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	public String str(String str) {
		switch (str) {
		case "level_undergraduate":
			return "Đại học";
		case "level_master_degree":
			return "Thạc sĩ";
		case "level_doctorate":
			return "Tiến sĩ";
		case "type_regular":
			return "Chính quy";
		case "type_work_study":
			return "Vừa học vừa làm";
		case "type_online_learning":
			return "Đào tạo từ xa";
		case "":
			return "Trống";
		case "da-hoan-thanh":
			return "Đã hoàn thành";
		case "chua-hoan-thanh":
			return "Chưa hoàn thành";
		case "da-dang":
			return "Đã đăng";
		case "chua-dang":
			return "Chưa đăng";
		}
		return str;
	}
	
	@GetMapping(value = "/danh-sach/nganh-chuyen-nganh")
	@SchoolAccountCheck
	public String industryMajor(Model model, Authentication authentication,
			@PathVariable(name = "domain") String domain, HttpServletRequest  request) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentAdminSchool("list_training_program", model);
		BASE_METHOD.titleAndAction("Quản lý chương trình đào tạo", "list-tp", model);
		School school = schoolService.findByDomain(domain);
		List<Industry> industries = industryService.listIndustryBySchool(school, "desc");
		model.addAttribute("industries", industries);
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		model.addAttribute("majors", majors);
		Long idIndustry = Long.valueOf(request.getParameter("ndt"));
		Long idMajor = Long.valueOf(request.getParameter("cndt"));
		List<TrainingProgram> trainingPrograms = new ArrayList<>();
		String txt = "";
		if(idIndustry == 0 && idMajor == 0) {
			return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
		}else if(idIndustry != 0 && idMajor == 0) {
			boolean checkIndustryWithSchool = industryService.checkIndustryWithSchool(idIndustry, school);
			if(!checkIndustryWithSchool) {
				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
			}
			Industry industry = industryService.get(idIndustry);
			txt = "Tìm theo: " +
					"\n\t Ngành đào tạo: " + industry.getIndustryName();
			trainingPrograms = tpService.listTPByIdIndustryAndIdSchool(idIndustry, school.getId());
		}else if(idMajor != 0 && idIndustry != 0) {
			boolean checkIndustryWithSchool = industryService.checkIndustryWithSchool(idIndustry, school);
			if(!checkIndustryWithSchool) {
				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
			}
			Industry industry = industryService.get(idIndustry);
			
			
			boolean checkMajorWithIndustry = majorService.checkMajorWithIndustry(idMajor, industry);
			if(!checkMajorWithIndustry) {
				return "redirect:/school-admin/" + domain + "/chuong-trinh-dao-tao/danh-sach";
			}
			Major major = majorService.get(idMajor);
			txt = "Tìm theo: " +
					"\n\t Ngành đào tạo: " + industry.getIndustryName() + 
					"\n\t Chuyên ngành đào tạo: " + major.getMajorName();
			trainingPrograms = tpService.findByMajor(major);
		}
		model.addAttribute("ndt", idIndustry);
		model.addAttribute("cndt", idMajor);
		model.addAttribute("list", trainingPrograms);
		model.addAttribute("txt", txt);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT; 
	}
	
	@GetMapping(value = "/danh-sach/chua-co-chuyen-nganh")
	@SchoolAccountCheck
	public String tpEmptyMajor(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		School school = schoolService.findByDomain(domain);
		List<Industry> industries = industryService.listIndustryBySchool(school, "desc");
		model.addAttribute("industries", industries);
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		model.addAttribute("majors", majors);
		model.addAttribute("list", tpService.listTPByMajorNull());
		BASE_METHOD.FragmentAdminSchool("list_training_program", model);
		BASE_METHOD.titleAndAction("Quản lý chương trình đào tạo", "list-tp", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT; 
	}
	
}
