package com.cosson.library.librarymanagement.controller;

import com.cosson.library.librarymanagement.entity.User;
import com.cosson.library.librarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.registerUser(user));
	}
}
