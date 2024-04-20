package tat.com.eduhub.controller;

import java.security.Principal;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleLogin {

	@GetMapping(value = "/afterGoogleLoginSuccess")
	public String googleLoginSuccess(Principal principal, Model model) {
		if (principal instanceof OAuth2AuthenticationToken) {
	        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) principal;
	        
	        boolean authenticated = authenticationToken.isAuthenticated();
	        System.err.println(authenticated);
	        
	        OAuth2User oauth2User = authenticationToken.getPrincipal();
	        String email = oauth2User.getAttribute("email");
	        System.err.println("Email: " + email);
	        System.err.println("After Google Login Success");
	        String name = oauth2User.getAttribute("name");
	        System.err.println("Name: " + name);
	        model.addAttribute("name", name);
	        model.addAttribute("email", email);
	    }
		return "googleLoginSuccess";
	}
	
}
