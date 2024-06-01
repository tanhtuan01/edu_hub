package tat.com.eduhub.controller.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;

@Controller
@RequestMapping(value = "/eh-admin/giang-vien")
public class LecturerWebController {

	@GetMapping(value = "/danh-sach")
	public String listLecturerHomePage(Model model) {
		
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
}
