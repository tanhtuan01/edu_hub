package tat.com.eduhub.service;

import java.util.List;

import tat.com.eduhub.entity.Major;

public interface MajorService {

	Major save(Major major);
	
	List<Major> listMajorByIdSchool(Long id);
}
