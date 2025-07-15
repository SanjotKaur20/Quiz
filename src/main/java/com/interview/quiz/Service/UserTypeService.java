package com.interview.quiz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.quiz.Entity.UserType;
import com.interview.quiz.Repository.UserTypeRepository;

@Service
public class UserTypeService {
	private final UserTypeRepository userTypeRepository;

	@Autowired
	public UserTypeService(UserTypeRepository usersTypeRepository) {
		this.userTypeRepository = usersTypeRepository;
	}
	public List<UserType>getAll(){
		return userTypeRepository.findAll();
		}


}
