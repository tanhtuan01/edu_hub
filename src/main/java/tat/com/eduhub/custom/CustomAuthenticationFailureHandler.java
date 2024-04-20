//package tat.com.eduhub.custom;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//	private RequestCache requestCache;
//
//    public CustomAuthenticationFailureHandler(RequestCache requestCache) {
//        this.requestCache = requestCache;
//    }
//	
//	@Override
//	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException exception) throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		 // Lấy giá trị redirectUrl trước đó (nếu có)
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/"; 
//
//        // Lấy giá trị name từ thông tin đăng nhập
//        String name = request.getParameter("username");
//
//        // Xử lý sau khi đăng nhập thất bại
//        System.err.println("Login Failure: Email: " + name);
//        System.err.println("URL: " + redirectUrl);
//
//        // Điều hướng đến URL mong muốn
//        response.sendRedirect("/dang-nhap?login");
//	}
//
//}
