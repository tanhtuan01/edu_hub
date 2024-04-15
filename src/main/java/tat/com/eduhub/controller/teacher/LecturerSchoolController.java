package tat.com.eduhub.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LecturerSchoolController {

	@GetMapping(value = "/school-lecturer/{domain}")
	public String lecturerSchoolPage() {
		
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
}
