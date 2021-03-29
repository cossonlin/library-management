package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.User;
import com.cosson.library.librarymanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerUser(User user) {
		if (userRepository.existsById(user.getUserId())) {
			String errorMessage = String.format("User ID %s already exists", user.getUserId());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
		} else {
			return userRepository.save(user);
		}
	}
}
