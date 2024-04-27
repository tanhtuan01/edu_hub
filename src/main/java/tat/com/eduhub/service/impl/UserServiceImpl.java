package tat.com.eduhub.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.UserRepository;
import tat.com.eduhub.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = repository.findByEmailEquals(username);
		if(user == null) {
			throw new UsernameNotFoundException("=> Email not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswords(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		// TODO Auto-generated method stub
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public User save(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = new User(
					userDTO.getUserName(), userDTO.getEmail(),
					passwordEncoder.encode(userDTO.getPasswords()), Arrays.asList(new Role("ROLE_STUDENT"))
				);
		user.setType("system_account");
		user.setAvt("logo_user.png");
		return repository.save(user);
	}
	
	@Override
	public Long saveDTOAndGetId(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = new User(
				userDTO.getUserName(), userDTO.getEmail(),
				passwordEncoder.encode(userDTO.getPasswords()), Arrays.asList(new Role("ROLE_STUDENT"))
			);
		user.setAvt("no-avatar.png");
		user.setType("system_account");
		user.setReceiveMail(userDTO.getReceiveMail());
		User userSave = repository.saveAndFlush(user);
		return userSave.getId();
	}
	
	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}
	
	@Override
	public User get(Long id) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = repository.findById(id);
		return userOptional.orElse(null);
	}
	
	@Override
	public Long saveAndGetId(User user) {
		// TODO Auto-generated method stub
		User u = repository.save(user);
		return u.getId();
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmailEquals(email);
	}
	
	@Override
	public Long createLecturerAndGetId(User user) {
		// TODO Auto-generated method stub
		user.setType("system_account");
		User save = repository.saveAndFlush(user);
		return save.getId();
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
}
