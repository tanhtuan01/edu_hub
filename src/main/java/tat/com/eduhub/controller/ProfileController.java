package tat.com.eduhub.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.UserDataInfo;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private UserHelper userHelper;
	
	@GetMapping(value = {"","/"})
	public String redirectToProfile(HttpServletRequest request, Model model) {
		UserDataInfo userDataInfo = userHelper.getUserDataInfo(request, model);
		switch (userDataInfo.getRole()) {
		case "ROLE_LECTURER":
			return "redirect:/lecturer/khoa-hoc";
		case "ROLE_ADMIN_SCHOOOL":
			return "redirect:/school-admin/" + userDataInfo.getDomain();
		case "ROLE_LECTURERSCHOOL":
			return "redirect:/school-lecturer/" +userDataInfo.getDomain();
		default:
			break;
		}
		
		return "/";
		
	}
	
}
