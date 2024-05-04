package tat.com.eduhub.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import tat.com.eduhub.dto.UserDataInfo;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.UserService;

@Component
public class UserHelper {
	
	@Autowired
	private UserService userService;
	
	public void storeUserDataInfo(UserDataInfo userDataInfo, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    session.setAttribute("userDataInfo", userDataInfo);
	}

	public UserDataInfo getUserDataInfo(HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession();
	    UserDataInfo userDataInfo = (UserDataInfo) session.getAttribute("userDataInfo");
	    model.addAttribute("user", userDataInfo);
	    return userDataInfo;
	}
	
	public User getUserLogged(Authentication authentication) {
		return userService.findByEmail(authentication.getName());
	}
}
