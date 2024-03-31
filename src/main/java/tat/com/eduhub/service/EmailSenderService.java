package tat.com.eduhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import tat.com.eduhub.base.BASE_FIELD;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(BASE_FIELD.FROM_EMAIL);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		
		mailSender.send(mailMessage);
	}
	
}
