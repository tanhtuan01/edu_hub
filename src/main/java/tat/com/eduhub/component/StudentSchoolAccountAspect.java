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
import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;

@Aspect
@Component
public class StudentSchoolAccountAspect {
	
	@Autowired
	private SchoolService schoolService;

	@AfterReturning(value = "@annotation(StudentAccountCheck)", returning = "returnValue")
	public void checkAccountAfterReturning(JoinPoint joinPoint, Object returnValue) throws IOException {
	    if (returnValue != null && returnValue.equals(BASE_FIELD.STUDENT_SCHOOL_LAYOUT)) {
	        Object[] args = joinPoint.getArgs();
	        Model model = null;

	        for (Object arg : args) {
	            if (arg instanceof Model) {
	                model = (Model) arg;
	                break;
	            }
	        }
	        
	        if (model != null) {
	            UserDataInfo userDataInfo = (UserDataInfo) model.getAttribute("user");
	            String domain = userDataInfo.getDomain();
	            School school = schoolService.findByDomain(domain);
	            String domainUrl = (String) model.getAttribute("domain");
	            if (school == null || !domain.equals(domainUrl)) {
	            	HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                    response.sendRedirect("/dang-xuat");
	            }
	        }
	    }
	}
	
}
