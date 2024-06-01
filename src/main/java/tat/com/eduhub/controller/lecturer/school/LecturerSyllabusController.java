package tat.com.eduhub.controller.lecturer.school;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.dto.ModuleDTO;
import tat.com.eduhub.dto.SyllabusDTO;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.Syllabus;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.SyllabusService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/de-cuong")
public class LecturerSyllabusController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private SyllabusService syllabusService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@GetMapping(value = {"","/them-moi"})
	@LecturerSchoolAccountCheck
	public String lecturerSyllabusPage(Authentication authentication, Model model, @PathVariable(name = "domain") String domain) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentLecturerSchool("syllabus", model);
		
		setData(model, domain);
		BASE_METHOD.titleAndAction("Đề cương", "syllabus", model);
		model.addAttribute("act", "add");
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
	@PostMapping(value = "/luu")
	public String saveLecturerSyllabus(@ModelAttribute(name = "syllabus") SyllabusDTO syllabusDTO,
			@PathVariable(name = "domain")String domain,
			@RequestParam(name = "syllabusfile", required = false) MultipartFile syllabusFile) {
		int error = 0;
		Modules modules = moduleService.get(syllabusDTO.getIdModule());
		if(syllabusFile != null) {
			String extension = BASE_METHOD.getExtensionFileName(syllabusFile);
//			String createdByString =  BASE_METHOD.removeAccents(modules.getCreatedBy());
			String fileName = BASE_METHOD.removeAccents(syllabusDTO.getName()) + BASE_METHOD.createRandomFileName() +"."+extension;
			String syllabusFilePath = BASE_METHOD.syllabusLecturerPathUpload(domain, fileName);
			try {
				School school = schoolService.findByDomain(domain);
				Syllabus syllabus = mapper.map(syllabusDTO, Syllabus.class);
				syllabus.setFileName(fileName);
				syllabus.setSchool(school);
				Files.write(Paths.get(syllabusFilePath), syllabusFile.getBytes());
				syllabusService.save(syllabus);
			} catch (Exception e) {
				// TODO: handle exception
				error = 1;
			}
		}
		
		return (error == 0) ? "redirect:/school-lecturer/" + domain + "/de-cuong/them-moi?added" :
			"redirect:/school-lecturer/" + domain + "/de-cuong/them-moi?failed";
		
		
	}
	
	@GetMapping(value = "/tim-kiem")
	@LecturerSchoolAccountCheck
	public String listSearchLecturerSyllabusPage(@RequestParam(name = "keyword", required = false, defaultValue =  "") String key,
			@ModelAttribute(name = "domain")String domain,
			@RequestParam(name = "idModule", required = false, defaultValue = "0") Long idModule,
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			Model model, Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		setData(model, domain);
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<Syllabus> syllabusPage ;
		String txt = null;
		if(key.trim().length() == 0 && idModule == 0) {
			return "redirect:/school-lecturer/" + domain + "/de-cuong";
		}
		else if(key.trim().length() == 0 && idModule != 0) {
			syllabusPage = syllabusService.findByIdModule(idModule, pageable);
		}
		else if(key.trim().length() != 0 && idModule == 0) {
			syllabusPage = syllabusService.findByModuleCodeOrModuleName(key, pageable);
			txt = "Tìm kiếm theo: '" + key + "'";
		}
		else {
			syllabusPage = syllabusService.findByIdModuleAndModuleCodeOrModuleName(idModule, key, pageable);
		}
		BASE_METHOD.titleAndAction("Đề cương", "syllabus", model);
		model.addAttribute("listSearch", syllabusPage);
		model.addAttribute("page", page);
		model.addAttribute("key", key);
		model.addAttribute("idModule", idModule);
		model.addAttribute("totalPages", syllabusPage.getTotalPages());
		model.addAttribute("length", syllabusPage.getTotalElements());
		model.addAttribute("act", "search");
		System.err.println("Total Search Records = " + syllabusPage.getTotalElements());
		model.addAttribute("txt", txt);
		BASE_METHOD.FragmentLecturerSchool("syllabus", model);
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
	public void setData(Model model, String domain) {
		School school = schoolService.findByDomain(domain);
		List<Modules> modules = moduleService.findBySchool(school);
		List<ModuleDTO> moduleDTOs = new ArrayList<>();
		for(Modules m : modules) {
			ModuleDTO moduleDTO = new ModuleDTO();
			moduleDTO.setId(m.getId());
			moduleDTO.setName(m.getName());
			moduleDTO.setMajorName(m.getMajor().getMajorName());
			moduleDTO.setCode(m.getCode());
			moduleDTOs.add(moduleDTO);
		}
		model.addAttribute("syllabus", new SyllabusDTO());
		model.addAttribute("module", moduleDTOs);
	}
	
}
