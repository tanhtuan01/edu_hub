package tat.com.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Payment;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;


public interface PaymentRepository extends JpaRepository<Payment, Long>{

	boolean existsByUserAndCourses(User user, Courses courses);
	
}
