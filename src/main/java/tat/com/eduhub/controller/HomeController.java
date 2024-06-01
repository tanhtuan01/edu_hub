package tat.com.eduhub.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.config.VNPayConfig;
import tat.com.eduhub.dto.GetLessonDTO;
import tat.com.eduhub.dto.LessonDTO;
import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.StudentLessons;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CategoryLessonService;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.LessonService;
import tat.com.eduhub.service.PaymentService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.StudentCoursesService;
import tat.com.eduhub.service.StudentLessonService;
import tat.com.eduhub.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private UserService userService;

	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private CoursesService coursesService;

	@Autowired
	private CategoryLessonService categoryLessonService;

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private StudentCoursesService studentCoursesService;

	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private StudentLessonService studentLessonService;

	@GetMapping
	public String indexWebPage(Model model) {
		BASE_METHOD.FragmentWeb("index_content", model);
		List<Courses> coursesPaid = coursesService.listByStatusAndType("in_progress", "paid");
		List<Courses> coursesFree = coursesService.listByStatusAndType("in_progress", "free");
		model.addAttribute("coursesPaid", coursesPaid);
		model.addAttribute("coursesFree", coursesFree);
		return BASE_FIELD.WEB_LAYOUT;
	}

	@GetMapping(value = "/giang-day")
	public String teachingPage(Model model) {
		BASE_METHOD.FragmentWeb("teaching", model);
		model.addAttribute("user", new UserDTO());
		return BASE_FIELD.WEB_LAYOUT;
	}

	@GetMapping(value = "/quan-ly-ctdt")
	public String manageTPPage(Model model) {
		BASE_METHOD.FragmentWeb("intro_school_manage_tp", model);
		SchoolDTO schoolDTO = new SchoolDTO();
		model.addAttribute("school", schoolDTO);
		return BASE_FIELD.WEB_LAYOUT;
	}

	@PostMapping(value = "/quan-ly-ctdt/tao-tai-khoan")
	public String signUpManage(@ModelAttribute(name = "school") SchoolDTO schoolDTO) {
		schoolService.signUp(schoolDTO);
		return "redirect:/quan-ly-ctdt?success#dang-ky-ngay";
	}

	@PostMapping(value = "/giang-day/tao-tai-khoan")
	public String createLecturerAcc(@ModelAttribute(name = "user") UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		user.setReceiveMail(userDTO.getEmail());
		user.setRoles(Arrays.asList(new Role("ROLE_LECTURER")));
		user.setAvt("no-avatar.png");
		user.setPasswords(passwordEncoder.encode(userDTO.getPasswords()));
		user.setDiploma(userDTO.getDiploma());
		user.setType("system_account");
		userService.create(user);
		return "redirect:/giang-day?success#dang-ky-ngay";
	}

	@GetMapping(value = "/courses/thong-tin-khoa-hoc")
	public String coursesPage(Model model, Authentication authentication,
			@RequestParam(value = "courses_id", required = false) Long idCourses) {
		if (idCourses == null) {
			return "redirect:/";
		}
		boolean checkCourses = coursesService.existsByIdAndStatus(idCourses, "in_progress");
		if (!checkCourses) {
			return "redirect:/";
		}
		String join = "not";
		Courses courses = coursesService.get(idCourses);
		if (authentication != null) {
			User user = userHelper.getUserLogged(authentication);
			boolean checkStudentCourses = studentCoursesService.existsByCoursesAndUser(courses, user);
			if (checkStudentCourses) {
				join = "join";
			}
		}
		model.addAttribute("join", join);
		model.addAttribute("courses", courses);
		List<CategoryLesson> categoryLessons = categoryLessonService.listByCourses(courses);
		model.addAttribute("categoryLessons", categoryLessons);
		BASE_METHOD.FragmentWeb("view_courses", model);
		return BASE_FIELD.WEB_LAYOUT;
	}

	@GetMapping(value = "/buy-now")
	public String buyNowCourses(@RequestParam(name = "courses_id") Long idCourses, Authentication authentication) {
		User user = userHelper.getUserLogged(authentication);
		Courses courses = coursesService.get(idCourses);
		boolean checkCourses = coursesService.existsByIdAndStatus(idCourses, "in_progress");
		if (!checkCourses) {
			return "redirect:/";
		}
		boolean checkStudentCourses = studentCoursesService.existsByCoursesAndUser(courses, user);
		if (!checkStudentCourses) {
			// Chua tham gia hoc
			Long price = courses.getNewPrice();
			return "redirect:/buy-now/pay?price=" + price + "&courses_id=" + idCourses;
		} else {
			// Da tham gia hoc
			return "redirect:/courses/thong-tin-khoa-hoc?courses_id=" + idCourses;
		}

	}

	@GetMapping(value = "/courses/bai-hoc/hoc")
	public String viewLesson(Model model, Authentication authentication,
			@RequestParam(name = "lesson_id", required = false) Long idLesson,
			@RequestParam(name = "courses_id", required = false) Long idCourses) {

		if (idCourses == null || idLesson == null) {
			return "redirect:/e-student/khoa-hoc";
		}
		Courses courses = coursesService.get(idCourses);
		User user = userHelper.getUserLogged(authentication);
		
		boolean checkCourses = coursesService.existsByIdAndStatus(idCourses, "in_progress");
		if(!checkCourses) {
			return "redirect:/";
		}
		
		boolean checkStudentCourses = studentCoursesService.existsByCoursesAndUser(courses, user);
		if (!checkStudentCourses) {
			return "redirect:/courses/thong-tin-khoa-hoc?courses_id=" + idCourses;
		}
		
		
		
		BASE_METHOD.FragmentWeb("view_lesson", model);
		Lesson lesson = lessonService.get(idLesson);
		LessonDTO lessonDTO = mapper.map(lesson, LessonDTO.class);
		model.addAttribute("lesson", lessonDTO);
		model.addAttribute("courses", courses);
		List<Lesson> lessons = lessonService.listByCourses(courses);
		List<LessonDTO> lessonDTOs = lessons.stream().map(l -> mapper.map(l, LessonDTO.class)).toList();
		GetLessonDTO getLessonDTO = new GetLessonDTO(lessonDTOs, lessonDTO);
		model.addAttribute("getLessonDTO", getLessonDTO);
		
		boolean existsByUserCoursesLesson = studentLessonService.existsByUserAndCoursesAndLesson(user, courses, lesson);
		if(!existsByUserCoursesLesson) {
			StudentLessons studentLessons = new StudentLessons();
			studentLessons.setProgess("on_learning");
			studentLessons.setUser(user);
			studentLessons.setCourses(courses);
			studentLessons.setLesson(lesson);
			studentLessonService.save(studentLessons);
		}
		
		return BASE_FIELD.WEB_LAYOUT;
	}

}
