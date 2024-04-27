package tat.com.eduhub.controller.student.school;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.StudentAccountCheck;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.Document;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TrainingProgram;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.DocumentService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TrainingProgramService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/student/{domain}")
public class StudentSchoolController {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private TrainingProgramService tpService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DocumentService documentService;
	
	@GetMapping(value = {"/","/trang-chu",""})
//	@StudentAccountCheck
	public String studentSchoolHomePage(Model model, HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@PathVariable(name = "domain") String domain, Authentication authentication) {
		
		BASE_METHOD.FragmentStudentSchool("training_program", model);
	    
		getDataUserAfterGoogleLogin(response, request, principal, authentication);
		model.addAttribute("domain", domain);
		userHelper.getUserDataInfo(request, model);
		
		School school = schoolService.findByDomain(domain);
		List<TrainingProgram> trainingPrograms = tpService.trainingProgramPostedBySchool(school);
		model.addAttribute("tp", trainingPrograms);
		
		model.addAttribute("act", "index");
		
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	@GetMapping(value = "/tai-lieu")
	@StudentAccountCheck
	public String studentDocumentPage(Model model, HttpServletRequest request, 
			HttpServletResponse response, Principal principal, Authentication authentication,
			@PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentStudentSchool("document", model);
		getDataUserAfterGoogleLogin(response, request, principal, authentication);
		userHelper.getUserDataInfo(request, model);
		model.addAttribute("domain", domain);
		model.addAttribute("act", "document");
		model.addAttribute("documents", null);
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	@GetMapping(value = "/chuong-trinh-dao-tao")
	public String trainingProgramPage(@RequestParam(name = "id", required = false) Long id,
			@PathVariable(name = "domain") String domain, Model model, HttpServletRequest request,
			HttpServletResponse response, Principal principal, Authentication authentication,
			@RequestParam(name = "ct") String ct) {
		getDataUserAfterGoogleLogin(response, request, principal, authentication);
		model.addAttribute("domain", domain);
		userHelper.getUserDataInfo(request, model);
		String slugCt = BASE_METHOD.slug(ct);
		
		return "redirect:/student/" + domain + "/chuong-trinh-dao-tao/" + slugCt +"?id="+id;
	}
	
	@GetMapping(value = "/chuong-trinh-dao-tao/{slug}")
	public String viewTiainingProgramPage(Model model, @PathVariable(name = "domain") String domain,
			HttpServletRequest request, HttpServletResponse response, Principal principal,
			Authentication authentication,
			@RequestParam(name = "id") Long id) {
		
		model.addAttribute("domain", domain);
		getDataUserAfterGoogleLogin(response, request, principal, authentication);
		userHelper.getUserDataInfo(request, model);
		if(id == null) {
			return "redirect:/student/" + domain;
		}
		
		TrainingProgram trainingProgram = tpService.get(id);
		model.addAttribute("tp", trainingProgram);
		BASE_METHOD.FragmentStudentSchool("view_training_program.html", model);
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	@GetMapping(value = "/tim-tai-lieu")
	public String searchDocumentOfModule(Model model, @PathVariable(name = "domain") String domain,
			HttpServletRequest request, HttpServletResponse response, Authentication authentication,
			Principal principal, @RequestParam(name = "hocphan") String hocPhan) {
		
		getDataUserAfterGoogleLogin(response, request, principal, authentication);
		userHelper.getUserDataInfo(request, model);
		model.addAttribute("domain", domain);
		School school = schoolService.findByDomain(domain);
		List<Document> documents = documentService.documentStudentbySchool(school.getId(), hocPhan);
		if(documents.size() > 0) {
			model.addAttribute("documents", documents);
		}else {
			model.addAttribute("documents", null);
		}
		BASE_METHOD.FragmentStudentSchool("document", model);
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	public void getDataUserAfterGoogleLogin(HttpServletResponse response, HttpServletRequest request,
			Principal principal, Authentication authentication) {
	    UserDataInfo userDataInfo = new UserDataInfo();
		if (principal instanceof OAuth2AuthenticationToken) {
	        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
	        OAuth2User oauth2User = authenticationToken.getPrincipal();
	        String email = oauth2User.getAttribute("email");
	        String name = oauth2User.getAttribute("name");
	        Optional<String> role = oauth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
	       
	        User findUser = userService.findByEmail(email);
	        if(findUser == null) {
	        	User user = new User();
	 	        user.setEmail(email);
	 	        user.setReceiveMail(email);
	 	        user.setUserName(name);
	 	        user.setAvt("no-avatar.png");
	 	        user.setRoles(Arrays.asList(new Role(role.get())));
	 	        user.setType("google_account");
	 	        user.setPasswords(passwordEncoder.encode(principal.getName()));
	 	        userService.create(user);
	        }
	        
	        userDataInfo.setEmail(email);
	        String domain = BASE_METHOD.extractValueFromEmail(email);
	        userDataInfo.setDomain(domain);
	        userDataInfo.setRole("ROLE_USER");
	        userDataInfo.setLoginMethod("google_account");
	        userDataInfo.setName(name);
	        userHelper.storeUserDataInfo(userDataInfo, request);
	    }
		if(principal instanceof UsernamePasswordAuthenticationToken) {
			String email = authentication.getName();
			User user = userService.findByEmail(email);
			UserDataInfo userDataInfo2 = new UserDataInfo();
			userDataInfo2.setLoginMethod("system_account");
			userDataInfo2.setEmail(email);
			Optional<String> role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
		    String roleName = role.orElse("");
		    userDataInfo2.setRole(roleName);
		    userDataInfo2.setName(user.getUserName());
		    String domain;
		    if(email.contains("@eduhub.com")) {
		    	// đăng nhập bằng tài khoản admin school
		    	domain = email.replace("@eduhub.com", "");
		    }else {
		    	domain = BASE_METHOD.extractValueFromEmail(email);
		    }
		    userDataInfo2.setDomain(domain);
		    userHelper.storeUserDataInfo(userDataInfo2, request);
		}
	}
}
