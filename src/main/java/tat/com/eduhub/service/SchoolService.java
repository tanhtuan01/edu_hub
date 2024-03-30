package tat.com.eduhub.service;

import tat.com.eduhub.dto.SchoolDTO;
import tat.com.eduhub.entity.School;

public interface SchoolService {

	School save(SchoolDTO schoolDTO);
	
}
