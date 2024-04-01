package tat.com.eduhub.controller.admin.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller	
public class AdminSchoolController {
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;

	
	@GetMapping(value = "/s-admin/{domain}")
	@SchoolAccountCheck
	public String adminSchoolByDomain(Authentication authentication, Model model, @PathVariable(name = "domain")String domain) {
		BASE_METHOD.FragmentAdminSchool("traning_program", model);
		String name = "";
		if(authentication != null) {
			name = authentication.getName();
			System.err.println(name);
		}
		
		School school = schoolService.findByDomain(domain.trim());
		System.err.println(school.toString());
		User user = userService.findByEmail(name);
		System.err.println(user.toString());
		model.addAttribute("user", user);
		model.addAttribute("school", school);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	
	

	
}
