package com.cybertek.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;

@Controller
@RequestMapping("/admin")
public class UserController {

	private UserService userService;

	private RoleService roleService;

	private PasswordEncoder passwordEncoder;

	private HttpSession httpSession;

	@Autowired
	public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder,
			HttpSession httpSession) {
		this.userService = userService;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.httpSession = httpSession;
	}

	@GetMapping("")
	public String add(Model model) {

		List<UserDTO> users = userService.listAllUsers();

		model.addAttribute("user", new UserDTO());
		model.addAttribute("users", users);
		model.addAttribute("roles", roleService.listAllRoles());
		return "admin/add";
	}

	@PostMapping("")
	public String insert(@ModelAttribute("user") UserDTO user, BindingResult result, Model model) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userService.save(user);

		List<UserDTO> users = userService.listAllUsers();
		model.addAttribute("user", new UserDTO());
		model.addAttribute("users", users);
		model.addAttribute("roles", roleService.listAllRoles());
		return "admin/add";
	}

	@GetMapping("/edit/{username}")
	public String edit(@PathVariable("username") String username, Model model) {

		UserDTO user = userService.findByUserName(username);
		List<UserDTO> users = userService.listAllUsers();

		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("roles", roleService.listAllRoles());
		return "admin/update";
	}

	@PostMapping("/edit/{username}")
	public String update(@PathVariable("username") String username, @ModelAttribute("user") UserDTO dto,
			BindingResult result, Model model) {

		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		UserDTO user = userService.update(dto);
		List<UserDTO> users = userService.listAllUsers();

		model.addAttribute("user", user);

		model.addAttribute("users", users);
		model.addAttribute("roles", roleService.listAllRoles());

		return "admin/update";
	}

	@GetMapping(value = "/delete/{username}")
	public String delete(@PathVariable("username") String username, Model model) {

		UserDTO user = userService.deActivate(username, false);

		return "redirect:/admin";
	}
	


}
