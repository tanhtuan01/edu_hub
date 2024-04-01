package tat.com.eduhub.component;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {

	public String getUseAuthenticated(Authentication authentication) {
		if(authentication != null) {
			return authentication.getName();
		}
		return "";
	}
	
}
