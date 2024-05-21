package com.ipen.ums.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipen.ums.entity.User;
import com.ipen.ums.exception.ResourceNotFoundException;
import com.ipen.ums.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User getUserByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username))
				.orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
	}

	public List<User> getUsersByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

	public List<User> getUsersByLastName(String lastName) {
		return userRepository.findByLastName(lastName);
	}

	public List<User> getUsersByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setUsername(updatedUser.getUsername());
			user.setDateOfBirth(updatedUser.getDateOfBirth());
			user.setEmail(updatedUser.getEmail());
			user.setFirstName(updatedUser.getFirstName());
			user.setLastName(updatedUser.getLastName());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
	}

	public void deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		userRepository.deleteById(id);
	}

}
