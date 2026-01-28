package com.example.springcore.repository;


import org.springframework.stereotype.Repository;

import com.example.springcore.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final Map<Integer, User> userDatabase = new HashMap<>();

    public void save(User user) {
        userDatabase.put(user.getId(), user);
        System.out.println("Saved user: " + user);
    }

    public Optional<User> findById(int id) {
        return Optional.ofNullable(userDatabase.get(id));
    }

    public Map<Integer, User> findAll() {
        return userDatabase;
    }
}

