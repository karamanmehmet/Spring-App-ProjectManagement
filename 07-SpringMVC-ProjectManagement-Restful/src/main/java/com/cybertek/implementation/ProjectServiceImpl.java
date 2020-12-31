package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;
import com.cybertek.exception.ProjectCodeAlreadyExistException;
import com.cybertek.exception.UserAlreadyExistAuthenticationException;
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
	public ProjectDTO getByProjectCode(String code) {
		Project project = projectRepository.getByCode(code);
		return projectMapper.convertToDto(project);

	}

	@Override
	public List<ProjectDTO> listAllProjects() {
		List<Project> list = projectRepository.findAll(Sort.by("code"));

		return list.stream().map(obj -> {
			return projectMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	public List<ProjectDTO> listAllProjects(UserDTO manager) {
		// TODO Auto-generated method stub

		User user = userService.getByUserName(manager.getUsername());

		List<Project> list = projectRepository.findAllByManager(user);

		return list.stream().map(obj -> {
			return projectMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	public ProjectDTO save(ProjectDTO dto) {
		
		
		
		//check if project code is exist
		
		Project p= projectRepository.getByCode(dto.getCode());
		
		if(p != null) {
			throw new ProjectCodeAlreadyExistException("Project Code "+dto.getCode()+" Already Exist Exception");
		}
		
		


		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		dto.setStatus(Status.OPEN);
		
		
		
		

		Project obj = projectMapper.convertToEntity(dto);
		obj.setInsertUserId(principalUser);
		obj.setInsertDateTime(LocalDateTime.now());

		Project project = projectRepository.saveAndFlush(obj);

		return projectMapper.convertToDto(project);
	}

	@Override
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
	public boolean delete(String code) {

		Project project = projectRepository.getByCode(code);

		try {
			taskService.deleteByProject(project);

			projectRepository.deleteProject(code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ProjectDTO> listAllProjectDetails() {
		List<Project> list = projectRepository.findAll(Sort.by("code"));

		List<ProjectDTO> listOfDTO = new ArrayList<ProjectDTO>();

		for (Project project : list) {

			ProjectDTO obj = projectMapper.convertToDto(project);
			obj.setCompletedTasksCount(taskService.totalCompletedTasks(project.getCode()));
			obj.setUnFinishedTasksCount(taskService.totalNonCompletedTasks(project.getCode()));

			listOfDTO.add(obj);

		}

		return listOfDTO;
	}

}
