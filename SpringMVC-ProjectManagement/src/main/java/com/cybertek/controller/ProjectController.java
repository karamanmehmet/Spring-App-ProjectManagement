package com.cybertek.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.implementation.UserServiceImpl;
import com.cybertek.implementation.ProjectServiceImpl;
import com.cybertek.util.Status;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private ProjectServiceImpl projectService;

	private UserServiceImpl adminService;

	@Autowired
	public ProjectController(ProjectServiceImpl projectService, UserServiceImpl adminService) {

		this.projectService = projectService;
		this.adminService = adminService;
	}

	@GetMapping
	public String add(Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO();

		model.addAttribute("projects", projects);
		model.addAttribute("project", new ProjectDTO());
		model.addAttribute("managers", adminService.getManagers());

		return "project/add";
	}

	@PostMapping
	public String insert(@ModelAttribute("project") @Valid ProjectDTO project, BindingResult result, Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO();

		project.setStatus(Status.OPEN);

		projects.add(project);

		model.addAttribute("projects", projects);
		model.addAttribute("project", new ProjectDTO());

		return "project/add";
	}

	@GetMapping("/edit/{projectcode}")
	public String edit(@PathVariable("projectcode") String projectcode, Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO();

		ProjectDTO p = DataGenerator.getProjectDTOByProject(projectService.getProjectByProjectCode(projectcode));

		model.addAttribute("project", p);

		model.addAttribute("managers", adminService.getManagers());
		model.addAttribute("projects", projects);

		return "project/update";
	}

	@PostMapping("/edit/{projectcode}")
	public String update(@PathVariable("projectcode") String projectcode,
			@ModelAttribute("project") ProjectDTO projectDTO, BindingResult result, Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO().stream()
				.filter(x -> x.getCode().equals(projectcode) == false).collect(Collectors.toList());

		projects.add(projectService.update(projectDTO));

		model.addAttribute("project", projectDTO);

		model.addAttribute("managers", adminService.getManagers());
		model.addAttribute("projects", projects);

		return "project/update";
	}

	@GetMapping("/delete/{projectcode}")
	public String delete(@PathVariable("projectcode") String projectcode, Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO().stream()
				.filter(x -> x.getCode().equals(projectcode) == false).collect(Collectors.toList());

		model.addAttribute("project", new ProjectDTO());

		model.addAttribute("managers", adminService.getManagers());
		model.addAttribute("projects", projects);

		return "project/add";
	}

	@GetMapping("/complete/{projectcode}")
	public String complete(@PathVariable("projectcode") String projectcode, Model model) {

		List<ProjectDTO> projects = projectService.getListOfProjectDTO().stream()
				.filter(x -> x.getCode().equals(projectcode) == false).collect(Collectors.toList());

		// Project p = projectService.getProjectByProjectCode(projectcode);

		List<ProjectDTO> projects2 = projectService.getListOfProjectDTO().stream()
				.filter(x -> x.getCode().equals(projectcode) == true).collect(Collectors.toList());

		ProjectDTO pDto = projects2.get(0);
		pDto.setStatus(Status.COMPLETED);

		projects.add(pDto);

		model.addAttribute("project", new ProjectDTO());

		model.addAttribute("managers", adminService.getManagers());
		model.addAttribute("projects", projects);

		return "project/add";
	}

	@GetMapping("/manager/complete")
	public String manager(Model model) {

		// Session dan aliniyor
		UserDTO manager = DataGenerator.activeManager;

		List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);

		model.addAttribute("projects", projects);

		return "manager/projects";
	}

	@GetMapping("/manager/complete/{projectcode}")
	public String manager_completed(@PathVariable("projectcode") String projectcode, Model model) {

		// Session dan aliniyor
		UserDTO manager = DataGenerator.activeManager;

		ProjectDTO updateDTO = projectService.getCountedListOfProjectDTO(manager).stream().filter(x -> x.getCode().equals(projectcode) == true).findFirst().get();
				
				
				
		
		updateDTO.setStatus(Status.COMPLETED);

		List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager).stream().filter(x -> x.getCode().equals(projectcode) == false)
				.collect(Collectors.toList());
				
			

		projects.add(updateDTO);

		model.addAttribute("projects", projects);

		return "manager/projects";
	}

}
