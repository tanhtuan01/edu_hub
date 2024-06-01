package tat.com.eduhub.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Payment;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.PaymentRepository;
import tat.com.eduhub.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PaymentRepository repository;
	
	@Override
	public boolean existsByUserAndCourses(User user, Courses courses) {
		// TODO Auto-generated method stub
		return repository.existsByUserAndCourses(user, courses);
	}
	
	@Override
	public Long saveAndGetId(Payment payment) {
		// TODO Auto-generated method stub
		Payment pay = repository.save(payment);
		return pay.getId();
	}
	
	@Override
	public Payment get(Long id) {
		// TODO Auto-generated method stub
		Optional<Payment> optional = repository.findById(id);
		return optional.orElse(null);
	}
}
