package tat.com.eduhub.controller.lecturer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;

@Controller
@RequestMapping(value = "/lecturer/khoa-hoc/them-moi")
public class LecturerWebAddCoursesController {

	@GetMapping
	public String addCoursesPage(Model model) {
		BASE_METHOD.FragmentLecturerWeb("add_courses", model);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
}
