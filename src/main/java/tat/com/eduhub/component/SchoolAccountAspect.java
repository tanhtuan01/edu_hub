package tat.com.eduhub.component;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.UserService;

@Aspect
@Component
public class SchoolAccountAspect {
	
	@Autowired
	private TeacherOfSchoolService tosService;
	
//	@Before("@annotation(SchoolAccountCheck)")
//	public void checkAccount(JoinPoint joinPoint) throws IOException {
//		
//		Object[] args = joinPoint.getArgs();
//		Model model = null;
//		
//		for(Object arg : args) {
//			if(arg instanceof Model) {
//				model = (Model) arg;
//				break;
//			}
//		}
//		
//		if(model != null) {
//			User user = (User) model.getAttribute("user");
//			School school = (School) model.getAttribute("school");
//			System.err.println("__________before check");
//			if (user != null && school != null) {
//		        boolean validAccount = tosService.existsByUserAndSchool(user, school);
//		        System.err.println(validAccount + "");
//		        if (!validAccount) {
//		            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//		            response.sendRedirect("/sign-in");
//		        }
//		    }
//		}
//		
//	}

	@AfterReturning(value = "@annotation(SchoolAccountCheck)", returning = "returnValue")
	public void checkAccountAfterReturning(JoinPoint joinPoint, Object returnValue) throws IOException {
	    if (returnValue != null && returnValue.equals(BASE_FIELD.SCHOOL_ADMIN_LAYOUT)) {
	        Object[] args = joinPoint.getArgs();
	        Model model = null;
	        
	        for (Object arg : args) {
	            if (arg instanceof Model) {
	                model = (Model) arg;
	                break;
	            }
	        }
	        
	        if (model != null) {
	            User user = (User) model.getAttribute("user");
	            School school = (School) model.getAttribute("school");
	            
	            if (user != null && school != null) {
	                boolean validAccount = tosService.existsByUserAndSchool(user, school);
	                
	                if (!validAccount) {
	                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	                    response.sendRedirect("/dang-xuat");
	                }
	            }
	        }
	    }
	}
	
	
}
