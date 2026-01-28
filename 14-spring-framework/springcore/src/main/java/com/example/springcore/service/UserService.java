package com.example.springcore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springcore.entity.User;
import com.example.springcore.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(int id, String name, String email) {
        User user = new User(id, name, email);
        userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Map<Integer, User> getAllUsers() {
        return userRepository.findAll();
    }
}

