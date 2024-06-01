package tat.com.eduhub.controller.admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.EmailSenderService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TeacherOfSchoolService;

@Controller
@RequestMapping(value = "/eh-admin/truong-hoc")
public class SchoolOnWebController {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private TeacherOfSchoolService tosService;

	@GetMapping(value = {"/danh-sach",""})
	public String listSchoolOnWebPage(Model model, Authentication authentication) {
		BASE_METHOD.FragmentAdminWeb("school", model);
		BASE_METHOD.titleActionUser("Trường học trên Eduhub", "school", userHelper.getUserLogged(authentication), model);
		List<School> list = schoolService.findAll();
		model.addAttribute("list", list);
		return BASE_FIELD.ADMIN_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/doi-trang-thai/{status}")
	public String changeStatusSchool(@PathVariable(name = "status") String status,
			@RequestParam(name = "id") Long idSchool) {
		if(status == null || idSchool == null) {
			return "redirect:/eh-admin/truong-hoc/danh-sach";
		}
		School school = schoolService.get(idSchool);
		TeacherOfSchool tos = tosService.findByUserAdminSchool(school);
		User user = tos.getUser();
		if(status.equals("khoa")) {
			school.setStatus("is_not_active");
			String sub = "Khóa tài khoản";
			String body = "Tài khoản admin của trường: " + school.getName() +" đã bị khóa" +
			"\nMọi thắc mắc hãy liên hệ tới địa chỉ email: eduhub.contact2024@gmail.com";
			emailSenderService.sendEmail(user.getReceiveMail(), sub, body);
		}
		if(status.equals("hoat-dong")) {
			school.setStatus("is_active");
			String sub = "Mở khóa tài khoản";
			String body = "Tài khoản admin của trường: " + school.getName() +" đã được mở khóa";
			emailSenderService.sendEmail(user.getReceiveMail(), sub, body);
		}
		try {
			schoolService.save(school);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/eh-admin/truong-hoc/danh-sach?success";
	}
	
}
