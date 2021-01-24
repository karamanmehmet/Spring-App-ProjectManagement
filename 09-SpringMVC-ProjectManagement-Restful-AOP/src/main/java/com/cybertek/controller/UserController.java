package com.cybertek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cybertek.dto.UserDTO;
import com.cybertek.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private UserService userService;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;

	}

	@GetMapping("{username}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserDTO> findByUserName(@PathVariable("username") String username) {

		return ResponseEntity.status(HttpStatus.OK).body(userService.findByUserName(username));
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> add(@RequestBody UserDTO dto) {

		dto.setPassword(passwordEncoder.encode(dto.getPassword()));

		UserDTO userDto = userService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDto);

	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {

		// if password is being set to update
		if (dto.getPassword() != null) {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		}

		UserDTO userDto = userService.update(dto);

		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@PatchMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> deActivate(@RequestBody UserDTO dto) {

		UserDTO userDto = userService.deActivate(dto.getUsername(), false);

		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> delete(@RequestBody UserDTO dto) {

		boolean result = userService.delete(dto.getUsername());

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/list")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<UserDTO>> listAllUsers() {

		return ResponseEntity.status(HttpStatus.OK).body(userService.listAllUsers());

	}

	@GetMapping("/list/role={role}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<UserDTO>> listAllUsersByRole(@RequestParam String role) {

		return ResponseEntity.status(HttpStatus.OK).body(userService.listAllUsersByRole(role));
	}

	@GetMapping("/list/manager")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<UserDTO>> listManagers() {

		return ResponseEntity.status(HttpStatus.OK).body(userService.listManagers());
	}

}
