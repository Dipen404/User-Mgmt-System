package com.ipen.ums.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ipen.ums.entity.User;
import com.ipen.ums.exception.ResourceNotFoundException;
import com.ipen.ums.exception.UniqueConstraintViolationException;
import com.ipen.ums.repository.UserRepository;
import com.ipen.ums.validation.DateValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
		if (existingUserByUsername.isPresent()) {
			throw new UniqueConstraintViolationException("username", "Username already exists");
		}

		Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
		if (existingUserByEmail.isPresent()) {
			throw new UniqueConstraintViolationException("email", "Email already exists");
		}
		String dateOfBirthValidation = DateValidator.validateDateOfBirth(user.getDateOfBirth());
		if (dateOfBirthValidation != null) {
			throw new IllegalArgumentException(dateOfBirthValidation);
		}
		return userRepository.save(user);
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
	}
	public Page<User> getUsersByFirstName(String firstName, Pageable pageable) {
        return userRepository.findByFirstName(firstName, pageable);
    }
//	public List<User> getUsersByFirstName(String firstName) {
//		return userRepository.findByFirstName(firstName);
//	}

//	public List<User> getUsersByLastName(String lastName) {
//		return userRepository.findByLastName(lastName);
//	}
	public Page<User> getUsersByLastName(String lastName, Pageable pageable) {
        return userRepository.findByLastName(lastName, pageable);
    }

	public User getUsersByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
	}

	public User updateUser(Long id, User updatedUser) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		userRepository.findByUsername(updatedUser.getUsername()).ifPresent(user -> {
			if (!user.getId().equals(id)) {
				throw new UniqueConstraintViolationException("username", "Username already exists");
			}
		});
		userRepository.findByEmail(updatedUser.getEmail()).ifPresent(user -> {
			if (!user.getId().equals(id)) {
				throw new UniqueConstraintViolationException("email", "Email already exists");
			}
		});
		existingUser.setUsername(updatedUser.getUsername());
		existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());

		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		userRepository.deleteById(id);
	}

}
