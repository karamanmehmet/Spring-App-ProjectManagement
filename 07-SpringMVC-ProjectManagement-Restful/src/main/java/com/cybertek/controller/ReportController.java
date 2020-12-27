package com.cybertek.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.PublicProjectInfoDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/report")
public class ReportController {

	private ProjectService projectService;
	private UserService userService;
	private TaskService taskService;

	@Autowired
	public ReportController(ProjectService projectService, UserService userService, TaskService taskService) {
		this.projectService = projectService;
		this.userService = userService;
		this.taskService = taskService;
	}

	@GetMapping("/projectsInfo")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<PublicProjectInfoDTO>> projectsInfo() {
 
		List<PublicProjectInfoDTO> list = projectService.listAllProjectDetails().stream().map(o -> {
			return new PublicProjectInfoDTO(o.getCode(), o.getName());
		}).collect(Collectors.toList());

		return ResponseEntity.ok(list);

	}

	@GetMapping("/tasks")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<List<TaskDTO>> listAllTasks() {

		List<TaskDTO> list = taskService.listAllTasks();
		return ResponseEntity.ok(list);
	}

	@PostMapping("/tasksByUser")
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	public ResponseEntity<List<TaskDTO>> listAllTasksByUser(@RequestBody UserDTO user) {

		UserDTO userDTO = userService.findByUserName(user.getUsername());
		List<TaskDTO> list = taskService.listAllByUser(userDTO);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/projects")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<ProjectDTO>> listAllProject() {

		List<ProjectDTO> list = projectService.listAllProjectDetails();
		return ResponseEntity.ok(list);
	}

	@PostMapping("/managerProjects")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<ProjectDTO>> listAllProjectByManager(@RequestBody UserDTO user) {

		UserDTO manager = userService.findByUserName(user.getUsername());
		List<ProjectDTO> list = projectService.listAllProjects(manager);
		return ResponseEntity.ok(list);
	}
}
