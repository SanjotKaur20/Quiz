package com.interview.quiz.Service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interview.quiz.Entity.User;
import com.interview.quiz.Entity.UserType;
import com.interview.quiz.Repository.UserRepository;
import com.interview.quiz.Repository.UserTypeRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    // ✅ Login Logic
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ Only USER Sign-Up
    public User addNew(User user) {
        // Force set userType to USER (assuming USER typeName is "USER")
        Optional<UserType> userTypeOptional = userTypeRepository.findByUserTypeNameIgnoreCase("USER");

        if (userTypeOptional.isPresent()) {
            user.setUserType(userTypeOptional.get());
        } else {
            throw new RuntimeException("USER type not found in the database");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user with role USER
        User savedUser = userRepository.save(user);

        System.out.println("USER registered: " + savedUser.getEmail());

        return savedUser;
    }

    // ✅ Save/update user
    public void save(User userData) {
        userRepository.save(userData);
    }

    // Optional utility if needed elsewhere
    public Optional<UserType> getUserTypeByName(String typeName) {
        return userTypeRepository.findByUserTypeNameIgnoreCase(typeName);
    }
    public boolean changeUserPassword(String email, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    
}
