package com.dhlee.jpa.custom;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	private ApiUserService apiUserService;

	@GetMapping
	public List<CustomUser> getAllUsers() {
		return apiUserService.getAllUsers();
	}

	@GetMapping("/{id}")
	public Optional<CustomUser> getUserById(@PathVariable("id") Long id) {
		return apiUserService.getUserById(id);
	}

	@PostMapping
	public CustomUser createUser(@RequestBody CustomUser user) {
		return apiUserService.createUser(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		apiUserService.deleteUser(id);
	}
}