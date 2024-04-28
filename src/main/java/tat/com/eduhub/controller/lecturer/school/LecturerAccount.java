package tat.com.eduhub.controller.lecturer.school;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import tat.com.eduhub.component.LecturerSchoolAccountCheck;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.service.SchoolService;
import tat.com.eduhub.service.UserService;

@Controller
@RequestMapping(value = "/school-lecturer/{domain}/quan-ly-tai-khoan")
public class LecturerAccount {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SchoolService schoolService;
	
	@GetMapping(value = {"","/cai-dat"})
	@LecturerSchoolAccountCheck
	public String lecturerManageAccount(@PathVariable(name = "domain") String domain,
			Model model, Authentication authentication) {
		UserSchoolUtils.populateUserAndSchool(userService, schoolService, domain, authentication, model);
		BASE_METHOD.FragmentLecturerSchool("account", model);
		User user = userService.findByEmail(authentication.getName());
		model.addAttribute("user", user);
		
		return BASE_FIELD.LECTURER_SCHOOL_LAYOUT;
	}
	
	@PostMapping(value = "/cap-nhat")
	public String updateLecturerAccount(@PathVariable(name = "domain") String domain,
			@ModelAttribute(name = "user") User user, @RequestParam(name = "newAvt") MultipartFile newAvt) {
		User u = userService.findByEmail(user.getEmail());
		if(newAvt.getOriginalFilename().length() > 0) {
			String oldImage = u.getAvt();
			String fileName = BASE_METHOD.randomString(18) + BASE_METHOD.createStrDateNow() +"." + BASE_METHOD.getExtensionFileName(newAvt); 
			u.setAvt(fileName);
			try {
				Files.write(Paths.get(BASE_METHOD.lecturerImagePathUpload(fileName)), newAvt.getBytes());
				if(!oldImage.equals("default_lecturer_avt.png")) {
					Files.delete(Paths.get(BASE_METHOD.lecturerImagePathUpload(oldImage)));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		u.setReceiveMail(user.getReceiveMail());
		userService.saveAndGetId(u);
		return "redirect:/school-lecturer/" + domain + "/quan-ly-tai-khoan?updated";
	}
	
}
