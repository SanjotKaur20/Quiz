package com.interview.quiz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.quiz.Entity.QuizHistory;
import com.interview.quiz.Entity.User;

public interface QuizHistoryRepository extends JpaRepository<QuizHistory, Long> {
	List<QuizHistory> findByUser(User user);

}
