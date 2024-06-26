package tat.com.eduhub.controller.lecturer.web;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.component.UserHelper;
import tat.com.eduhub.dto.CoursesDTO;
import tat.com.eduhub.dto.LessonDTO;
import tat.com.eduhub.dto.StudentCoursesDTO;
import tat.com.eduhub.entity.CategoryLesson;
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.StudentCourses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CategoryLessonService;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.LessonService;
import tat.com.eduhub.service.StudentCoursesService;

@Controller
@RequestMapping(value = "/lecturer/khoa-hoc")
public class LecturerWebCoursesController {
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private LessonService lessonService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private CategoryLessonService categoryLessonService;
	
	@Autowired
	private StudentCoursesService studentCoursesService;
	
	@GetMapping
	public String coursesPage() {
		return "redirect:/lecturer/khoa-hoc/quan-ly";
	}

	@GetMapping(value = "/them-moi")
	public String addCoursesPage(Model model) {
		BASE_METHOD.FragmentLecturerWeb("add_courses", model);
		model.addAttribute("courses", new CoursesDTO());
		model.addAttribute("act", "addc");
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@PostMapping(value = "/them-moi")
	public String addCourses(Model model, @ModelAttribute(name = "courses") CoursesDTO coursesDTO,
			Authentication authentication, @RequestParam(name = "imageFile", required = false) MultipartFile image) {
		User user = userHelper.getUserLogged(authentication);
		if(coursesDTO.getId() == null) {
			Courses c = mapper.map(coursesDTO, Courses.class);
			c.setUser(user);
			c.setStatus("not_ready");
			if(!image.isEmpty()) {
				String extension = BASE_METHOD.getExtensionFileName(image);
				String fileName = BASE_METHOD.randomString(30) + "." + extension;
				c.setImage(fileName);
				String imagePathUpload = BASE_METHOD.coursesLessonPathUpload(fileName);
				try {
					Files.write(Paths.get(imagePathUpload), image.getBytes());
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("Can't Upload Image (addCourses - LecturerWebAddCoursesController)");
				}
			}
			
			try {
				coursesService.save(c);
				return "redirect:/lecturer/khoa-hoc/quan-ly?added";
			} catch (Exception e) {
				return "redirect:/lecturer/khoa-hoc/them-moi?failed";
			}
		}else {
			
			Courses courses = coursesService.get(coursesDTO.getId());
			String redirectUrl = checkAndReturn(authentication, coursesDTO.getId());
			if(!redirectUrl.isEmpty()) {
				return redirectUrl;
			}
			courses.setUser(user);
			courses.setName(coursesDTO.getName());
			courses.setDescription(coursesDTO.getDescription());
			courses.setShortDescription(coursesDTO.getShortDescription());
			courses.setStatus(coursesDTO.getStatus());
			courses.setOldPrice(coursesDTO.getOldPrice());
			courses.setNewPrice(coursesDTO.getNewPrice());
			courses.setDiscount(coursesDTO.getDiscount());
			courses.setType(coursesDTO.getType());
			courses.setArea(coursesDTO.getArea());
			String oldFileName = courses.getImage();
			if(!image.isEmpty()) {
				String extension = BASE_METHOD.getExtensionFileName(image);
				String fileName = BASE_METHOD.randomString(30) + "." + extension;
				String imagePathUpload = BASE_METHOD.coursesLessonPathUpload(fileName);
				courses.setImage(fileName);
				try {
					Files.write(Paths.get(imagePathUpload), image.getBytes());
					if(oldFileName != null) {
						Files.delete(Paths.get(BASE_METHOD.coursesLessonPathUpload(oldFileName)));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			try {
				coursesService.save(courses);
				return "redirect:/lecturer/khoa-hoc/quan-ly?added";
			} catch (Exception e) {
				return "redirect:/lecturer/khoa-hoc/them-moi?failed";
			}
		}
		
		
		
	}
	
	@GetMapping(value = "/chinh-sua")
	public String editCoursesPage(@RequestParam(name = "courses_id") Long idCourses,
			Model model, Authentication authentication) {
		String redirectUrl = checkAndReturn(authentication, idCourses);
		if(!redirectUrl.isEmpty()) {
			return redirectUrl;
		}
		BASE_METHOD.FragmentLecturerWeb("add_courses", model);
		Courses courses = coursesService.get(idCourses);
		CoursesDTO coursesDTO = mapper.map(courses, CoursesDTO.class);
		model.addAttribute("courses", coursesDTO);
		model.addAttribute("act", "addc");
		model.addAttribute("act", "manage");
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/xem")
	public String viewCourses(Model model, @RequestParam(name = "courses_id", required = false) Long id,
			Authentication authentication) {
		if(id == null) {
			return "redirect:/lecturer/khoa-hoc/quan-ly";
		}else {
			String redirectUrl = checkAndReturn(authentication, id);
			if(!redirectUrl.isEmpty()) {
				return redirectUrl;
			}
		}
		BASE_METHOD.FragmentLecturerWeb("courses_list_lesson", model);
		Courses courses = coursesService.get(id);
		List<Lesson> lessons = lessonService.listByCourses(courses);
		CoursesDTO coursesDTO = mapper.map(courses, CoursesDTO.class);
		List<LessonDTO> lessonDTOs = lessons.stream().map(l -> mapper.map(l, LessonDTO.class)).collect(Collectors.toList());
		model.addAttribute("c", coursesDTO);
		model.addAttribute("l", lessonDTOs);
		model.addAttribute("act", "manage");
		List<CategoryLesson> categoryLessons = categoryLessonService.listByCourses(courses);
		model.addAttribute("categoryLessons", categoryLessons);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/trang-thai")
	public String updateCoursesStatus(@RequestParam(name = "status") String status,
			@RequestParam(name = "courses_id") Long idCourses, Authentication authentication) {
		String redirectUrl = checkAndReturn(authentication, idCourses);
		if(!redirectUrl.isEmpty()) {
			return redirectUrl;
		}
		Courses courses = coursesService.get(idCourses);
		courses.setStatus(status);
		coursesService.save(courses);
		
		return "redirect:/lecturer/khoa-hoc/xem?courses_id=" + idCourses + "&updated";
	}
	
	public String checkAndReturn(Authentication authentication, Long idCourses) {
		User user = userHelper.getUserLogged(authentication);
		boolean checkUserCourses = coursesService.checkCoursesWithUser(user, idCourses);
		if(!checkUserCourses) {
			return "redirect:/lecturer/khoa-hoc";
		}
		return "";
	}
	
	@GetMapping(value = "/hoc-vien")
	public String studentCoursesList(Model model,@RequestParam(name = "courses_id", required = false) Long id,
			Authentication authentication) {
		if(id == null) {
			return "redirect:/lecturer/khoa-hoc/quan-ly";
		}else {
			String redirectUrl = checkAndReturn(authentication, id);
			if(!redirectUrl.isEmpty()) {
				return redirectUrl;
			}
		}
		User user = userHelper.getUserLogged(authentication);
		boolean existsByCoursesUser = coursesService.checkCoursesWithUser(user, id);
		if(!existsByCoursesUser) {
			return "redirect:/lecturer/khoa-hoc/quan-ly";
		}
		BASE_METHOD.FragmentLecturerWeb("list_student", model);
		Courses courses = coursesService.get(id);
		CoursesDTO coursesDTO = mapper.map(courses, CoursesDTO.class);
		model.addAttribute("c", coursesDTO);
		
		List<StudentCourses> studentCourses = studentCoursesService.findByCourses(courses);
		model.addAttribute("studentCourses", studentCourses);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
}
