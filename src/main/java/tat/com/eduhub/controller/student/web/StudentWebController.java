package tat.com.eduhub.controller.student.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/u")
public class StudentWebController {

	@GetMapping(value = {"","/tai-khoan-cua-toi"})
	public String studentHomePage() {
		return "";
	}
	
}

	

