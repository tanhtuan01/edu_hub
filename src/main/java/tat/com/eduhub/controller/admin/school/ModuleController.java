package tat.com.eduhub.controller.admin.school;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import tat.com.eduhub.dto.MajorDTO;
import tat.com.eduhub.dto.ModuleDTO;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.service.MajorService;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-admin/{domain}/hoc-phan")
public class ModuleController {

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private ModuleService moduleService;

	private ModelMapper mapper = new ModelMapper();

	@GetMapping(value = { "/them-moi", "/danh-sach", "" })
	@SchoolAccountCheck
	public String addModulePage(HttpServletRequest request,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, Model model,
			Authentication authentication, @PathVariable(name = "domain") String domain) {
		Pageable pageable = PageRequest.of(page - 1, size);
		String requestURI = request.getRequestURI();
		BASE_METHOD.FragmentAdminSchool("create_module", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		model.addAttribute("domain", domain);
		School school = schoolService.findByDomain(domain);
		model.addAttribute("moduleAction", "add");
		model.addAttribute("addOrEdit", "add");
		if (requestURI.equals("/school-admin/" + domain + "/hoc-phan/danh-sach")
				|| requestURI.equals("/school-admin/" + domain + "/hoc-phan")) {
			model.addAttribute("moduleAction", "list");
		}
		

		model.addAttribute("module", new ModuleDTO());
		Page<Modules> modulePage = moduleService.pageModuleBySchool(school, pageable);
		model.addAttribute("listmodule", modulePage);

		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		List<MajorDTO> majorDTOs = majors.stream().map(m -> mapper.map(m, MajorDTO.class)).collect(Collectors.toList());
		model.addAttribute("majors", majorDTOs);
		
		model.addAttribute("page", page);
		model.addAttribute("totalPages", modulePage.getTotalPages());
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}

	@PostMapping(value = "/luu")
	public String saveModule(@ModelAttribute(name = "module") ModuleDTO moduleDTO,
			@ModelAttribute(name = "domain") String domain) {
		School school = schoolService.findByDomain(domain);
		Modules module = new Modules();
		if (moduleDTO.getId() == null) {
			module = mapper.map(moduleDTO, Modules.class);
		} else {
			module = moduleService.get(moduleDTO.getId());
			module.setCode(moduleDTO.getCode());module.setName(moduleDTO.getName());
			module.setCredits(moduleDTO.getCredits()); module.setExercise(moduleDTO.getExercise());
			module.setSelfStudy(moduleDTO.getSelfStudy()); module.setPractise(moduleDTO.getPractise());
			module.setTheory(moduleDTO.getTheory()); 
		}
		Major major = majorService.get(moduleDTO.getIdMajor());
		module.setMajor(major);
		module.setSchool(school);
		moduleService.save(module);
		return "redirect:/school-admin/" + domain + "/hoc-phan?save_success";
	}

	@GetMapping(value = "/xoa")
	public String deleteModule(@RequestParam(name = "id", required = false) Long id,
			@ModelAttribute(name = "domain") String domain) {

		moduleService.delete(id);
		return "redirect:/school-admin/" + domain + "/hoc-phan/danh-sach?page=1&delete_success";

	}
	
	@GetMapping(value = "/chinh-sua")
	@SchoolAccountCheck
	public String updateModulePage(@RequestParam(name = "id", required = false) Long id,
			Authentication authentication, Model model, @ModelAttribute(name = "domain")String domain) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentAdminSchool("create_module", model);
		School school = schoolService.findByDomain(domain);
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		List<MajorDTO> majorDTOs = majors.stream().map(m -> mapper.map(m, MajorDTO.class)).collect(Collectors.toList());
		model.addAttribute("majors", majorDTOs);
		model.addAttribute("moduleAction", "edit");
		model.addAttribute("domain", domain);
		model.addAttribute("addOrEdit", "edit");
		if(id == null) {
			return "redirect:/school-admin/" + domain + "/hoc-phan/danh-sach?page=1";
		}
		
		Modules modules = moduleService.get(id);
		ModuleDTO moduleDTO = mapper.map(modules, ModuleDTO.class);
		model.addAttribute("module", moduleDTO);
		
		Pageable pageable = PageRequest.of(0, 10);
		Page<Modules> modulePage = moduleService.pageModuleBySchool(school, pageable);
		model.addAttribute("listmodule", modulePage);
		
		model.addAttribute("page", 1);
		model.addAttribute("totalPages", modulePage.getTotalPages());
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}

}
