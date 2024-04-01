package tat.com.eduhub.controller.admin.school;

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
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/s-admin/{domain}/training-program")
public class TrainingProgramController {

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {"/create", ""})
	@SchoolAccountCheck
	public String createTrainingProgramPage(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication) {
		model.addAttribute("domain", domain);
		
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		BASE_METHOD.FragmentAdminSchool("create_training_program", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/create")
	@SchoolAccountCheck
	public String saveTrainingProgramName(Model model, Authentication authentication,@RequestParam(name = "name")String name, @ModelAttribute("domain")String domain) {
		System.err.println(name);
		System.err.println("domain: " + domain);
		System.err.println(userHelper.getUseAuthenticated(authentication));
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		return "redirect:/s-admin/"+domain+"/training-program/write";
	}
	
	@GetMapping(value = "/write")
	@SchoolAccountCheck
	public String writeTrainingProgramPage(Model model) {
		BASE_METHOD.FragmentAdminSchool("create_training_program", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
}
