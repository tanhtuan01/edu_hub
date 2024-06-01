package tat.com.eduhub.custom;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.UserService;
//lấy và giá trị sau khi đăng nhập sau đó điều hướng
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private UserHelper userHelper;

	private TeacherOfSchoolService tosService;
	
	private UserService userService;
	
	private RequestCache requestCache;
	
	public CustomAuthenticationSuccessHandler(RequestCache cache, UserService service, TeacherOfSchoolService tos, UserHelper helper) {
		this.requestCache = cache;
		this.userService = service;
		this.tosService = tos;
		this.userHelper = helper;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	        Authentication authentication) throws IOException, ServletException {
		
		UserDataInfo userDataInfo = new UserDataInfo();
		
		String email = authentication.getName();	    
	    System.err.println("Email: " + email);
	    
	    userDataInfo.setEmail(email);
	    userDataInfo.setLoginMethod("system_account");
	    // Lấy URL trước đó (nếu có)
	    SavedRequest savedRequest = requestCache.getRequest(request, response);

	    String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/";

	    Optional<String> role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst();
	    String roleName = role.orElse("");
//	    authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINSCHOOL"))
	    //System.err.println("Role: " + roleName);
	    String domain = domain(email);
	    
	    //System.err.println("Domain: " + domain);
	    userDataInfo.setDomain(domain);
	    userDataInfo.setRole(roleName);
	    userHelper.storeUserDataInfo(userDataInfo, request);
	    if(redirectUrl.contains("school-admin") && roleName.equals("ROLE_ADMINSCHOOL") ||
	    	redirectUrl.contains("school-lecturer") && roleName.equals("ROLE_LECTURERSCHOOL") ||
	    	redirectUrl.contains("u/") && roleName.equals("ROLE_STUDENT")||
	    	redirectUrl.contains("student") && roleName.equals("ROLE_STUDENT") ||
	    	redirectUrl.contains("lecturer") && roleName.equals("ROLE_LECTURER") ||
	    	redirectUrl.contains("eh-admin") && roleName.equals("ROLE_SUPERADMIN") ) {
	    	response.sendRedirect(redirectUrl);
	    }else if(redirectUrl.contains("school-admin") && !roleName.equals("ROLE_ADMINSCHOOL")||
		    	redirectUrl.contains("school-lecturer") && !roleName.equals("ROLE_LECTURERSCHOOL") ||
		    	redirectUrl.contains("u/") && !roleName.equals("ROLE_STUDENT")||
		    	redirectUrl.contains("student") && !roleName.equals("ROLE_STUDENT") ||
		    	redirectUrl.contains("lecturer") && !roleName.equals("ROLE_LECTURER") ||
		    	redirectUrl.contains("lecturer") && !roleName.equals("ROLE_SUPERADMIN")
	    		) {
	    	response.sendRedirect("/dang-xuat");
	    }
	    
	    else if(roleName.equals("ROLE_ADMINSCHOOL")|| roleName.equals("ROLE_LECTURERSCHOOL") || 
	    		roleName.equals("ROLE_STUDENT") || roleName.equals("ROLE_USER") || roleName.equals("ROLE_LECTURER") ||
	    		roleName.equals("ROLE_SUPERADMIN")) {
	    	response.sendRedirect(redirectUrl);
	    }  
	    else {
	    	response.sendRedirect("/dang-xuat");
	    }
	    
	   
	}

	
	
	private String domain(String email) {
		User user = userService.findByEmail(email);
		TeacherOfSchool teacherOfSchool = tosService.findByUser(user);
		String domain = "";
		if(teacherOfSchool != null) {
			domain = teacherOfSchool.getSchool().getDomain();
		}else {
			domain = BASE_METHOD.extractValueFromEmail(email);
		}
		return domain;
	}
	

	
	
}
