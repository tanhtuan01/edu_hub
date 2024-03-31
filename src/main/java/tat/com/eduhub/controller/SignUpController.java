package tat.com.eduhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/sign-up")
public class SignUpController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@GetMapping
	public String signUpPage(Model model) {
		BASE_METHOD.FragmentWeb("page_sign_up", model);
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@PostMapping
	public String saveUser(@ModelAttribute(name = "user")UserDTO userDTO) {
		Long id = (long) 0;
		try {
			id = userService.saveDTOAndGetId(userDTO);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if(id > 0) {
			return "redirect:/sign-up?success";
		}else {
			return "redirect:/sign-up?error";
		}
	}
	
	@GetMapping(value = "/manage")
	public String signUpPageManage(Model model) {
		BASE_METHOD.FragmentWeb("page_sign_up_manage",model);
		SchoolDTO schoolDTO = new SchoolDTO();
		model.addAttribute("school", schoolDTO);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@PostMapping(value = "/manage")
	public String signUpManage(@ModelAttribute(name = "school") SchoolDTO schoolDTO) {
		schoolService.signUp(schoolDTO);
		return "redirect:/sign-up/manage?success";
	}
	
}
