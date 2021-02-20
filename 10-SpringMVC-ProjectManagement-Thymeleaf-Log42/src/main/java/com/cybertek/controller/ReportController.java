package com.cybertek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.PublicProjectInfoDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ReportService;
import com.cybertek.service.TokenService;
import com.cybertek.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	private TokenService tokenService;

	private ReportService reportService;

	private UserService userService;

	@Autowired
	public ReportController(TokenService tokenService, ReportService reportService, UserService userService) {

		this.tokenService = tokenService;
		this.reportService = reportService;
		this.userService = userService;
	}

	@GetMapping("/general")
	public String manager(Model model) {

		List<PublicProjectInfoDTO> projects = reportService.projectsInfo();
		model.addAttribute("projects", projects);
		return "report/general";
	}

	@GetMapping("/project")
	public String project(Model model) {

		List<UserDTO> managerList = userService.listManagers();
		List<ProjectDTO> projects = reportService.listAllProject();
		model.addAttribute("projects", projects);
		model.addAttribute("managers", managerList);
		model.addAttribute("manager", new UserDTO());
		return "report/project";
	}

	@PostMapping("/project")
	public String project(@ModelAttribute("manager") UserDTO userDTO, BindingResult result, Model model) {

		List<ProjectDTO> projects = new ArrayList<>();

		List<UserDTO> managerList = userService.listManagers();

		if (userDTO.getUsername().equals("")) {
			projects = reportService.listAllProject();

		} else {
			projects = reportService.listAllProjectByManager(userDTO);
		}
		model.addAttribute("projects", projects);
		model.addAttribute("managers", managerList);

		return "report/project";
	}
	
	
	@GetMapping("/task")
	public String task(Model model) {

		List<UserDTO> userList = userService.listAllUsers();
		List<TaskDTO> tasks = reportService.listAllTasks();
		model.addAttribute("tasks", tasks);
		model.addAttribute("users", userList);
		model.addAttribute("user", new UserDTO());
		return "report/task";
	}

	@PostMapping("/task")
	public String task_post(@ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {

		List<TaskDTO> tasks = new ArrayList<>();

		List<UserDTO> userList = userService.listAllUsers();

		if (userDTO.getUsername().equals("")) {
			tasks = reportService.listAllTasks();

		} else {
			tasks = reportService.listAllTasksByUser(userDTO);
		}
		model.addAttribute("tasks", tasks);
		model.addAttribute("users", userList);

		return "report/task";
	}

}
