package com.cybertek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cybertek.dto.TaskDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.util.Status;

@Controller
@RequestMapping("/task")
public class TaskController {

	private TaskService taskService;
	private ProjectService projectService;
	private UserService userService;

	@Autowired
	public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {

		this.taskService = taskService;
		this.projectService = projectService;
		this.userService = userService;
	}

	@GetMapping
	public String add(Model model) {

		List<TaskDTO> tasks = taskService.listAllTasks();
		model.addAttribute("task", new TaskDTO());
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);
		return "task/add";
	}

	@PostMapping
	public String insert(@ModelAttribute("task") TaskDTO task, BindingResult result, Model model) {

		taskService.save(task);

		List<TaskDTO> tasks = taskService.listAllTasks();
		model.addAttribute("task", new TaskDTO());
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);

		return "task/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {

		TaskDTO task = taskService.findById(id);

		List<TaskDTO> tasks = taskService.listAllTasks();
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);

		return "task/update";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Long id, @ModelAttribute("task") TaskDTO taskDTO, BindingResult result,
			Model model) {

		TaskDTO task = taskService.update(taskDTO);

		List<TaskDTO> tasks = taskService.listAllTasks();
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);

		return "task/update";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {

		taskService.delete(id);

		return "redirect:/task";
	}

	@GetMapping("/employee")
	public String edit(Model model) {

		List<TaskDTO> tasks = taskService.listAllTasksByStatusIsNot(Status.COMPLETED);
		model.addAttribute("task", new TaskDTO());
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);
		model.addAttribute("statuses", Status.values());
		return "task/update_employee";
	}

	@GetMapping("/employee/edit/{id}")
	public String emplpoyee_update(@PathVariable("id") Long id, Model model) {
		TaskDTO task = taskService.findById(id);
		List<TaskDTO> tasks = taskService.listAllTasksByStatusIsNot(Status.COMPLETED);
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);
		model.addAttribute("statuses", Status.values());
		return "task/update_employee";
	}

	@PostMapping("/employee/edit/{id}")
	public String employee_update(@PathVariable("id") Long id, @ModelAttribute("task") TaskDTO taskDTO,
			BindingResult result, Model model) {


		TaskDTO task = taskService.findById(id);
		task.setStatus(taskDTO.getStatus());
		
		task = taskService.update(task);

		List<TaskDTO> tasks = taskService.listAllTasksByStatusIsNot(Status.COMPLETED);
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listAllUsersByRole("ROLE_USER"));
		model.addAttribute("projects", projectService.listAllProjects());
		model.addAttribute("tasks", tasks);
		model.addAttribute("statuses", Status.values());
		return "task/update_employee";
	}

	@GetMapping("/employee/archive")
	public String emplpoyee_archive(Model model) {

		List<TaskDTO> tasks = taskService.listAllTasksByStatus(Status.COMPLETED);
		model.addAttribute("tasks", tasks);
		return "task/archive_employee";
	}

}
