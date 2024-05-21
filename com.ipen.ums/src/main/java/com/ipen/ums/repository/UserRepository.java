package com.ipen.ums.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ipen.ums.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

//	List<User> findByFirstName(String firstName);
	Page<User> findByFirstName(String firstName, Pageable pageable);

//	List<User> findByLastName(String lastName);
	Page<User> findByLastName(String lastName, Pageable pageable);

	Optional<User> findByEmail(String email);
}
