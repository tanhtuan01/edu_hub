package tat.com.eduhub.controller.lecturer.web;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import tat.com.eduhub.dto.CoursesDTO;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.LessonService;

@Controller
@RequestMapping(value = "/lecturer/khoa-hoc/quan-ly")
public class LecturerWebManageCourses {

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private LessonService lessonService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@GetMapping
	public String manageCoursesPage(Model model, Authentication authentication) {
		BASE_METHOD.FragmentLecturerWeb("manage_courses", model);
		List<Courses> courses = coursesService.findByAuthor(userHelper.getUserLogged(authentication));
		List<CoursesDTO> coursesDTOs = courses.stream().map(c -> mapper.map(c, CoursesDTO.class))
										.collect(Collectors.toList());
		model.addAttribute("courses", coursesDTOs);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/xoa")
	public String deleteCourses(@RequestParam(name = "id") Long id, Authentication authentication) {
		User user = userHelper.getUserLogged(authentication);
		boolean checkCoursesWithUser = coursesService.checkCoursesWithUser(user, id);
		if(checkCoursesWithUser) {
			coursesService.delete(id);
		}
		return "redirect:/lecturer/khoa-hoc/quan-ly?deleted";
	}
	
	
	
}
