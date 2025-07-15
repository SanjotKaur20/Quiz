package com.interview.quiz.Service;

import com.interview.quiz.Entity.Languages;
import com.interview.quiz.Entity.Quiz;
import com.interview.quiz.Repository.LanguagesRepository;
import com.interview.quiz.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private LanguagesRepository languageRepository;

    public Quiz addQuiz(String name, String level, Long languageId) {
        Languages language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));
        Quiz quiz = new Quiz(name, level, language);
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
    public Quiz findById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found with ID: " + id));
    }

    
}
