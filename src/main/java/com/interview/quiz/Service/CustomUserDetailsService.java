package com.interview.quiz.Service;

import com.interview.quiz.Entity.CustomUserDetails;
import com.interview.quiz.Entity.User;
import com.interview.quiz.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository usersRepository) {
		this.userRepository=usersRepository;
	}
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }
}
