package tat.com.eduhub.controller.admin.school;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;

@Controller
@RequestMapping(value = "/s-admin/{domain}/nganh-chuyen-nganh")
public class IndustryAndMajorController {

	@GetMapping(value = {"","/them-moi"})
	public String createIndustryOrMajorPage(Model model) {
		BASE_METHOD.FragmentAdminSchool("create_industry_major", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu-nganh")
	public String saveIndustry() {
		return "";
	}
}
