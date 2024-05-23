package com.simpleproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.simpleproject.model.Role;
import com.simpleproject.model.User;
import com.simpleproject.service.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService service;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	};

	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());

		return "signupform";
	}

	@GetMapping("/newrequest")
	public String showNewRequest(Model model) {
		model.addAttribute("user", new User());

		return "newrequests";
	}

	@GetMapping("/userprofile")
	public String showProfile(Model model) {
		model.addAttribute("user", new User());

		return "userprofile";
	}

	@GetMapping("/userprofile/ordernow")
	public String showOrder(Model model) {
		model.addAttribute("user", new User());

		return "ordernow";
	}

	@GetMapping("/userprofile/certificate")
	public String showCertificate(Model model) {
		model.addAttribute("user", new User());

		return "viewcertificate";
	}

	@PostMapping("/processregister")
	public String processRegistration(User user) {
		service.saveUserWithDefaultRole(user);

		return "registersuccess";
	}

	@GetMapping("/admin/listusers")
	public String getAllPages(Model model) {
		return getOnePage(model, 1);
	}

	@GetMapping("/admin/listusers/page/{pageNumber}")
	public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = service.findPage(currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<User> listUsers = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", listUsers);

		model.addAttribute("listUsers", listUsers);

		return "userslist";
	}

	@GetMapping("/admin")
	public String getAdmin(Model model) {
		return showAdminProfle(model, 1);
	}

	@GetMapping("/admin/page")
	public String showAdminProfle(Model model, int currentPage) {
		Page<User> page = service.findPage(currentPage);
		long totalItems = page.getTotalElements();
		model.addAttribute("totalItems", totalItems);

		return "adminprofile";
	}

	@GetMapping("/admin/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = service.get(id);
		List<Role> listRoles = service.getRoles();

		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);

		return "userform";
	}

	@PostMapping("/admin/users/save")
	public String saveUser(User user) {
		service.save(user);
		return "redirect:/admin/listusers";
	}

	@GetMapping("/admin/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		service.deleteById(id);
		return "redirect:/admin/listusers";
	}

}
