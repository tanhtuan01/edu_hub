package tat.com.eduhub.controller.admin.school;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.IndustryService;
import tat.com.eduhub.service.MajorService;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller	
public class AdminSchoolController {
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private IndustryService industryService;
	
	@Autowired
	private MajorService majorService;
	
	@Autowired
	private TeacherOfSchoolService tosService;
	
	@Autowired
	private ModuleService moduleService;
	
	@GetMapping(value = "/school-admin/{domain}")
	@SchoolAccountCheck
	public String adminSchoolByDomain(Authentication authentication, Model model, @PathVariable(name = "domain")String domain) {

		BASE_METHOD.titleAndAction("Trang chủ", "index", model);
		BASE_METHOD.FragmentAdminSchool("home", model);

		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		School school = schoolService.findByDomain(domain);
		
		model.addAttribute("totalTP", tpService.findBySchool(school).size());
		model.addAttribute("totalIndustry", industryService.listIndustrySchool(school).size());
		model.addAttribute("totalMajor", majorService.listMajorByIdSchool(school.getId()).size());
		model.addAttribute("totalTOS", tosService.listTeacherOfSchools(school).size());
		
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		model.addAttribute("majors", majors);
		
		List<Modules> modules = moduleService.findBySchool(school);
		model.addAttribute("modules", modules);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	

	
}
