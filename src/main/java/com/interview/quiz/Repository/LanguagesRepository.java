package com.interview.quiz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.quiz.Entity.Languages;

public interface LanguagesRepository extends JpaRepository<Languages, Long>{
    boolean existsByName(String name);

}
