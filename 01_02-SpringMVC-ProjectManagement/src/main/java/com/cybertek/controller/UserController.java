package com.cybertek.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.implementation.UserServiceImpl;
import com.cybertek.implementation.RoleServiceImpl;

@Controller
@RequestMapping("/admin")
public class UserController {

	private UserServiceImpl userService;

	private RoleServiceImpl roleService;

	@Autowired
	public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping
	public String add(Model model) {

		
		model.addAttribute("user", new UserDTO());

		List<UserDTO> users = userService.listOfUserDTO();

		model.addAttribute("users", users);
		model.addAttribute("roles", roleService.getRoleDTOs());

		return "admin/add";
	}

	@PostMapping
	public String insert(@ModelAttribute("user") UserDTO user, BindingResult result, Model model) {

		if (result.hasErrors()) {

		}

		List<UserDTO> users = userService.listOfUserDTO();

		// DATABASE INSERT OP

		users.add(user);

		// get updated list and put
		model.addAttribute("users", users);
		model.addAttribute("user", new UserDTO());
		model.addAttribute("roles", roleService.getRoleDTOs());
		return "admin/add";
	}

	@GetMapping("/edit/{username}")
	public String edit(@PathVariable("username") String username, Model model) {

		List<UserDTO> users = userService.listOfUserDTO();

		model.addAttribute("user", userService.userByUserName(username));

		model.addAttribute("users", users);

		model.addAttribute("roles", roleService.getRoleDTOs());

		return "admin/update";
	}

	@PostMapping("/edit/{username}")
	public String update(@PathVariable("username") String username, @ModelAttribute("user") UserDTO user,
			BindingResult result, Model model) {

		List<UserDTO> users = userService.updateUsers(user);

		// get updated list and put
		model.addAttribute("users", users);
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.getRoleDTOs());
		return "admin/update";
	}

	@GetMapping(value = "/delete/{username}")
	public String delete(@PathVariable("username") String username, Model model) {

		List<UserDTO> users = userService.deleteUser(username);

		// database delete

		// get updated list and put
		model.addAttribute("users", users);
		model.addAttribute("user", new UserDTO());
		model.addAttribute("roles", roleService.getRoleDTOs());
		return "admin/add";
	}

}
