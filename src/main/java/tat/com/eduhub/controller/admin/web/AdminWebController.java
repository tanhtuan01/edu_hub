package tat.com.eduhub.controller.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = {"/eh-admin/trang-chu", "/eh-admin"})
public class AdminWebController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String adminWebPage(Model model, Authentication authentication) {
		BASE_METHOD.FragmentAdminWeb("index", model);
		User user = userService.findByEmail(authentication.getName());
		BASE_METHOD.titleActionUser("Trang chủ Eduhub", "index", user, model);
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
}
