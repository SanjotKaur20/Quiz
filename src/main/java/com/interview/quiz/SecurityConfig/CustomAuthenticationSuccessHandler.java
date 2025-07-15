package com.interview.quiz.SecurityConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.interview.quiz.Entity.CustomUserDetails;
import com.interview.quiz.Entity.User;
import com.interview.quiz.Service.CustomUserDetailsService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userTypeId = customUserDetails.getUser().getUserType().getUserTypeId().longValue();

        System.out.println("The user with email " + customUserDetails.getUsername()
                + " has logged in with user_type_id: " + userTypeId);
        if (userTypeId == 1L) {
        	
            response.sendRedirect("/admin/dashboard");
            System.out.println("SENDING TO ADMIN");
        } else if (userTypeId == 2L) {
            response.sendRedirect("/user/user-dashboard");
        } else {
            response.sendRedirect("/access-denied");
System.out.print("ERROR SENDING00");
        }


    }
}
