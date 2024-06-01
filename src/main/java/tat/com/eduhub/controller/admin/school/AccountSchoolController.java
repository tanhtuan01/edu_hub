package tat.com.eduhub.controller.admin.school;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tat.com.eduhub.base.BASE_FIELD;
import tat.com.eduhub.base.BASE_METHOD;
import tat.com.eduhub.base.UserSchoolUtils;
import tat.com.eduhub.component.SchoolAccountCheck;
import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.dto.UserDTO;
import tat.com.eduhub.entity.Role;
import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.EmailSenderService;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-admin/{domain}/quan-ly-tai-khoan")
public class AccountSchoolController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private TeacherOfSchoolService tosService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	@GetMapping(value = {"", "/cai-dat"})
	@SchoolAccountCheck
	public String accountSchool(Model model, @PathVariable(name = "domain") String domain,
			Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentAdminSchool("account_school", model);
		
		User u = userService.findByEmail(authentication.getName());
		UserDTO userDTO = mapper.map(u, UserDTO.class);
		model.addAttribute("u", userDTO);
		
		School school = schoolService.findByDomain(domain);
		SchoolDTO schoolDTO = mapper.map(school, SchoolDTO.class);
		model.addAttribute("s", schoolDTO);
		
		BASE_METHOD.titleAndAction("Thay đổi thông tin", "account", model);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/cap-nhat")
	public String updateAccount(@ModelAttribute(name = "u") UserDTO userDTO,
			@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "newAvt", required = false) MultipartFile newAvt) throws IOException {
		User user = userService.findByEmail(userDTO.getEmail());
		user.setUserName(userDTO.getUserName());
		user.setReceiveMail(userDTO.getReceiveMail());
		
		if(newAvt.getOriginalFilename().length() == 0) {
			//System.err.println("DONT CHOOSE AVT");
		}else {
			String oldImage = user.getAvt();
			String fileName = BASE_METHOD.randomString(18) + BASE_METHOD.createStrDateNow() +"." + BASE_METHOD.getExtensionFileName(newAvt); 
			user.setAvt(fileName);
			try {
				Files.write(Paths.get(BASE_METHOD.schoolImagePathUpload(fileName)), newAvt.getBytes());
				if(!oldImage.equals("no-avatar.png")) {
					Files.delete(Paths.get(BASE_METHOD.schoolImagePathUpload(oldImage)));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			//System.err.println("CHOOSED AVT");
		}
		Long idSave = userService.saveAndGetId(user);
		if(idSave != null) {
			return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan?updated";
		}else {
			return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan?errorupdate";
		}
		
	}
	
	@PostMapping(value = "/cap-nhat-tt-truong")
	public String updateSchoolInfo(@ModelAttribute(name = "s") SchoolDTO schoolDTO,
			@ModelAttribute(name = "domain")String domain, @RequestParam(name = "newLogo")MultipartFile newLogo) {
		
		School school = schoolService.findByDomain(domain);
		
		if(newLogo.getOriginalFilename().length() > 0) {
			String oldImage = school.getLogo();
			String fileName = BASE_METHOD.randomString(18) + BASE_METHOD.createStrDateNow() +"." + BASE_METHOD.getExtensionFileName(newLogo); 
			school.setLogo(fileName);
			try {
				Files.write(Paths.get(BASE_METHOD.schoolImagePathUpload(fileName)), newLogo.getBytes());
				if(!oldImage.equals("defaultLogoSchool.png")) {
					Files.delete(Paths.get(BASE_METHOD.schoolImagePathUpload(oldImage)));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		school.setAddress(schoolDTO.getAddress());
		school.setEmail(schoolDTO.getEmail());
		school.setHotline(schoolDTO.getHotline());
		schoolService.save(school);
		return "redirect:/school-admin/"+domain+"/quan-ly-tai-khoan?updated";
	}
	

	@GetMapping(value = "/giang-vien")
	@SchoolAccountCheck
	public String lecturerAccountPage(Model model, Authentication authentication, 
			@PathVariable(name = "domain") String domain,
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "size", defaultValue = "5", required = false) int size) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentAdminSchool("lecturer", model);
		model.addAttribute("userDTO", new UserDTO());
		BASE_METHOD.titleAndAction("Giảng viên trường", "lecturer", model);
		setDataLecturer(domain, model, page, size);
		
		return BASE_FIELD.SCHOOL_ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/giang-vien/them")
	public String saveLecturerAccount(@ModelAttribute(name = "domain") String domain,
			@ModelAttribute(name = "userDTO") UserDTO userDTO) {
		School school = schoolService.findByDomain(domain);
		User user = mapper.map(userDTO, User.class);
		String password = BASE_METHOD.randomString(5) + BASE_METHOD.createStrDateNow();
		user.setPasswords(passwordEncoder.encode(password));
		user.setAvt("default_lecturer_avt.png");
		user.setRoles(Arrays.asList(new Role("ROLE_LECTURERSCHOOL")));
		user.setReceiveMail(userDTO.getSendToEmail());
		Long idUserSave = userService.createLecturerAndGetId(user);
		TeacherOfSchool teacherOfSchool = new TeacherOfSchool();
		teacherOfSchool.setSchool(school);
		teacherOfSchool.setUser(userService.get(idUserSave));
		tosService.save(teacherOfSchool);
		emailSenderService.sendEmail(userDTO.getSendToEmail(), "Tài khoản từ " + school.getName() + " trên EduHub", 
				"Đây là tài khoản dành cho Giảng viên của bạn:  " + "\n" + "\tTên đăng nhập: " + userDTO.getEmail() + "\n"
				+"\tMật khẩu: " + password+ "\n"
				+"Đây là đường dẫn đến tài khoản của bạn: http://localhost:2024/school-lecturer/hunre.edu.vn/"
				+"\nTài khoản được tạo từ: " + school.getName());
		return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan/giang-vien?sent";
	}
	
	@GetMapping(value = "/giang-vien/dat-lai-mat-khau")
	public String resetLecturerAccountPassword(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id") Long id){
		User user = userService.get(id);
		String password = BASE_METHOD.randomString(5) + BASE_METHOD.createStrDateNow();
		user.setPasswords(passwordEncoder.encode(password));
		School school = schoolService.findByDomain(domain);
		System.err.println(user.toString());
		Long idSave = userService.createLecturerAndGetId(user);
		
		if(idSave != null) {
			emailSenderService.sendEmail(user.getReceiveMail(), "Tài khoản từ " + school.getName() + " trên EduHub", 
					"Mật khẩu của bạn đã được đặt lại:  " + "\n" + "\tTên đăng nhập: " + user.getEmail() + "\n"
					+"\tMật khẩu: " + password+ "\n"
					+"Đây là đường dẫn đến tài khoản của bạn: http://localhost:2024/school-lecturer/hunre.edu.vn/"
					+"\nTài khoản được tạo từ: " + school.getName());
			return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan/giang-vien?sent";
		}
		
		
		
		
		return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan/giang-vien";
	}
	
	@GetMapping(value = "/giang-vien/xoa")
	public String deleteLecturerAccount(@ModelAttribute(name = "domain") String domain,
			@RequestParam(name = "id") Long id) {
		try {
			tosService.delete(id);
		} catch (Exception e) {
			System.err.println("LOOIX: " +e.getMessage());
		}
		return "redirect:/school-admin/" + domain + "/quan-ly-tai-khoan/giang-vien?deleted";
	}
	
	public void setDataLecturer(String domain, Model model, int page, int size) {
		
		School school = schoolService.findByDomain(domain);
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<TeacherOfSchool> pageTos = tosService.pageTeacherOfSchool(school, pageable);
		
		model.addAttribute("totalPages", pageTos.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("tosPage", pageTos);
		
	}
	
}
