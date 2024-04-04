package tat.com.eduhub.base;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

public class UserSchoolUtils {
	

	public static void populateUserAndSchool(UserService userService,SchoolService schoolService,String domain ,
			Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        School school = schoolService.findByDomain(domain.trim());

        model.addAttribute("domain", domain);
        model.addAttribute("user", user);
        model.addAttribute("school", school);
    }
	
}
