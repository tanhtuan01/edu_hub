package tat.com.eduhub.controller.student.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CategoryLessonService;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.StudentCoursesService;


@Controller
@RequestMapping(value = "/e-student")
public class StudentWebHomeController {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private StudentCoursesService studentCoursesService;
	
	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private CategoryLessonService categoryLessonService;

	@GetMapping(value = {"","/tai-khoan-cua-toi"})
	public String studentHomePage(Model model) {
		BASE_METHOD.FragmentStudentWeb("index", model);
		model.addAttribute("act", "home");
		return BASE_FIELD.STUDENT_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/khoa-hoc")
	public String studentCourses(Model model, Authentication authentication) {
		BASE_METHOD.FragmentStudentWeb("courses", model);
		model.addAttribute("act", "courses");
		List<StudentCourses> studentCourses = studentCoursesService.findByUser(userHelper.getUserLogged(authentication));
		model.addAttribute("studentCourses", studentCourses);
		return BASE_FIELD.STUDENT_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/khoa-hoc/thong-tin-khoa-hoc")
	public String coursesPage(Model model, Authentication authentication,
			@RequestParam(value = "courses_id", required = false) Long idCourses) {
		if (idCourses == null) {
			return "redirect:/";
		}
		boolean checkCourses = coursesService.existsByIdAndStatus(idCourses, "in_progress");
		if (!checkCourses) {
			return "redirect:/e-student/khoa-hoc";
		}

		User user = userHelper.getUserLogged(authentication);
		Courses courses = coursesService.get(idCourses);
		boolean checkStudentCourses = studentCoursesService.existsByCoursesAndUser(courses, user);
		if(!checkStudentCourses) {
			return "redirect:/e-student/khoa-hoc";
		}
		model.addAttribute("join", "join");
		model.addAttribute("courses", courses);
		List<CategoryLesson> categoryLessons = categoryLessonService.listByCourses(courses);
		model.addAttribute("categoryLessons", categoryLessons);
		BASE_METHOD.FragmentWeb("view_courses", model);
		return BASE_FIELD.WEB_LAYOUT;
	}
}

	

