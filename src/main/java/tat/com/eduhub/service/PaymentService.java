package tat.com.eduhub.service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Payment;
import tat.com.eduhub.entity.User;

public interface PaymentService {

	boolean existsByUserAndCourses(User user, Courses courses);
	
	Long saveAndGetId(Payment payment);

	Payment get(Long id);
}
