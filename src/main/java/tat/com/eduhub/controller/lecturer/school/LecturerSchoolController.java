package tat.com.eduhub.controller.lecturer.school;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.dto.SubjectDistributionDTO;
import tat.com.eduhub.entity.SubjectDistribution;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.SubjectDistributionDetailService;
import tat.com.eduhub.service.SubjectDistributionService;
import tat.com.eduhub.service.UserService;

@Controller
public class LecturerSchoolController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectDistributionService sdService;
	
	@Autowired
	private SubjectDistributionDetailService sddService;
	
	@GetMapping(value = "/school-lecturer/{domain}")
	@LecturerSchoolAccountCheck
	public String lecturerSchoolPage(Model model,
			Authentication authentication, @PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentLecturerSchool("index_content", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.titleAndAction("Trang chủ", "index", model);
		User user = userService.findByEmail(authentication.getName());
		List<SubjectDistribution> subjectDistributions = sdService.listByUser(user);

		model.addAttribute("list", subjectDistributions.size());
		//return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
		return "redirect:/school-lecturer/"+domain+"/hoc-phan-chuong-trinh-dao-tao/danh-sach";
	}
	
}
