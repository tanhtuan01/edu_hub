package tat.com.eduhub.custom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	// lấy và giá trị sau khi đăng nhập sau đó điều hướng
	
	private RequestCache requestCache;
	
	public CustomAuthenticationSuccessHandler(RequestCache cache) {
		this.requestCache = cache;
	}
	
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		String email = authentication.getName();
//        request.getSession().setAttribute("email", email);
//
//        // Lấy URL trước đó (nếu có)
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/"; // Trang chủ mặc định
//
//        System.err.println("Email " + email);
//        System.err.println("Redirect Url: " + redirectUrl);
//        // Điều hướng đến URL mong muốn
//        response.sendRedirect(redirectUrl);
//		
//	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	        Authentication authentication) throws IOException, ServletException {
	    String email = authentication.getName();
	    request.getSession().setAttribute("email", email);

	    // Lấy URL trước đó (nếu có)
	    SavedRequest savedRequest = requestCache.getRequest(request, response);
	    String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/";
	    
	    if(redirectUrl.contains("school-admin") && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMINSCHOOL"))||
	    	redirectUrl.contains("school-lecturer") && authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_LECTURERSCHOOL"))) {
	    	response.sendRedirect(redirectUrl);
	    }
	    else {
	    	response.sendRedirect("/dang-xuat");
	    }
	    
	   
	}

	private String extractValueFromUrl(String url, String prefix) {
	    int startIndex = url.indexOf(prefix);
	    if (startIndex != -1) {
	        int endIndex = url.indexOf("/", startIndex + prefix.length());
	        if (endIndex != -1) {
	            return url.substring(startIndex + prefix.length(), endIndex);
	        } else {
	            return url.substring(startIndex + prefix.length());
	        }
	    }
	    return null;
	}
	
	
	
}
