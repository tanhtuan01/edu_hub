package tat.com.eduhub.controller.lecturer.school;

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
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/chuong-trinh-dao-tao")
public class LecturerTrainingProgram {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private UserService userService;

	@Autowired
	private TrainingProgramService tpService;

	@GetMapping(value = { "", "/danh-sach", "/" })
	@LecturerSchoolAccountCheck
	public String listTrainingProgram(Model model, Authentication authentication,
			@PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentLecturerSchool("list_training_program", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		School school = schoolService.findByDomain(domain);
		List<TrainingProgram> trainingPrograms = tpService.trainingProgramPostedBySchool(school);
		model.addAttribute("tp", trainingPrograms);
		BASE_METHOD.titleAndAction("Chương trình đào tạo", "list-tp", model);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}

	@GetMapping(value = "/xem")
	public String getTP(@PathVariable(name = "domain") String domain, @RequestParam(name = "ct") String ct,
			@RequestParam(name = "id") Long id) {
		String slugCt = BASE_METHOD.slug(ct);
		return "redirect:/school-lecturer/" + domain + "/chuong-trinh-dao-tao/" + slugCt + "/" + id;
	}

	@GetMapping(value = "/{slug}/{id}")
	@LecturerSchoolAccountCheck
	public String viewTP(Model model, @PathVariable(name = "id", required = false) Long id,
			@PathVariable(name = "domain") String domain, Authentication authentication) {
		 TrainingProgram trainingProgram = null;
		 BASE_METHOD.titleAndAction("Chương trình đào tạo", "list-tp", model);
		try {
			 trainingProgram = tpService.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("LỖI:" + e.getMessage());
		}
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);

		if (id == null || trainingProgram == null) {
			return "redirect:/school-lecturer/" + domain + "/chuong-trinh-dao-tao/danh-sach";
		}
		model.addAttribute("tp", trainingProgram);

		BASE_METHOD.FragmentLecturerSchool("view_training_program", model);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}

}
