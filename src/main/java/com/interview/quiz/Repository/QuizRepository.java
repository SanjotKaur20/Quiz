package com.interview.quiz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.quiz.Entity.Quiz;

public interface QuizRepository  extends JpaRepository<Quiz, Long>{
	List<Quiz> findByLanguageId(Long languageId);


}
