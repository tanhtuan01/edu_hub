package tat.com.eduhub.controller.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/eh-admin/hoc-vien-nguoi-dung")
public class StudentWebController {

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = {"/danh-sach", ""})
	public String studentOnWebController(Model model, Authentication authentication) {
		BASE_METHOD.FragmentAdminWeb("student_user", model);
		BASE_METHOD.titleActionUser("Học viên, Người dùng", "student", userHelper.getUserLogged(authentication), model);
		List<User> list = userService.listRoleUserStudent();
		model.addAttribute("list", list);
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
}
