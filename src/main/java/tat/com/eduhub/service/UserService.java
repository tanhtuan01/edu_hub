package tat.com.eduhub.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.entity.User;

public interface UserService extends UserDetailsService{

	User save(UserDTO userDTO);
	
	Long saveAndGetId(UserDTO userDTO);
}
