package com.cybertek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cybertek.annotation.ExecutionTime;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.util.Status;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	private TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {

		this.taskService = taskService;
	}

	@GetMapping("{id}")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<TaskDTO> findById(@PathVariable int id) {

		return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<TaskDTO> insert(@RequestBody TaskDTO task) {

		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
	}

	@PutMapping
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO task) {

		return ResponseEntity.status(HttpStatus.OK).body(taskService.update(task));
	}

	@DeleteMapping
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<Boolean> delete(@RequestBody TaskDTO task) {

		return ResponseEntity.status(HttpStatus.OK).body(taskService.delete(task.getId()));

	}

	@GetMapping("/list")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<TaskDTO>> list() {

		List<TaskDTO> tasks = taskService.listAllTasks();
		return ResponseEntity.status(HttpStatus.OK).body(tasks);
	}

	@PostMapping("/list/user")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<TaskDTO>> listByUser(@RequestBody UserDTO user) {

		List<TaskDTO> tasks = taskService.listAllByUser(user);
		return ResponseEntity.status(HttpStatus.OK).body(tasks);
	}

	@PostMapping("/list/project")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<TaskDTO>> listByProject(@RequestBody ProjectDTO project) {

		List<TaskDTO> tasks = taskService.listAllByProject(project);
		return ResponseEntity.status(HttpStatus.OK).body(tasks);
	}

	@GetMapping("/list/status={status}")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<TaskDTO>> listAllTasksByStatus(@RequestParam Status status) {

		return ResponseEntity.status(HttpStatus.OK).body(taskService.listAllTasksByStatus(status));
	}

	@GetMapping("/list/statusnot={status}")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<TaskDTO>> listAllTasksByStatusIsNot(@RequestParam Status status) {

		return ResponseEntity.status(HttpStatus.OK).body(taskService.listAllTasksByStatusIsNot(status));
	}

}
