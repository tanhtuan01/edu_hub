package tat.com.eduhub.controller.lecturer.school;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/chuong-trinh-dao-tao")
public class LecturerTrainingProgramController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private SubjectDistributionDetailService sddService;
	
	@Autowired
	private SubjectDistributionService sdService;
	
	@GetMapping(value = {"","/danh-sach"})
	@LecturerSchoolAccountCheck
	public String listTrainingProgram(@PathVariable(name = "domain") String domain,Model model,
			Authentication authentication) {
		BASE_METHOD.FragmentLecturerSchool("list_training_program", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		User user = userService.findByEmail(authentication.getName());
		List<SubjectDistribution> subjectDistributions = sdService.listByUser(user);
		List<SubjectDistributionDTO> subjectDistributionDTOs = new ArrayList<>();
		for(SubjectDistribution s : subjectDistributions) {
			SubjectDistributionDTO sDTO = new SubjectDistributionDTO(); 
			sDTO.setId(s.getId());
			sDTO.setTpName(s.getTrainingProgram().getName());
			sDTO.setStatus(sddService.findBySubjectDistribution(s).size());
			sDTO.setModuleName(s.getModule().getName());
			subjectDistributionDTOs.add(sDTO);
		}
		model.addAttribute("list", subjectDistributionDTOs);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
}
