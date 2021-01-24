package com.cybertek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybertek.annotation.ExecutionTime;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.util.Status;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	private ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {

		this.projectService = projectService;

	}

	@GetMapping("{code}")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<ProjectDTO> getByProjectCode(@PathVariable String code) {

		ProjectDTO project = projectService.getByProjectCode(code);
		return ResponseEntity.status(HttpStatus.OK).body(project);
	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@ExecutionTime
	public ResponseEntity<ProjectDTO> add(@RequestBody ProjectDTO project) {

		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.save(project));

	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@ExecutionTime
	public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO project) {

		return ResponseEntity.status(HttpStatus.OK).body(projectService.update(project));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@ExecutionTime
	public ResponseEntity<Boolean> delete(@RequestBody ProjectDTO project) {

		return ResponseEntity.status(HttpStatus.OK).body(projectService.delete(project.getCode()));

	}

	@PatchMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	@ExecutionTime
	public ResponseEntity<ProjectDTO> complete(@RequestBody ProjectDTO dto) {

		ProjectDTO project = projectService.getByProjectCode(dto.getCode());
		project.setStatus(Status.COMPLETED);
		projectService.update(project);

		return ResponseEntity.status(HttpStatus.OK).body(projectService.update(project));
	}

	@GetMapping("/list")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<ProjectDTO>> listAllProjects() {

		return ResponseEntity.status(HttpStatus.OK).body(projectService.listAllProjects());

	}

	@PostMapping("/list/manager")
	@PreAuthorize("isAuthenticated()")
	@ExecutionTime
	public ResponseEntity<List<ProjectDTO>> listAllUsersByRole(@RequestBody UserDTO manager) {

		return ResponseEntity.status(HttpStatus.OK).body(projectService.listAllProjects(manager));
	}

	@GetMapping("/list/detailed")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<ProjectDTO>> listAllProjectDetails() {

		return ResponseEntity.status(HttpStatus.OK).body(projectService.listAllProjectDetails());
	}

}
