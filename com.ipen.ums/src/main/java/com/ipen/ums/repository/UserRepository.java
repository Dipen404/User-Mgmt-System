package com.ipen.ums.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipen.ums.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	List<User> findByFirstName(String firstName);

	List<User> findByLastName(String lastName);

	List<User> findByEmail(String email);
}
