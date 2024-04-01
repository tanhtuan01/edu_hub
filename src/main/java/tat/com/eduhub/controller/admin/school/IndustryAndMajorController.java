package tat.com.eduhub.controller.admin.school;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import tat.com.eduhub.dto.IndustryDTO;
import tat.com.eduhub.dto.MajorDTO;
import tat.com.eduhub.entity.Industry;
import tat.com.eduhub.entity.Major;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.service.IndustryService;
import tat.com.eduhub.service.MajorService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/s-admin/{domain}/nganh-chuyen-nganh")
public class IndustryAndMajorController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private IndustryService industryService;
	
	@Autowired
	private MajorService majorService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@GetMapping(value = {"","/them-moi"})
	@SchoolAccountCheck
	public String createIndustryOrMajorPage(@RequestParam(name = "cm", defaultValue = "career", required = false) String cm,
			Model model, Authentication authentication, @PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentAdminSchool("create_industry_major", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		model.addAttribute("domain", domain);
		model.addAttribute("industry", new IndustryDTO());
		model.addAttribute("major", new MajorDTO());
		model.addAttribute("cmAction", cm);
		
		School school = schoolService.findByDomain(domain);
		
		List<Industry> industries = industryService.listIndustryBySchool(school, "ASC");
		List<IndustryDTO> industryDTOs = industries.stream().map(e -> mapper.map(e, IndustryDTO.class)).collect(Collectors.toList());
		model.addAttribute("industries", industryDTOs);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu-nganh")
	public String saveIndustry(@ModelAttribute(name = "industry") IndustryDTO industryDTO, Model model,
			@ModelAttribute(name = "domain") String domain) {
		School school = schoolService.findByDomain(domain);
		Industry industry = mapper.map(industryDTO, Industry.class);
		industry.setSchool(school);
		industryService.save(industry);
		
		return "redirect:/s-admin/"+ domain +"/nganh-chuyen-nganh/danh-sach?sortI=new";
	}
	
	@GetMapping(value = "/danh-sach")
	@SchoolAccountCheck
	public String listIndustryAndMajor(
			@RequestParam(name = "sortM", defaultValue = "ASC", required = false) String sortM,
			@RequestParam(name = "sortI", defaultValue = "ASC", required = false) String sortI,
			@RequestParam(name = "cm", defaultValue = "career", required = false) String cm,
			Model model, Authentication authentication, @ModelAttribute(name = "domain") String domain) {
		
		if(sortI.equals("new") && cm.equals("career")) {
			sortI = "DESC";
		}
		if(cm.equals("major") && sortM.equals("new")) {
			sortM = "DESC";
		}
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		School school = schoolService.findByDomain(domain);
		
		List<Industry> industries = industryService.listIndustryBySchool(school, sortI);
		List<IndustryDTO> industryDTOs = industries.stream().map(e -> mapper.map(e, IndustryDTO.class)).collect(Collectors.toList());
		model.addAttribute("industries", industryDTOs);
		
		model.addAttribute("domain", domain);
		
		
		
		List<Major> majors = majorService.listMajorByIdSchool(school.getId());
		List<MajorDTO> majorDTOs = new ArrayList<>();
		for(Major major : majors) {
			MajorDTO majorDTO = new MajorDTO();
			majorDTO.setId(major.getId());
			majorDTO.setMajorCode(major.getMajorCode());
			majorDTO.setMajorName(major.getMajorName());
			majorDTO.setIndustryName(major.getIndustry().getIndustryName());
			majorDTOs.add(majorDTO);
		}
//		List<MajorDTO> majorDTOs = majors.stream().map(m-> mapper.map(m, MajorDTO.class)).collect(Collectors.toList());
		model.addAttribute("majors", majorDTOs);
		
		BASE_METHOD.FragmentAdminSchool("list_industry_major", model);
		model.addAttribute("cmAction", cm);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu-chuyen-nganh")
	public String saveMajor(@ModelAttribute(name = "major") MajorDTO majorDTO, @ModelAttribute(name = "domain") String domain) {
		Major major = mapper.map(majorDTO, Major.class);
		Industry industry = industryService.get(majorDTO.getIdIndustry());
		major.setIndustry(industry);
		majorService.save(major);
		
		return "redirect:/s-admin/"+domain +"/nganh-chuyen-nganh/danh-sach?cm=major&sortM=new";
	}
}
