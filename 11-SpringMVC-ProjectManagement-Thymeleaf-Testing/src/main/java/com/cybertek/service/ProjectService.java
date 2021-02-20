package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;

public interface ProjectService {

	ProjectDTO getByProjectCode(String code);
	
	List<ProjectDTO> listAllProjects();
	
	List<ProjectDTO> listAllProjects(UserDTO manager);
	
	Project save(ProjectDTO dto);
	
	ProjectDTO update(ProjectDTO dto);
	
	List<ProjectDTO> listAllProjectDetails();
	
	boolean delete(String code);

}
