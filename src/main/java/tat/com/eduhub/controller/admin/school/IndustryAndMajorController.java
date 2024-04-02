package tat.com.eduhub.controller.admin.school;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@RequestMapping(value = "/school-admin/{domain}/nganh-chuyen-nganh")
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
	
	@GetMapping(value = {"/them-moi"})
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
		model.addAttribute("actIndustry", "Thêm");
		model.addAttribute("actMajor", "Thêm");
		
		List<Industry> industries = industryService.listIndustryBySchool(school, "ASC");
		List<IndustryDTO> industryDTOs = industries.stream().map(e -> mapper.map(e, IndustryDTO.class)).collect(Collectors.toList());
		model.addAttribute("industries", industryDTOs);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu-nganh")
	public String saveIndustry(@ModelAttribute(name = "industry") IndustryDTO industryDTO, Model model,
			@ModelAttribute(name = "domain") String domain) {
		School school = schoolService.findByDomain(domain);
	
		if(industryDTO.getId() != null) {
			Industry idt = industryService.get(industryDTO.getId());
			idt.setIndustryName(industryDTO.getIndustryName());
			idt.setIndustryCode(industryDTO.getIndustryCode());
			industryService.save(idt);
		}else {
			Industry industry = mapper.map(industryDTO, Industry.class);
			industry.setSchool(school);
			industryService.save(industry);
		}

		return "redirect:/school-admin/"+ domain +"/nganh-chuyen-nganh/danh-sach/nganh?i_add_success";
	}
	
	@GetMapping(value = {"/danh-sach", "/danh-sach/nganh", "/danh-sach/chuyen-nganh", ""})
	@SchoolAccountCheck
	public String listIndustryAndMajor(HttpServletRequest request,
			@RequestParam(name = "cm", defaultValue = "career", required = false) String cm,
			Model model, Authentication authentication, @ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required =  false) int size,
			@RequestParam(name = "pageM", defaultValue = "1", required = false) int pageM,
			@RequestParam(name = "sizeM", defaultValue = "10", required =  false) int sizeM) {
		
		String requestURI = request.getRequestURI();
		if(requestURI.equals("/school-admin/"+domain+"/nganh-chuyen-nganh/danh-sach/nganh")) {
			cm = "career";
		}
		if(requestURI.equals("/school-admin/"+domain+"/nganh-chuyen-nganh/danh-sach/chuyen-nganh")) {
			cm = "major";
		}
		
		Pageable pageable = PageRequest.of(page - 1, size);
		Pageable pageableM = PageRequest.of(pageM - 1, sizeM);
		
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		School school = schoolService.findByDomain(domain);
		
		Page<Industry> idtPage = industryService.pageIndustryBySchool(school, pageable);
		List<IndustryDTO> industryDTOlist = idtPage.getContent().stream().map(e -> mapper.map(e, IndustryDTO.class)).collect(Collectors.toList());
		Page<IndustryDTO> industryDTOs = new PageImpl<>(industryDTOlist, idtPage.getPageable(), idtPage.getTotalElements());
	
		model.addAttribute("industries", industryDTOs);
		Page<Major> majorPage = majorService.pageMajorByIdSchool(school.getId(), pageableM);
		List<MajorDTO> majorDTOList = new ArrayList<>();
		for(Major major : majorPage) {
			MajorDTO majorDTO = new MajorDTO();
			majorDTO.setId(major.getId());
			majorDTO.setMajorCode(major.getMajorCode());
			majorDTO.setMajorName(major.getMajorName());
			majorDTO.setIndustryName(major.getIndustry().getIndustryName());
			majorDTOList.add(majorDTO);
		}
		
		Page<MajorDTO> majorDTOpage = new PageImpl<>(majorDTOList, majorPage.getPageable(), majorPage.getTotalElements());
		
		model.addAttribute("majors", majorDTOpage);
		
		BASE_METHOD.FragmentAdminSchool("list_industry_major", model);
		model.addAttribute("cmAction", cm);
		model.addAttribute("domain", domain);
		model.addAttribute("totalPages", industryDTOs.getTotalPages());
		model.addAttribute("totalPagesMajor", majorDTOpage.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("pageM", pageM);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu-chuyen-nganh")
	public String saveMajor(@ModelAttribute(name = "major") MajorDTO majorDTO, @ModelAttribute(name = "domain") String domain) {
		Major major = mapper.map(majorDTO, Major.class);
		Industry industry = industryService.get(majorDTO.getIdIndustry());
		major.setIndustry(industry);
		majorService.save(major);
		return "redirect:/school-admin/"+domain +"/nganh-chuyen-nganh/danh-sach/chuyen-nganh?m_add_success";
	}
	
	@GetMapping(value = {"/chinh-sua/nganh", "/chinh-sua/chuyen-nganh"})
	@SchoolAccountCheck
	public String editIndustryPage(@RequestParam(name = "id", required = false)Long id, Model model,
			@PathVariable(name = "domain") String domain, Authentication authentication,
			HttpServletRequest request) {
		if(id == null) {
			return "redirect:/school-admin/" + domain + "/nganh-chuyen-nganh";
		}
		BASE_METHOD.FragmentAdminSchool("create_industry_major", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		School school = schoolService.findByDomain(domain);
		String requestURI = request.getRequestURI();
		model.addAttribute("domain", domain);

		if(requestURI.equals("/school-admin/"+domain+"/nganh-chuyen-nganh/chinh-sua/nganh")) {
			model.addAttribute("cmAction", "career");
			model.addAttribute("major", new MajorDTO());
			Industry industry = industryService.get(id);
			model.addAttribute("industry", industry);
			model.addAttribute("actIndustry", "Chỉnh sửa");
			model.addAttribute("actMajor", "Thêm");
			if(industry == null){
				return "redirect:/school-admin/" + domain + "/nganh-chuyen-nganh";
			}
		}
		if(requestURI.equals("/school-admin/"+domain+"/nganh-chuyen-nganh/chinh-sua/chuyen-nganh")) {
			model.addAttribute("cmAction", "major");
			Major major = majorService.get(id);
			model.addAttribute("industry", new IndustryDTO());
			
			model.addAttribute("actIndustry", "Thêm");
			model.addAttribute("actMajor", "Chỉnh sửa");
			if(major == null){
				return "redirect:/school-admin/" + domain + "/nganh-chuyen-nganh";
			}
			MajorDTO majorDTO = mapper.map(major, MajorDTO.class);
			majorDTO.setIdIndustry(major.getIndustry().getId());
			model.addAttribute("major", majorDTO);
		}
		
		List<Industry> industries = industryService.listIndustryBySchool(school, "ASC");
		List<IndustryDTO> industryDTOs = industries.stream().map(e -> mapper.map(e, IndustryDTO.class)).collect(Collectors.toList());
		model.addAttribute("industries", industryDTOs);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@GetMapping(value = {"/xoa/nganh", "/xoa/chuyen-nganh"})
	public String deleteIndustryOrMajor(@RequestParam(name = "id")Long id, HttpServletRequest request,
			@ModelAttribute(name = "domain")String domain) {
		String requestURI = request.getRequestURI();
		String url = "";
		if(requestURI.equals("/school-admin/" +domain + "/nganh-chuyen-nganh/xoa/nganh")) {
			url = "nganh?i_delete_success";
			industryService.delete(id);
		}
		if(requestURI.equals("/school-admin/" +domain + "/nganh-chuyen-nganh/xoa/chuyen-nganh")) {
			url = "chuyen-nganh?m_delete_success";
			majorService.delete(id);
		}
		
		return "redirect:/school-admin/" + domain + "/nganh-chuyen-nganh/danh-sach/" + url;
	}

}
