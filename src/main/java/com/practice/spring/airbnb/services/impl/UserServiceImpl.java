package com.practice.spring.airbnb.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.exception.ResourceNotFoundException;
import com.practice.spring.airbnb.repositories.UserRepository;
import com.practice.spring.airbnb.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{
    
    
    private final UserRepository userRepository;

    @Override
    public User getUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> 
                    new ResourceNotFoundException("User not found with id "+id));  
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByEmail(username).orElse(null);
           
    }

    

}
