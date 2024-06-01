package tat.com.eduhub.service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.Lesson;
import tat.com.eduhub.entity.StudentLessons;
import tat.com.eduhub.entity.User;

public interface StudentLessonService {

	boolean existsByUserAndCoursesAndLesson(User user, Courses courses, Lesson lesson);
	
	StudentLessons save(StudentLessons studentLessons);
	
	StudentLessons get(Long id);
}
