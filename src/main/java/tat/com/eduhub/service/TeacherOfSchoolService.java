package tat.com.eduhub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.School;
import tat.com.eduhub.entity.TeacherOfSchool;
import tat.com.eduhub.entity.User;

public interface TeacherOfSchoolService {

	TeacherOfSchool save(TeacherOfSchool teacherOfSchool);
	
	boolean existsByUserAndSchoolAdmin(User user, School school);
	
	Page<TeacherOfSchool> pageTeacherOfSchool(School school, Pageable pageable);
	
	void delete(Long id);
	
	List<TeacherOfSchool> listTeacherOfSchools(School school);
}
