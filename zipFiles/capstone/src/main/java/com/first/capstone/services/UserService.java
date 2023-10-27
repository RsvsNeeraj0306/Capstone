package com.first.capstone.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.entity.User;
import com.first.capstone.respositories.UserRepository;



@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByName(String username) {
        return userRepository.findByName("neeraj");
    }

 
}