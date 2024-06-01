package tat.com.eduhub.controller.lecturer.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.StudentCoursesDTO;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.RevenueLecturer;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.RevenueLecturerService;
import tat.com.eduhub.service.StudentCoursesService;

@Controller
public class LecturerWebHomeController {

	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private StudentCoursesService studentCoursesService;
	
	@Autowired
	private RevenueLecturerService revenueLecturerService;
	
	@Autowired
	private UserHelper userHelper;
	
	@GetMapping(value = {"/lecturer/trang-chu", "/lecturer"})
	public String lecturerWebHomePage(Model model, Authentication authentication) {
		
		BASE_METHOD.FragmentLecturerWeb("home", model);
		model.addAttribute("act", "home");
		
		User user = userHelper.getUserLogged(authentication);
		List<Courses> courses  = coursesService.findByAuthor(user);
		model.addAttribute("totalCourses", courses.size());
		
		List<RevenueLecturer> revenueLecturers = revenueLecturerService.findByUser(user);
		Long totalPrice = (long) 0;
		for(RevenueLecturer r : revenueLecturers) {
			totalPrice += r.getTotalPrice();
		}
		model.addAttribute("totalPrice", totalPrice);
		int totalStudent = 0;
		List<StudentCoursesDTO> studentCoursesDTOs = new ArrayList<>();
		for(Courses c : courses) {
			List<StudentCourses> sc = studentCoursesService.findByCoursesReversed(c);
			StudentCoursesDTO studentCoursesDTO = new StudentCoursesDTO();
			studentCoursesDTO.setCoursesName(c.getName());
			for(StudentCourses sdc : sc) {
				totalStudent ++;
				studentCoursesDTO.setDate(sdc.getCreatedDate());
				studentCoursesDTO.setName(sdc.getUser().getUserName());
				studentCoursesDTOs.add(studentCoursesDTO);
			}
		}
		model.addAttribute("totalStudent", totalStudent);
		model.addAttribute("listStudentJoin", studentCoursesDTOs);
		
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
}
