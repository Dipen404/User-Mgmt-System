package com.ipen.ums.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipen.ums.entity.User;
import com.ipen.ums.exception.ResourceNotFoundException;
import com.ipen.ums.exception.UniqueConstraintViolationException;
import com.ipen.ums.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		try {
			User createdUser = userService.createUser(user);
			return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
		} catch (UniqueConstraintViolationException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
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

//	@GetMapping("/first-name/{firstName}")
//	public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
//		List<User> users = userService.getUsersByFirstName(firstName);
//		return ResponseEntity.ok(users);
//	}
	@GetMapping("/first-name/{firstName}")
    public ResponseEntity<Page<User>> getUsersByFirstName(
            @PathVariable String firstName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getUsersByFirstName(firstName, pageable);
        return ResponseEntity.ok(users);
    }

//	@GetMapping("/last-name/{lastName}")
//	public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
//		List<User> users = userService.getUsersByLastName(lastName);
//		return ResponseEntity.ok(users);
//	}
	@GetMapping("/last-name/{lastName}")
    public ResponseEntity<Page<User>> getUsersByLastName(
            @PathVariable String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getUsersByLastName(lastName, pageable);
        return ResponseEntity.ok(users);
    }

	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUsersByEmail(@PathVariable String email) {

		return ResponseEntity.ok(userService.getUsersByEmail(email));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		try {
			User updatedUser = userService.updateUser(id, user);
			return ResponseEntity.ok(updatedUser);
		} catch (ResourceNotFoundException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UniqueConstraintViolationException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.status(HttpStatus.OK).body("User with ID " + id + " deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID " + id);
		}
	}

}
