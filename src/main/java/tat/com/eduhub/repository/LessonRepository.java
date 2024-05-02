package tat.com.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tat.com.eduhub.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long>{

}
