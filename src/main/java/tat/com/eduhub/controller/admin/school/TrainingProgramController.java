package tat.com.eduhub.controller.admin.school;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.TrainingProgramDTO;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-admin/{domain}/chuong-trinh-dao-tao")
public class TrainingProgramController {

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainingProgramService tpService;
	
	private ModelMapper mapper = new ModelMapper();
	
	private Long idTpSaved = (long) 0;
	
	@GetMapping(value = {"/them-moi", "/create"})
	@SchoolAccountCheck
	public String createTrainingProgramPage(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication) {
		idTpSaved = (long) 0;
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		BASE_METHOD.FragmentAdminSchool("create_training_program", model);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = {"/create", "/them-moi"})
	public String saveTrainingProgramName(Model model, Authentication authentication,@RequestParam(name = "name")String name, @ModelAttribute("domain")String domain) {
		
		TrainingProgramDTO dto = new TrainingProgramDTO();
		dto.setName(name);
		
		TrainingProgram tp = new TrainingProgram();
		tp.setName(dto.getName());
		
		idTpSaved = tpService.saveAndGetId(tp);
		
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/viet-noi-dung";
	}
	
	@GetMapping(value = {"/write", "/viet-noi-dung"})
	@SchoolAccountCheck
	public String writeTrainingProgramPage(Authentication authentication,Model model, @ModelAttribute(name = "domain")String domain) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);

		if(idTpSaved == null || idTpSaved == 0) {
			return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/them-moi";
		}
		
		BASE_METHOD.FragmentAdminSchool("training_program", model);
		try {
			TrainingProgram tp = tpService.get(idTpSaved);
			if(tp == null) {
				return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/them-moi";
			}else {
				TrainingProgramDTO dto = mapper.map(tp, TrainingProgramDTO.class);
				model.addAttribute("tp", dto);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = {"/luu", "/save"})
	public String saveTrainingProgram(@ModelAttribute(name = "tp")TrainingProgramDTO dto,@ModelAttribute(name = "domain")String domain) {
		TrainingProgram tp = mapper.map(dto, TrainingProgram.class);
		idTpSaved = tpService.saveAndGetId(tp);
		return "redirect:/school-admin/"+domain+"/chuong-trinh-dao-tao/viet-noi-dung";
	}
}
