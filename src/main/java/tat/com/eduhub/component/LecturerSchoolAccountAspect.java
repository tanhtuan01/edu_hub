package tat.com.eduhub.component;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.TeacherOfSchoolService;

@Aspect
@Component
public class LecturerSchoolAccountAspect {
	
	@Autowired
	private TeacherOfSchoolService tosService;

	@AfterReturning(value = "@annotation(LecturerSchoolAccountCheck)", returning = "returnValue")
	public void checkAccountAfterReturning(JoinPoint joinPoint, Object returnValue) throws IOException {
	    if (returnValue != null && returnValue.equals(BASE_FIELD.LECTURER_SCHOOL_LAYOUT)) {
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
	                boolean validAccount = tosService.existsByUserAndSchoolLecturer(user, school);
	                if (!validAccount || !school.getStatus().equals("is_active")) {
	                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	                    response.sendRedirect("/dang-xuat");
	                }
	            }else {
	            	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                    response.sendRedirect("/dang-xuat");
	            }
	        }
	    }
	}
	
}
