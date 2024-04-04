package tat.com.eduhub.controller.admin.school;

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
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.dto.DocumentDTO;
import tat.com.eduhub.dto.ModuleDTO;
import tat.com.eduhub.dto.SyllabusDTO;
import tat.com.eduhub.entity.Document;
import tat.com.eduhub.entity.Modules;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.service.DocumentService;
import tat.com.eduhub.service.ModuleService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-admin/{domain}/tai-lieu")
public class DocumentController {
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private DocumentService documentService;
	
	private ModelMapper mapper = new ModelMapper();

	@GetMapping(value = {"", "/them-moi"})
	@SchoolAccountCheck
	public String documentPage(Model model, Authentication authentication, @PathVariable(name = "domain")String domain) {
		BASE_METHOD.FragmentAdminSchool("document", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		model.addAttribute("act", "add");
		
		setData(model, domain);
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/luu")
	public String saveDocument(@ModelAttribute(name = "domain")String domain,
			@ModelAttribute(name = "document") DocumentDTO documentDTO,
			@RequestParam(name = "documentfile")MultipartFile documentFile) {

		Document document = mapper.map(documentDTO, Document.class);
		String documentFilePath;
		
		String extension = BASE_METHOD.getExtensionFileName(documentFile);
		String fileName = BASE_METHOD.removeAccents(documentDTO.getName()) + BASE_METHOD.createRandomFileName() +"." + extension;
		document.setFileName(fileName);
		document.setModule(moduleService.get(documentDTO.getIdModule()));
		if(documentDTO.getShare().equals("private")) {
			documentFilePath = BASE_METHOD.documentPathUploadPrivate(domain, fileName);
		}else {
			documentFilePath = BASE_METHOD.documentPathUploadPublic(fileName);
		}
		
		try {
			documentService.save(document);
			Files.write(Paths.get(documentFilePath), documentFile.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error: " + e.getMessage());
		}
		return "redirect:/school-admin/" + domain + "/tai-lieu?success";
	}
	
	@GetMapping(value = "/tim-kiem")
	@SchoolAccountCheck
	public String searchDocument(Model model, Authentication authentication,
			@ModelAttribute(name = "domain") String domain, 
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "idModule", required = false) Long idModule,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size) {
		setData(model, domain);
		BASE_METHOD.FragmentAdminSchool("document", model);
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		
		if(idModule == null) {
			return "redirect:/school-admin/" + domain + "/tai-lieu";
		}

		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Document> documentPage;
		String txt = null;
		if(keyword.trim().length() > 0) {
			txt = "Tìm kiếm theo: '"+keyword+"'";
		}
		if(idModule == 0) {
			documentPage = documentService.findDocumentWithIdModuleAndNameModuleAndDocumentType(
					null, keyword, type, pageable);
		}else {
			documentPage = documentService.findDocumentWithIdModuleAndNameModuleAndDocumentType(
					idModule, keyword, type, pageable);
		}
		model.addAttribute("length", documentPage.getTotalElements());
		model.addAttribute("totalPages", documentPage.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("documentPage", documentPage);
		System.err.println("Total document search records: " + documentPage.getTotalElements());
		model.addAttribute("txt", txt);
		model.addAttribute("type", type);
		model.addAttribute("idModule", idModule);
		model.addAttribute("act", "search");
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
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
		model.addAttribute("document", new DocumentDTO());
		model.addAttribute("module", moduleDTOs);
	}
	
}
