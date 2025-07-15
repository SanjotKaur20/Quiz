package com.interview.quiz.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.quiz.Entity.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer>{
    Optional<UserType> findByUserTypeNameIgnoreCase(String userTypeName);

}
