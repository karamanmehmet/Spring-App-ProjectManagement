package com.cybertek.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cybertek.configuration.UserPrincipal;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.UserService;
import com.cybertek.util.Status;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private ProjectService projectService;

	private UserService userService;

	@Autowired
	public ProjectController(ProjectService projectService, UserService adminService) {

		this.projectService = projectService;
		this.userService = adminService;
	}

	@GetMapping
	public String add(Model model) {

		
		
		
		
		List<ProjectDTO> projects = projectService.listAllProjects();

		model.addAttribute("project", new ProjectDTO());
		model.addAttribute("projects", projects);
		model.addAttribute("managers", userService.listManagers());

		return "project/add";
	}

	@PostMapping
	public String insert(@ModelAttribute("project") @Valid ProjectDTO project, BindingResult result, Model model) {

		projectService.save(project);

		List<ProjectDTO> projects = projectService.listAllProjects();

		model.addAttribute("project", new ProjectDTO());
		model.addAttribute("projects", projects);
		model.addAttribute("managers", userService.listManagers());

		return "project/add";
	}

	@GetMapping("/edit/{projectcode}")
	public String edit(@PathVariable("projectcode") String projectcode, Model model) {

		
		
		
		
		ProjectDTO project = projectService.getByProjectCode(projectcode);
		List<ProjectDTO> projects = projectService.listAllProjects();

		model.addAttribute("project", project);
		model.addAttribute("projects", projects);
		model.addAttribute("managers", userService.listManagers());

		return "project/update";
	}

	@PostMapping("/edit/{projectcode}")
	public String update(@PathVariable("projectcode") String projectcode,
			@ModelAttribute("project") ProjectDTO projectDTO, BindingResult result, Model model) {

		 UserPrincipal principal=	(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		System.out.println("User :" + principal.getUsername());
		
		
		ProjectDTO project = projectService.update(projectDTO);

		List<ProjectDTO> projects = projectService.listAllProjects();
		model.addAttribute("project", project);
		model.addAttribute("projects", projects);
		model.addAttribute("managers", userService.listManagers());

		return "project/update";
	}

	@GetMapping("/delete/{projectcode}")
	public String delete(@PathVariable("projectcode") String projectcode, Model model) {

		projectService.delete(projectcode);

		return "redirect:/project";
	}

	@GetMapping("/complete/{projectcode}")
	public String complete(@PathVariable("projectcode") String projectcode, Model model) {

		ProjectDTO project = projectService.getByProjectCode(projectcode);
		project.setStatus(Status.COMPLETED);
		projectService.update(project);

		return "redirect:/project";
	}

	@GetMapping("/manager/complete")
	public String manager(Model model) {

		List<ProjectDTO> projects = projectService.listAllProjectDetails();
		model.addAttribute("projects", projects);
		return "manager/projects";
	}

	@GetMapping("/manager/complete/{projectcode}")
	public String manager_completed(@PathVariable("projectcode") String projectcode, Model model) {

		ProjectDTO project = projectService.getByProjectCode(projectcode);
		project.setStatus(Status.COMPLETED);
		projectService.update(project);

		return "redirect:/project/manager/complete";

	}

}
