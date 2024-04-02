package tat.com.eduhub.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tat.com.eduhub.entity.Major;

public interface MajorService {

	Major save(Major major);
	
	List<Major> listMajorByIdSchool(Long id);
	
	Page<Major> pageMajorByIdSchool(Long id, Pageable pageable);
	
	Major get(Long id);
	
	void delete(Long id);
}
