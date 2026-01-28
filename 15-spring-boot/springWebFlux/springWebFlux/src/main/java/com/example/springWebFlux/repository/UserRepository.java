package com.example.springWebFlux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.springWebFlux.model.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByEmail(String email);
}
