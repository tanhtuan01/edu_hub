package tat.com.eduhub.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Courses;
import tat.com.eduhub.entity.User;
import tat.com.eduhub.repository.CoursesRepository;
import tat.com.eduhub.service.CoursesService;

@Service
public class CoursesServiceImpl implements CoursesService{

	@Autowired
	private CoursesRepository repository;
	
	@Override
	public Courses save(Courses courses) {
		// TODO Auto-generated method stub
		return repository.save(courses);
	}
	
	@Override
	public List<Courses> findByAuthor(User user) {
		// TODO Auto-generated method stub
		List<Courses> courses = repository.findByUser(user);
		courses.sort(Comparator.comparing(Courses::getId).reversed());
		return courses;
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
	
	@Override
	public Courses get(Long id) {
		// TODO Auto-generated method stub
		Optional<Courses> optional = repository.findById(id);
		return optional.orElse(null);
	}
	
	@Override
	public boolean checkCoursesWithUser(User user, Long id) {
		// TODO Auto-generated method stub
		return repository.existsByUserAndIdEquals(user, id);
	}
	
	@Override
	public List<Courses> listByStatusAndType(String status, String type) {
		// TODO Auto-generated method stub
		return repository.findByStatusAndTypeEquals(status, type);
	}
	
	@Override
	public boolean existsByIdAndStatus(Long idCourses, String status) {
		// TODO Auto-generated method stub
		return repository.existsByIdEqualsAndStatusEquals(idCourses, status);
	}
}
