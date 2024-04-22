package tat.com.eduhub.controller.student.school;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.component.UserHelper;

@Controller
@RequestMapping(value = "/student/{domain}")
public class StudentSchoolController {
	
	@Autowired
	private UserHelper userHelper;

	@GetMapping(value = {"/","/home"})
	public String studentSchoolHomePage(Model model, HttpServletRequest request) {
		userHelper.getUserDataInfo(request, model);
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
}
