package tat.com.eduhub.controller.lecturer.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/quan-ly-tai-khoan")
public class LecturerAccount {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@GetMapping(value = {"","/cai-dat"})
	@LecturerSchoolAccountCheck
	public String lecturerManageAccount(@PathVariable(name = "domain") String domain,
			Model model, Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentLecturerSchool("account", model);
		User user = userService.findByEmail(authentication.getName());
		model.addAttribute("user", user);
		
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
}
