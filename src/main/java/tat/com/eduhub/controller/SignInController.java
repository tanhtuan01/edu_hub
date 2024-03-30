package tat.com.eduhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;

@Controller
@RequestMapping(value = "sign-in")
public class SignInController {

	@GetMapping
	public String signInPage(Model model) {
		model.addAttribute("fragment", BASE_METHOD.FragmentWeb("page_sign_in"));
//		BASE_METHOD.AddFragment("web/page_sign_in", model);
		return BASE_FIELD.SIGN_LAYOUT;
	}
	
}
