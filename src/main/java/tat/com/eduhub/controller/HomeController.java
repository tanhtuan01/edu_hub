package tat.com.eduhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;

@Controller
public class HomeController {

	@GetMapping
	public String indexWebPage(Model model) {
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
}
