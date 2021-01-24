package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.ProjectRepository;
import com.cybertek.repository.TaskRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.TaskService;
import com.cybertek.util.Status;

@Service
public class TaskServiceImpl implements TaskService {

	TaskRepository taskRepository;

	UserRepository userRepository;

	ProjectRepository projectRepository;

	UserMapper userMapper;

	ProjectMapper projectMapper;

	TaskMapper taskMapper;

	@Autowired
	public TaskServiceImpl(com.cybertek.repository.TaskRepository taskRepository, TaskMapper taskMapper,
			UserMapper userMapper, ProjectMapper projectMapper, UserRepository userRepository,
			ProjectRepository projectRepository) {
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
		this.userMapper = userMapper;
		this.projectMapper = projectMapper;
		this.userRepository = userRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	public TaskDTO findById(long id) {

		Task task = taskRepository.findById(id).get();
		return taskMapper.convertToDto(task);
	}

	@Override
	public List<TaskDTO> listAllTasks() {

		List<Task> list = taskRepository.findAll();

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	public List<TaskDTO> listAllByProject(ProjectDTO project) {

		List<Task> list = taskRepository.findAllByProject(projectMapper.convertToEntity(project));

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	public List<TaskDTO> listAllByUser(UserDTO user) {

		List<Task> list = taskRepository.findAllByUser(userMapper.convertToEntity(user));

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public TaskDTO save(TaskDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Task task = new Task();
		task.setContent(dto.getContent());
		task.setEndDateTime(dto.getEndDateTime());
		task.setProject(projectRepository.getByCode(dto.getProject().getCode()));
		task.setUser(userRepository.findByusername(dto.getUser().getUsername()));
		task.setTitle(dto.getTitle());
		task.setStartDateTime(dto.getStartDateTime());
		task.setStatus(Status.OPEN);
		task.setInsertUserId(principalUser);
		task.setInsertDateTime(LocalDateTime.now());

		return taskMapper.convertToDto(taskRepository.save(task));

	}

	@Override
	public TaskDTO update(TaskDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Task task = taskRepository.findById(dto.getId()).get();

		if (dto.getProject() != null && dto.getProject().getCode() != null) {
			Project project = projectMapper.convertToEntity(dto.getProject());
			task.setProject(project);
		}
		if (dto.getUser() != null && dto.getUser().getUsername() != null) {
			User user = userMapper.convertToEntity(dto.getUser());
			task.setUser(user);
		}
		task.setContent(dto.getContent());

		task.setTitle(dto.getTitle());

		if (dto.getStatus() != null) {
			task.setStatus(dto.getStatus());
		}

		task.setLastUpdateUserId(principalUser);
		task.setLastUpdateDateTime(LocalDateTime.now());

		if (dto.getStatus().equals(Status.COMPLETED)) {
			task.setEndDateTime(LocalDateTime.now());
		}

		taskRepository.saveAndFlush(task);

		return findById(dto.getId());
	}

	@Override
	public boolean delete(long id) {
		try {
			taskRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteByProject(Project project) {

		try {
			taskRepository.deleteByProject(project);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int totalNonCompletedTasks(String projectCode) {
		return taskRepository.totalNonCompletedTasks(projectCode);
	}

	@Override
	public int totalCompletedTasks(String projectCode) {
		return taskRepository.totalCompletedTasks(projectCode);
	}

	@Override
	public List<TaskDTO> listAllTasksByStatus(Status status) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByusername(principalUser);

		List<Task> list = taskRepository.findAllByStatusNQ(status, user);

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByusername(principalUser);

		List<Task> list = taskRepository.findAllByStatusIsNotAndUser(status, user);

		return list.stream().map(obj -> {
			return taskMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

}
