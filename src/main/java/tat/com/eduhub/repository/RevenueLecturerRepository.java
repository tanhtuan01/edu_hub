package tat.com.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.RevenueLecturer;
import tat.com.eduhub.entity.User;


public interface RevenueLecturerRepository extends JpaRepository<RevenueLecturer, Long>{

	List<RevenueLecturer> findByUser(User user);
	
}
