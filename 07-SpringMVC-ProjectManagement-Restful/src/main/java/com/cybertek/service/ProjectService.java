package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;

public interface ProjectService {

	ProjectDTO getByProjectCode(String code);
	
	List<ProjectDTO> listAllProjects();
	
	List<ProjectDTO> listAllProjects(UserDTO manager);
	
	ProjectDTO save(ProjectDTO dto);
	
	ProjectDTO update(ProjectDTO dto);
	
	List<ProjectDTO> listAllProjectDetails();
	
	boolean delete(String code);

}
