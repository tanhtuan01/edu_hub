package tat.com.eduhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.Base.BASE_FIELD;
import tat.com.eduhub.Base.BASE_METHOD;
import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "sign-up")
public class SignUpController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String signUpPage(Model model) {
		model.addAttribute("fragment", BASE_METHOD.FragmentWeb("page_sign_up"));
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return BASE_FIELD.SIGN_LAYOUT;
	}
	
	@PostMapping
	public String saveUser(@ModelAttribute(name = "user")UserDTO userDTO) {
		Long id = (long) 0;
		try {
			id = userService.saveAndGetId(userDTO);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if(id > 0) {
			return "redirect:/sign-up?success";
		}else {
			return "redirect:/sign-up?error";
		}
	}
}
