package com.cybertek.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.repository.TaskRepository;

@Component
public class TaskMapper {

	TaskRepository repository;

	UserMapper userMapper;

	ProjectMapper projectMapper;

	@Autowired
	public TaskMapper(TaskRepository repository, UserMapper userMapper, ProjectMapper projectMapper) {
		this.repository = repository;
		this.userMapper = userMapper;
		this.projectMapper = projectMapper;
	}

	public Task convertToEntity(TaskDTO dto) {

		Task task = null;

		if (repository.findById(dto.getId()).isPresent()) {
			task = repository.findById(dto.getId()).get();
		}

		if (task == null) {

			return new Task(dto.getTitle(), dto.getContent(), userMapper.convertToEntity(dto.getUser()),
					projectMapper.convertToEntity(dto.getProject()), dto.getStartDateTime(), dto.getEndDateTime(),
					dto.getStatus());

		}

		return task;
	}

	public TaskDTO convertToDto(Task entity) {

		return new TaskDTO(entity.getId(), entity.getTitle(), entity.getContent(),
				userMapper.convertToDto(entity.getUser()), projectMapper.convertToDto(entity.getProject()),
				userMapper.convertToDto(entity.getProject().getManager()), entity.getStatus());

	}

}
