package tat.com.eduhub.controller.admin.web;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.EmailSenderService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/eh-admin/request")
public class SchoolRequestController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherOfSchoolService tosService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private ModelMapper mapper = new ModelMapper();

	@GetMapping
	public String listSchoolRequest(Model model, @RequestParam(name = "size", defaultValue = "10")int size,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		BASE_METHOD.FragmentAdminWeb("request_list", model);
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<School> pageListSchoolIsNotActive = schoolService.pageListSchoolIsNotActive(pageable);
		model.addAttribute("ps", pageListSchoolIsNotActive.getContent());
		model.addAttribute("currentPage", pageListSchoolIsNotActive.getNumber());
		model.addAttribute("totalPages", pageListSchoolIsNotActive.getTotalPages());
		model.addAttribute("page", page);
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/confirm")
	public String confirmRequestFromSchool(@RequestParam(name = "id")Long id) {
		
		String randomStr = BASE_METHOD.randomString(5) + BASE_METHOD.createStrDateNow();
		// change status
		School school = schoolService.get(id);
		school.setStatus("is_active");
		schoolService.save(school);
		// create admin school account
		User user = new User();
		user.setUserName(school.getName());
		user.setAvt("no-avatar.png");
		user.setEmail(school.getDomain()+"@eduhub.com");
		user.setPasswords(passwordEncoder.encode(randomStr));
		user.setRoles(Arrays.asList(new Role("ROLE_ADMINSCHOOL")));
		Long userId = userService.saveAndGetId(user);
		//get admin school account still create
		User u = userService.get(userId);
		// save acc of school
		TeacherOfSchool teacherOfSchool = new TeacherOfSchool();
		teacherOfSchool.setSchool(school);
		teacherOfSchool.setAdmin(true);
		teacherOfSchool.setUser(u);
		tosService.save(teacherOfSchool);
		
		// send email
		emailSenderService.sendEmail(school.getEmail(), 
				"Tạo tài khoản thành công", "Đây là tài khoản admin của bạn: tài khoản: "+ u.getEmail() +", mật khẩu: "+randomStr +",đường dẫn: https://www.facebook.com/");
		
		return "redirect:/eh-admin/request?confirm_success";
	}
	
	@GetMapping(value = "/delete")
	public String deleteRequestFromSchool(@RequestParam(name = "id")Long id) {
		schoolService.delete(id);
		return "redirect:/eh-admin/request?delete_success";
	}
	
}
