package com.interview.quiz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.quiz.Entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    List<Question> findByQuizId(Long quizId);  // <-- Add this line

}
