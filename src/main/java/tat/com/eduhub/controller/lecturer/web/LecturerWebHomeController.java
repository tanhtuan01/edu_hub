package tat.com.eduhub.controller.lecturer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;

@Controller
public class LecturerWebHomeController {

	@GetMapping(value = "/lecturer/trang-chu")
	public String lecturerWebHomePage(Model model) {
		
		BASE_METHOD.FragmentLecturerWeb("", model);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
}
