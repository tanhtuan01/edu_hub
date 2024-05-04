package tat.com.eduhub.controller.lecturer.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.CoursesService;
import tat.com.eduhub.service.LessonService;

@Controller
@RequestMapping(value = "/lecturer/khoa-hoc/bai-hoc")
public class LecturerWebLessonController {

	@Autowired
	private CoursesService coursesService;
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private UserHelper userHelper;
	
	private ModelMapper mapper = new ModelMapper();
	
	@GetMapping(value = {"","/"})
	public String redirectToCoursesPage() {
		return "redirect:/lecturer/khoa-hoc";
	}
	
	@GetMapping(value = "/them-moi")
	public String addLessonOfCoursesPage(Model model, @RequestParam(name = "courses_id") Long coursesId,
			Authentication authentication) {
		
		if(coursesId == null) {
			return "redirect:/lecturer/khoa-hoc";
		}
		
		User user = userHelper.getUserLogged(authentication);
		boolean checkCoursesWithUser = coursesService.checkCoursesWithUser(user, coursesId);
		if(!checkCoursesWithUser) {
			return "redirect:/lecturer/khoa-hoc/quan-ly";
		}
		LessonDTO lessonDTO = new LessonDTO();
		lessonDTO.setIdCourses(coursesId);
		Courses courses = coursesService.get(coursesId);
		CoursesDTO coursesDTO = mapper.map(courses, CoursesDTO.class);
		BASE_METHOD.FragmentLecturerWeb("add_lesson", model);
		model.addAttribute("l", lessonDTO);
		model.addAttribute("c", coursesDTO);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@GetMapping(value = "/chinh-sua")
	public String deleteLessonPage(@RequestParam(name = "lesson_id") Long idLesson,
			@RequestParam(name = "courses_id") Long idCourses, Authentication authentication,
			Model model) {
		User user = userHelper.getUserLogged(authentication);
		boolean checkUserCourses = coursesService.checkCoursesWithUser(user, idCourses);
		if(!checkUserCourses) {
			return "redirect:/lecturer/khoa-hoc/";
		}
		Courses courses = coursesService.get(idCourses);
		boolean checkLessonIdCourses = lessonService.existsByCoursesAndId(courses, idLesson);
		if(!checkLessonIdCourses) {
			return "redirect:/lecturer/khoa-hoc";
		}
		
		Lesson lesson = lessonService.get(idLesson);
		LessonDTO lessonDTO = mapper.map(lesson, LessonDTO.class);
		CoursesDTO coursesDTO = mapper.map(courses, CoursesDTO.class);
		
		model.addAttribute("l", lessonDTO);
		model.addAttribute("c", coursesDTO);
		
		BASE_METHOD.FragmentLecturerWeb("add_lesson", model);
		return BASE_FIELD.LECTURER_WEB_LAYOUT;
	}
	
	@PostMapping(value = "/them-moi")
	public String addLessonOfCourses(Model model, @ModelAttribute(name = "l") LessonDTO lessonDTO,
			@RequestParam(name = "videoFile") MultipartFile video) {
		
		Courses courses = coursesService.get(lessonDTO.getIdCourses());
		if(lessonDTO.getId() == null) {
			
			Lesson lesson = mapper.map(lessonDTO, Lesson.class);
			
			
			lesson.setCourses(courses);
			
			if(!video.isEmpty()) {

				String extension = BASE_METHOD.getExtensionFileName(video);
				String fileName = "VIDEO." + BASE_METHOD.randomString(30) + "." + extension;
				String filePath = BASE_METHOD.videoPathUpload(fileName);
				lesson.setFileName(fileName);
				try {
					Files.write(Paths.get(filePath), video.getBytes());
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("Lỗi upload video: " + filePath);
				}
			}
			lessonService.save(lesson);
		
			if(courses.getStatus().equals("not_ready")) {
				courses.setStatus("on_ready");
				coursesService.save(courses);
			}
			return "redirect:/lecturer/khoa-hoc/xem?courses_id=" + lessonDTO.getIdCourses() + "&added";
		}
		else {
			Lesson lesson = lessonService.get(lessonDTO.getId());
			lesson.setContent(lessonDTO.getContent());
			lesson.setDescription(lessonDTO.getDescription());
			lesson.setName(lessonDTO.getName());
			lesson.setPreview(lessonDTO.isPreview());
			lesson.setCourses(courses);
			String oldFile = lesson.getFileName();
			if(!video.isEmpty()) {

				String extension = BASE_METHOD.getExtensionFileName(video);
				String fileName = "VIDEO." + BASE_METHOD.randomString(30) + "." + extension;
				String filePath = BASE_METHOD.videoPathUpload(fileName);
				lesson.setFileName(fileName);
				
				try {
					if(oldFile != null) {
						String oldFilePath = BASE_METHOD.videoPathUpload(oldFile);
						Files.delete(Paths.get(oldFilePath));
					}
					Files.write(Paths.get(filePath), video.getBytes());
					
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println("Lỗi upload video: " + filePath);
				}
			}
			lessonService.save(lesson);
			return "redirect:/lecturer/khoa-hoc/xem?courses_id=" + lessonDTO.getIdCourses() + "&updated";
		}
		
		
		
	}
	
	@GetMapping(value = "/xoa")
	public String deleteLesson(@RequestParam(name = "courses_id") Long idCourses,
			@RequestParam(name = "lesson_id") Long idLesson, Authentication authentication) {
		User user = userHelper.getUserLogged(authentication);
		boolean checkUserCourses = coursesService.checkCoursesWithUser(user, idCourses);
		if(!checkUserCourses) {
			return "redirect:/lecturer/khoa-hoc";
		}
		Courses courses = coursesService.get(idCourses);
		boolean coursesAndLesson = lessonService.existsByCoursesAndId(courses, idLesson);
		if(!coursesAndLesson) {
			return "redirect:/lecturer/khoa-hoc/xem?courses_id=" + idCourses;
		}
		
		Lesson lesson = lessonService.get(idLesson);
		String fileName = lesson.getFileName();
		if(fileName != null) {
			try {
				String filePath = BASE_METHOD.videoPathUpload(fileName);
				Files.delete(Paths.get(filePath));
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		lessonService.delete(idLesson);
		List<Lesson> lessons = lessonService.listByCourses(courses);
		if(lessons.size() == 0) {
			courses.setStatus("not_ready");
			coursesService.save(courses);
		}
		
		
		
		return "redirect:/lecturer/khoa-hoc/xem?courses_id=" + idCourses + "&deleted";
	}
}
