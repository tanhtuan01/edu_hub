package tat.com.eduhub.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.entity.User;

public interface UserService extends UserDetailsService{

	User save(UserDTO userDTO);
	
	Long saveDTOAndGetId(UserDTO userDTO);
	
	User create(User user);
	
	User get(Long id);
	
	Long saveAndGetId(User user);
	
	User findByEmail(String email);
}
