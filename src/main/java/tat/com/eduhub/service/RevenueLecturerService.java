package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.RevenueLecturer;
import tat.com.eduhub.entity.User;

public interface RevenueLecturerService {

	RevenueLecturer save(RevenueLecturer revenueLecturer);
	
	List<RevenueLecturer> findByUser(User user);
}
