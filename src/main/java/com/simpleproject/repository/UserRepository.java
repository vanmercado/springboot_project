package com.simpleproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.simpleproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("Select u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);

}