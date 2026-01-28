package com.example.springWebFlux.controller;

import org.springframework.web.bind.annotation.*;

import com.example.springWebFlux.exception.ResourceNotFoundException;
import com.example.springWebFlux.model.User;
import com.example.springWebFlux.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("User not found with id " + id)));
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
