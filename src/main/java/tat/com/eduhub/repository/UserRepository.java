package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tat.com.eduhub.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailEquals(String email);
	
	 @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER' or r.name = 'ROLE_STUDENT'")
	List<User> findRoleUserStudent();
}
