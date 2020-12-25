package com.cybertek.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.Project;
import com.cybertek.repository.ProjectRepository;

@Component
public class ProjectMapper {

	ProjectRepository repository;

	UserMapper userMapper;

	@Autowired
	public ProjectMapper(ProjectRepository repository, UserMapper userMapper) {
		this.repository = repository;
		this.userMapper = userMapper;
	}

	public Project convertToEntity(ProjectDTO dto) {

		Project project = repository.getByCode(dto.getCode());

		if (project == null) {

			return new Project(dto.getCode(), dto.getName(), userMapper.convertToEntity(dto.getManager()),
					dto.getStartDate(), dto.getEndDate(), dto.getStatus(), dto.getDetail());
		}

		return project;
	}

	public ProjectDTO convertToDto(Project entity) {

		return new ProjectDTO(entity.getCode(), entity.getName(), userMapper.convertToDto(entity.getManager()),
				entity.getStartDate(), entity.getEndDate(), entity.getStatus(), entity.getDetail());
	}

}
