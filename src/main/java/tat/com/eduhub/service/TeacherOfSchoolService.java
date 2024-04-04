package tat.com.eduhub.service;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;

public interface TeacherOfSchoolService {

	TeacherOfSchool save(TeacherOfSchool teacherOfSchool);
	
	boolean existsByUserAndSchoolAdmin(User user, School school);
}
