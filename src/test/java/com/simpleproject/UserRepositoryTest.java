package com.simpleproject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.simpleproject.model.Role;
import com.simpleproject.model.User;
import com.simpleproject.repository.RoleRepository;
import com.simpleproject.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private TestEntityManager entityManager;

//	@Test
//	public void testCreateUser() {
//		User user = new User();
//		user.setEmail("testone@gmail.com");
//		user.setPassword("testonepass");
//		user.setFirstName("Testonefn");
//		user.setLastName("Testoneln");
//
//		User savedUser = repo.save(user);
//
//		User existUser = entityManager.find(User.class, savedUser.getId());
//
//		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
//	}

//	@Test
//	public void testFindUserByEmail() {
//		String email = "testone@gmail.com";
//
//		User user = repo.findByEmail(email);
//
//		assertThat(user).isNotNull();
//
//	}

	@Test
	public void testAddRoleToNewUser() {
		User user = new User();
		user.setEmail("testten@gmail.com");
		user.setPassword("testten");
		user.setFirstName("testtenfn");
		user.setLastName("testtenln");

		Role roleUser = roleRepo.findByName("Customer");
		user.addRole(roleUser);

		User savedUser = userRepo.save(user);

		assertThat(savedUser.getRoles().size()).isEqualTo(2);
	}

	@Test
	public void testAddRolesToExistingUser() {
		User user = userRepo.findById(1L).get();

		Role roleUser = roleRepo.findByName("Admin");
		user.addRole(roleUser);

		Role roleAdmin = new Role(2L);
		user.addRole(roleAdmin);

		User savedUser = userRepo.save(user);

		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
}
