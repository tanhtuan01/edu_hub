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

	        boolean error = false;
	        if (model != null) {
	            User user = (User) model.getAttribute("user");
	            School school = (School) model.getAttribute("school");
	            
	            if (user != null && school != null) {
	                boolean validAccount = tosService.existsByUserAndSchoolAdmin(user, school);

	                if (!validAccount) {
	                    error = true;
	                }
	            }else {
	            	error = true;
	            }
	        }else {
	        	error = true;
	        }
	        if(error) {
	        	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                response.sendRedirect("/dang-xuat");
	        }
	    }
	}
	
	
}
