package com.example.springWebFlux.service;

import org.springframework.stereotype.Service;

import com.example.springWebFlux.model.User;
import com.example.springWebFlux.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Mono<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<User> save(User user) {
		return userRepository.save(user);
	}

	public Mono<Void> deleteById(Long id) {
		return userRepository.deleteById(id);
	}

//	 

}
