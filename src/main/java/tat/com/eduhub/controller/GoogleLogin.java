package tat.com.eduhub.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.UserService;

@Controller
public class GoogleLogin {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	                                                                                                                                                                                     

	@GetMapping(value = "/afterGoogleLoginSuccess")
	public String googleLoginSuccess(Principal principal, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

	    String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/";
	    
	    UserDataInfo userDataInfo = new UserDataInfo();
		if (principal instanceof OAuth2AuthenticationToken) {
	        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
	       
//	        boolean authenticated = authenticationToken.isAuthenticated();
//	        System.err.println(authenticated);
	        
	        OAuth2User oauth2User = authenticationToken.getPrincipal();
	        String email = oauth2User.getAttribute("email");
//	        System.err.println("Email: " + email);
//	        System.err.println("After Google Login Success");
	        String name = oauth2User.getAttribute("name");
//	        System.err.println("Name: " + name);
//	        model.addAttribute("name", name);
//	        model.addAttribute("email", email);
	        Optional<String> role = oauth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
	       
	        User findUser = userService.findByEmail(email);
//	        System.err.println(principal.getName());
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
	        userDataInfo.setDomain(domain);
	        userDataInfo.setRole("ROLE_USER");
	        userDataInfo.setLoginMethod("google_account");
	        userDataInfo.setName(name);
	        userHelper.storeUserDataInfo(userDataInfo, request);
	    }
		System.err.println("redirectUrl: " + redirectUrl);
		return "googleLoginSuccess";
	}

	
	
	
}
