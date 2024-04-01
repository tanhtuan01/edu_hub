package tat.com.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailEquals(String email);
	
}
