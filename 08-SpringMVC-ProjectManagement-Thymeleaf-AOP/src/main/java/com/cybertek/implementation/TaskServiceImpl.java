package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cybertek.annotation.ExecutionTime;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.TaskRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.TaskService;
import com.cybertek.util.Status;

@Service
public class TaskServiceImpl implements TaskService {

	TaskRepository taskRepository;
	
	UserRepository userRepository;

	UserMapper userMapper;

	ProjectMapper projectMapper;

	TaskMapper taskMapper;

	@Autowired
	public TaskServiceImpl(com.cybertek.repository.TaskRepository taskRepository, TaskMapper taskMapper,
			UserMapper userMapper, ProjectMapper projectMapper,UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
		this.userMapper = userMapper;
		this.projectMapper = projectMapper;
		this.userRepository = userRepository;
	}

	@Override
	@ExecutionTime
	public TaskDTO findById(long id) {

		Task task = taskRepository.findById(id).get();
		return taskMapper.convertToDto(task);
	}

	@Override
	@ExecutionTime
	public List<TaskDTO> listAllTasks() {

		List<Task> list = taskRepository.findAll();

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	@ExecutionTime
	public List<TaskDTO> listAllByProject(ProjectDTO project) {

		List<Task> list = taskRepository.findAllByProject(projectMapper.convertToEntity(project));

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	@ExecutionTime
	public List<TaskDTO> listAllByUser(UserDTO user) {

		List<Task> list = taskRepository.findAllByUser(userMapper.convertToEntity(user));

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}


	@Override
	@ExecutionTime
	public Task save(TaskDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		dto.setStatus(Status.OPEN);

		Task task = taskMapper.convertToEntity(dto);
		task.setInsertUserId(principalUser);
		task.setInsertDateTime(LocalDateTime.now());

		return taskRepository.save(task);

	}

	@Override
	@ExecutionTime
	public TaskDTO update(TaskDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Task task = taskRepository.findById(dto.getId()).get();

		Project project = projectMapper.convertToEntity(dto.getProject());
		User user = userMapper.convertToEntity(dto.getUser());

		task.setContent(dto.getContent());
		task.setEndDateTime(dto.getEndDateTime());
		task.setProject(project);
		task.setUser(user);
		task.setTitle(dto.getTitle());
		task.setStartDateTime(dto.getStartDateTime());
		task.setStatus(dto.getStatus());
		task.setLastUpdateUserId(principalUser);
		task.setLastUpdateDateTime(LocalDateTime.now());
		
		if(dto.getStatus().equals(Status.COMPLETED)) {
			task.setEndDateTime(LocalDateTime.now());
		}

		taskRepository.saveAndFlush(task);

		return findById(dto.getId());
	}

	@Override
	@ExecutionTime
	public void delete(long id) {
		taskRepository.deleteById(id);

	}

	@Override
	@ExecutionTime
	public void deleteByProject(Project project) {
		taskRepository.deleteByProject(project);

	}

	@Override
	@ExecutionTime
	public int totalNonCompletedTasks(String projectCode) {
		return taskRepository.totalNonCompletedTasks(projectCode);
	}

	@Override
	@ExecutionTime
	public int totalCompletedTasks(String projectCode) {
		return taskRepository.totalCompletedTasks(projectCode);
	}

	@Override
	@ExecutionTime
	public List<TaskDTO> listAllTasksByStatus(Status status) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByusername(principalUser);
		
		List<Task> list = taskRepository.findAllByStatusNQ(status,user);

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	@ExecutionTime
	public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
		
		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByusername(principalUser);
		
		
		List<Task> list = taskRepository.findAllByStatusIsNotAndUser(status,user);

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public List<TaskDTO> listAllByUserAndProject(UserDTO user, ProjectDTO project) {
		// TODO Auto-generated method stub
		return null;
	}

}
