package com.interview.quiz.Service;

import com.interview.quiz.Entity.Languages;
import com.interview.quiz.Repository.LanguagesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguagesRepository languageRepository;

    public Languages addLanguage(String name) {
        if (languageRepository.existsByName(name)) {
            throw new RuntimeException("Language already exists");
        }
        return languageRepository.save(new Languages(name));
    }

    public List<Languages> getAllLanguages() {
        return languageRepository.findAll();
    }
}
