package com.ipen.ums.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipen.ums.entity.User;
import com.ipen.ums.exception.ResourceNotFoundException;
import com.ipen.ums.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser = userService.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		try {
			User user = userService.getUserByUsername(username);
			return ResponseEntity.ok(user);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with usersname " + username);
		}
	}

	@GetMapping("/first-name/{firstName}")
	public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
		List<User> users = userService.getUsersByFirstName(firstName);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/last-name/{lastName}")
	public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
		List<User> users = userService.getUsersByLastName(lastName);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<List<User>> getUsersByEmail(@PathVariable String email) {
		List<User> users = userService.getUsersByEmail(email);
		return ResponseEntity.ok(users);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		try {
			User updatedUser = userService.updateUser(id, user);
			return ResponseEntity.ok(updatedUser);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.OK).body("User with ID " + id + " deleted successfully");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID " + id);
		}
	}

}
