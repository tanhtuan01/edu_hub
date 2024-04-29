package tat.com.eduhub.controller;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping
	public String indexWebPage(Model model) {
		BASE_METHOD.FragmentWeb("index_content", model);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/giang-day")
	public String teachingPage(Model model) {
		BASE_METHOD.FragmentWeb("teaching", model);
		model.addAttribute("user", new UserDTO());
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/quan-ly-ctdt")
	public String manageTPPage(Model model) {
		BASE_METHOD.FragmentWeb("intro_school_manage_tp", model);
		SchoolDTO schoolDTO = new SchoolDTO();
		model.addAttribute("school", schoolDTO);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@PostMapping(value = "/quan-ly-ctdt/tao-tai-khoan")
	public String signUpManage(@ModelAttribute(name = "school") SchoolDTO schoolDTO) {
		schoolService.signUp(schoolDTO);
		return "redirect:/quan-ly-ctdt?success#dang-ky-ngay";
	}
	
	@PostMapping(value = "/giang-day/tao-tai-khoan")
	public String createLecturerAcc(@ModelAttribute(name = "user") UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		user.setReceiveMail(userDTO.getEmail());
		user.setRoles(Arrays.asList(new Role("ROLE_LECTURER")));
		user.setAvt("no-avatar.png");
		user.setPasswords(passwordEncoder.encode(userDTO.getPasswords()));
		user.setDiploma(userDTO.getDiploma());
		user.setType("system_account");
		userService.create(user);
		return "redirect:/giang-day?success#dang-ky-ngay";
	}
}
