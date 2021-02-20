package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cybertek.annotation.ExecutionTime;
import com.cybertek.configuration.UserPrincipal;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import com.cybertek.util.Status;

@Service
public class ProjectServiceImpl implements ProjectService {

	UserService userService;

	TaskService taskService;

	ProjectRepository projectRepository;

	ProjectMapper projectMapper;

	@Autowired
	public ProjectServiceImpl(UserService userService, TaskService taskService, ProjectRepository projectRepository,
			ProjectMapper projectMapper) {
		this.userService = userService;
		this.taskService = taskService;
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}

	@Override
	@ExecutionTime
	public ProjectDTO getByProjectCode(String code) {

		System.out.println("Testing Debug");

		if (code == null && code.length()<1)
			throw new RuntimeException("Project Not Found");

		Project project = projectRepository.getByCode(code);
		return projectMapper.convertToDto(project);

	}

	@Override
	@ExecutionTime
	public List<ProjectDTO> listAllProjects() {
		List<Project> list = projectRepository.findAll(Sort.by("code"));


		if(list == null)
			return null;

		return list.stream().map(obj -> {
			return projectMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	@ExecutionTime
	public List<ProjectDTO> listAllProjects(UserDTO manager) {
		// TODO Auto-generated method stub

		User user = userService.getByUserName(manager.getUsername());

		List<Project> list = projectRepository.findAllByManager(user);

		return list.stream().map(obj -> {
			return projectMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	@ExecutionTime
	public Project save(ProjectDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		dto.setStatus(Status.OPEN);
	

		Project obj = projectMapper.convertToEntity(dto);
		obj.setInsertUserId(principalUser);
		obj.setInsertDateTime(LocalDateTime.now());
	    
		Project project = projectRepository.saveAndFlush(obj);

		return project;
	}

	@Override
	@ExecutionTime
	public ProjectDTO update(ProjectDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Project project = projectRepository.getByCode(dto.getCode());
		project.setName(dto.getName());
		project.setDetail(dto.getDetail());
		project.setEndDate(dto.getEndDate());
		project.setStartDate(dto.getStartDate());
		project.setLastUpdateUserId(principalUser);
		project.setLastUpdateDateTime(LocalDateTime.now());

		if (dto.getStatus() != null)
			project.setStatus(dto.getStatus());

		User manager = userService.getByUserName(dto.getManager().getUsername());
		project.setManager(manager);

		projectRepository.saveAndFlush(project);

		return getByProjectCode(dto.getCode());

	}

	@Override
	@ExecutionTime
	public boolean delete(String code) {



		Project project = projectRepository.getByCode(code);



		taskService.deleteByProject(project);

		try {
			projectRepository.deleteProject(code);
			return true;
		}catch (Exception exc ){
			return false;
		}


	}

	@Override
	@ExecutionTime
	public List<ProjectDTO> listAllProjectDetails() {
		List<Project> list = projectRepository.findAll(Sort.by("code"));

		List<ProjectDTO> listOfDTO = new ArrayList();

		for (Project project : list) {

			ProjectDTO obj = projectMapper.convertToDto(project);
			obj.setCompletedTasksCount(taskService.totalCompletedTasks(project.getCode()));
			obj.setUnFinishedTasksCount(taskService.totalNonCompletedTasks(project.getCode()));

			listOfDTO.add(obj);

		}

		return listOfDTO;
	}

}
