package tat.com.eduhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;

@Controller
public class HomeController {

	@GetMapping
	public String indexWebPage(Model model) {
		BASE_METHOD.FragmentWeb("index_content", model);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
}
