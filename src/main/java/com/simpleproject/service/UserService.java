package com.simpleproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.simpleproject.model.Role;
import com.simpleproject.model.User;
import com.simpleproject.repository.RoleRepository;

@Service
public class UserService {

	@Autowired
	private com.simpleproject.repository.UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	public void saveUserWithDefaultRole(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		Role roleUser = roleRepo.findByName("Customer");
		user.addRole(roleUser);
		userRepo.save(user);
	}

	public void save(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}

	public List<User> listAll() {
		return userRepo.findAll();
	}

	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}

	public User get(Long id) {
		return userRepo.findById(id).get();
	}

	public List<Role> getRoles() {
		return roleRepo.findAll();
	}

	public Page<User> findPage(int pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber - 1, 7);
		return userRepo.findAll(pageable);
	}

}
