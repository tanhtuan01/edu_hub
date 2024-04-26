package tat.com.eduhub.controller.student.school;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.StudentAccountCheck;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/student/{domain}")
public class StudentSchoolController {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping(value = {"/","/trang-chu",""})
	@StudentAccountCheck
	public String studentSchoolHomePage(Model model, HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@PathVariable(name = "domain") String domain) {
		
		BASE_METHOD.FragmentStudentSchool("training_program", model);
	    
		getDataUserAfterGoogleLogin(response, request, principal);
		model.addAttribute("domain", domain);
		userHelper.getUserDataInfo(request, model);
		
		model.addAttribute("act", "index");
		
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	@GetMapping(value = "/tai-lieu")
	@StudentAccountCheck
	public String studentDocumentPage(Model model, HttpServletRequest request, 
			HttpServletResponse response, Principal principal, @PathVariable(name = "domain") String domain) {
		BASE_METHOD.FragmentStudentSchool("document", model);
		getDataUserAfterGoogleLogin(response, request, principal);
		userHelper.getUserDataInfo(request, model);
		model.addAttribute("act", "document");
		return BASE_FIELD.STUDENT_SCHOOL_LAYOUT;
	}
	
	public void getDataUserAfterGoogleLogin(HttpServletResponse response, HttpServletRequest request,
			Principal principal) {
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
	        }else {
	        	System.err.println("Have System Account");
	        }
	        
	        userDataInfo.setEmail(email);
	        String domain = BASE_METHOD.extractValueFromEmail(email);
	        if(domain.equals("gmail.com")) {
	        	try {
					response.sendRedirect("/dang-xuat");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        userDataInfo.setDomain(domain);
	        userDataInfo.setRole("ROLE_USER");
	        userDataInfo.setLoginMethod("google_account");
	        userDataInfo.setName(name);
	        userHelper.storeUserDataInfo(userDataInfo, request);
	    }
	}
}
