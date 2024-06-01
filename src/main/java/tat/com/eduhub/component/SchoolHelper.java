package tat.com.eduhub.component;

import org.springframework.stereotype.Component;

import tat.com.eduhub.entity.School;


public class SchoolHelper {

	public int totalMajorsOfSchool(School school) {
		
		return school.getIndustries().stream()
				.mapToInt(idt -> idt.getMajors().size())
				.sum();
		
	}
	
}
