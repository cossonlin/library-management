package com.cosson.library.librarymanagement.service;

import com.cosson.library.librarymanagement.entity.User;
import com.cosson.library.librarymanagement.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
	private UserService userService;
	private UserRepository userRepository = mock(UserRepository.class);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.userService = new UserService(userRepository);
	}

	@Test
	void registerUser_ShouldReturnUser_WhenUserIdIsNew() {
		User user = new User();
		user.setUserId("id");
		user.setUserName("name");
		when(userRepository.save(any(User.class))).thenReturn(user);

		User createdUser = userService.registerUser(user);
		assertEquals(user.getUserId(), createdUser.getUserId());
		assertEquals(user.getUserName(), createdUser.getUserName());
	}

	@Test()
	void registerUser_ShouldThrowException_WhenUserIdExists() {
		User user = new User();
		user.setUserId("id");
		user.setUserName("name");
		when(userRepository.existsById(anyString())).thenReturn(true);
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(user));
		assertEquals("User ID id already exists", exception.getReason());
	}
}
