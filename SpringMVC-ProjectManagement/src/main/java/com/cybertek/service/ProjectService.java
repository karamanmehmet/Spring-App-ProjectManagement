package com.cybertek.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.implementation.UserServiceImpl;
import com.cybertek.util.Status;
import com.cybertek.implementation.ProjectServiceImpl;
import com.cybertek.implementation.TaskServiceImpl;

@Service
public class ProjectService implements ProjectServiceImpl {

	UserServiceImpl userService;

	TaskServiceImpl taskService;

	@Autowired
	public ProjectService(UserServiceImpl userService, TaskServiceImpl taskService) {
		super();
		this.userService = userService;
		this.taskService = taskService;
	}

	@Override
	public List<Project> getListOfProject() {

		return DataGenerator.getProjects();
	}

	@Override
	public List<Project> getListOfProject(String status) {
		// TODO Auto-generated method stub
		return DataGenerator.getProjects().stream().filter(x -> x.getStatus().equals(status))
				.collect(Collectors.toList());
	}

	@Override
	public List<Project> getListOfProject(User manager) {
		// TODO Auto-generated method stub
		return DataGenerator.getProjects().stream().filter(x -> x.getManager() == manager).collect(Collectors.toList());
	}

	@Override
	public List<Project> getListOfProject(User manager, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project insert(Project project) {
		// TODO Auto-generated method stub
		return project;
	}

	@Override
	public Project update(Project project) {
		// TODO Auto-generated method stub
		return project;
	}

	public ProjectDTO update(ProjectDTO project) {
		// TODO Auto-generated method stub

		ProjectDTO obj = getListOfProjectDTO().stream().filter(x -> x.getCode().equals(project.getCode())).findFirst()
				.get();

		obj.setDetail(project.getDetail());
		obj.setStartDate(project.getStartDate());
		obj.setEndDate(project.getEndDate());
		obj.setManager(project.getManager());
		obj.setName(project.getName());

		obj.setStatus(project.getStatus() != null ? project.getStatus() : obj.getStatus());

		return obj;

	}

	@Override
	public boolean delete(Project project) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public Project getProjectByProjectCode(String projectcode) {
		// TODO Auto-generated method stub

		return DataGenerator.getProjects().stream().filter(x -> x.getCode().equals(projectcode)).findFirst().get();

	}

	@Override
	public List<ProjectDTO> getListOfProjectDTO() {
		List<ProjectDTO> list = getListOfProject().stream().map(x -> {

			return new ProjectDTO(x.getCode(), x.getName(),
					userService.getUserDTOByUsername(x.getManager().getUsername()), x.getStartDate(), x.getEndDate(),
					x.getStatus(), x.getDetail());

		}).collect(Collectors.toList());

		return list;
	}

	@Override
	public ProjectDTO getProjectDTOByProjectCode(String projectcode) {

		return getListOfProject().stream().filter(x -> x.getCode().equals(projectcode)).map(x -> {

			return new ProjectDTO(x.getCode(), x.getName(),
					userService.getUserDTOByUsername(x.getManager().getUsername()), x.getStartDate(), x.getEndDate(),
					x.getStatus(), x.getDetail());

		}).findFirst().get();
	}

	@Override
	public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

		List<ProjectDTO> list = getListOfProject().stream()
				.filter(x -> x.getManager().getUsername().equals(manager.getUsername())).map(x -> {

					List<Task> taskList = taskService.getListOfTask(x);

					int completedCount = (int) taskList.stream().filter(t -> t.getStatus() == Status.COMPLETED).count();

					return new ProjectDTO(x.getCode(), x.getName(),
							userService.getUserDTOByUsername(x.getManager().getUsername()), completedCount,
							(taskList.size() - completedCount), x.getStartDate(), x.getEndDate(), x.getStatus(),
							x.getDetail());

				}).collect(Collectors.toList());

		return list;

	}

}
