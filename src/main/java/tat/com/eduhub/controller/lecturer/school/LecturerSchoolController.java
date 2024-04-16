package tat.com.eduhub.controller.lecturer.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
public class LecturerSchoolController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/school-lecturer/{domain}")
	@LecturerSchoolAccountCheck
	public String lecturerSchoolPage(Model model,
			Authentication authentication, @PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentLecturerSchool("list_training_program", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
}
