package tat.com.eduhub.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import tat.com.eduhub.dto.UserDataInfo;

@Component
public class UserHelper {
	
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
}
