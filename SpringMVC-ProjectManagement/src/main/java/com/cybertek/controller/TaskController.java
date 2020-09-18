package com.cybertek.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.servlet.ModelAndView;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;
import com.cybertek.implementation.ProjectServiceImpl;
import com.cybertek.implementation.TaskServiceImpl;
import com.cybertek.implementation.UserServiceImpl;
import com.cybertek.util.Status;

@Controller
@RequestMapping("/task")
public class TaskController {

	private TaskServiceImpl taskService;
	private ProjectServiceImpl projectService;
	private UserServiceImpl userService;

	@Autowired
	public TaskController(TaskServiceImpl taskService,ProjectServiceImpl projectService,UserServiceImpl userService) {

		this.taskService = taskService;
		this.projectService = projectService;
		this.userService = userService;
	}

	@GetMapping
	public String add(Model model) {

		List<TaskDTO> tasks = taskService.getListOfTaskDTO();

		model.addAttribute("tasks", tasks);
		model.addAttribute("task", new TaskDTO());
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects",projectService.getListOfProjectDTO());

		return "task/add";
	}

	@PostMapping
	public String insert(@ModelAttribute("task") TaskDTO task, BindingResult result, Model model) {

		List<TaskDTO> tasks = taskService.getListOfTaskDTO();


		task.setStatus(Status.OPEN);
		task.setStartDateTime(LocalDateTime.now());
		tasks.add(task);

		model.addAttribute("tasks", tasks);
		model.addAttribute("task", new TaskDTO());
		
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects", projectService.getListOfProjectDTO());
		return "task/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {

		List<TaskDTO> tasks = taskService.getListOfTaskDTO();

		TaskDTO task = DataGenerator.getTaskDTOByTask(taskService.getTaskById(id));

		model.addAttribute("tasks", tasks);
		
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects", projectService.getListOfProjectDTO());

		return "task/update";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable("id") Long id, @ModelAttribute("task") TaskDTO taskDTO, BindingResult result,
			Model model) {

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getId() != id).collect(Collectors.toList());

		

		TaskDTO task = DataGenerator.getTaskDTOByTask(taskService.getTaskById(id));
		task.setContent(taskDTO.getContent());
		task.setTitle(taskDTO.getTitle());
		task.setUser(taskDTO.getUser());

		tasks.add(task);

		model.addAttribute("tasks", tasks);
		
		model.addAttribute("task", task);
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects", projectService.getListOfProjectDTO());

		return "task/update";
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView();

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getId() != id).collect(Collectors.toList());

		mv.addObject("tasks", tasks);
		mv.addObject("users", userService.listOfUserDTO());
		mv.addObject("task", new TaskDTO());
		mv.addObject("projects", projectService.getListOfProjectDTO());


		mv.setViewName("task/add");

		return mv;
		// return "task/add";
	}

	@GetMapping("/employee")
	public String edit(Model model) {

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getUser().equals(DataGenerator.activeUser) && x.getStatus() != Status.COMPLETED)
				.collect(Collectors.toList());

		// TaskDTO task = DataGenerator.getTaskDTOByTask(taskService.getTaskById(id));

	
		model.addAttribute("tasks", tasks);
		model.addAttribute("task", new TaskDTO());
		
		model.addAttribute("statuses", DataGenerator.getStatusList());
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects",projectService.getListOfProjectDTO() );

		return "task/update_employee";
	}
	
	@GetMapping("/employee/edit/{id}")
	public String emplpoyee_update(@PathVariable("id") Long id, 
			Model model) {

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getUser().equals(DataGenerator.activeUser) && x.getStatus() != Status.COMPLETED)
				.collect(Collectors.toList());

		TaskDTO task = DataGenerator.getTaskDTOByTask(taskService.getTaskById(id));


		model.addAttribute("tasks", tasks);

		model.addAttribute("task", task);
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects", projectService.getListOfProjectDTO());
		model.addAttribute("statuses", DataGenerator.getStatusList());
		return "task/update_employee";
	}
	
	
	@PostMapping("/employee/edit/{id}")
	public String employee_update(@PathVariable("id") Long id, @ModelAttribute("task") TaskDTO taskDTO, BindingResult result,
			Model model) {

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getId() != id && x.getUser().equals(DataGenerator.activeUser) && x.getStatus() != Status.COMPLETED).collect(Collectors.toList());

		

		TaskDTO task = DataGenerator.getTaskDTOByTask(taskService.getTaskById(id));
		
		task.setStatus(taskDTO.getStatus());

		tasks.add(task);

		model.addAttribute("tasks", tasks);
		
		model.addAttribute("task", task);
		model.addAttribute("statuses", DataGenerator.getStatusList());
		model.addAttribute("users", userService.listOfUserDTO());
		model.addAttribute("projects", projectService.getListOfProjectDTO());

		return "task/update_employee";
	}
	
	
	@GetMapping("/employee/archive")
	public String emplpoyee_archive(Model model) {

		List<TaskDTO> tasks = DataGenerator.converterTaskDTO(taskService.getListOfTask()).stream()
				.filter(x -> x.getUser().equals(DataGenerator.activeUser) && x.getStatus() == Status.COMPLETED)
				.collect(Collectors.toList());

	


		model.addAttribute("tasks", tasks);

		return "task/archive_employee";
	}
	

}
