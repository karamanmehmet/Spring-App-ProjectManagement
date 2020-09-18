package com.cybertek.implementation;

import java.util.List;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;

public interface ProjectServiceImpl {

	 ProjectDTO update(ProjectDTO project); 
	
	List<Project> getListOfProject();
	
	List<ProjectDTO> getListOfProjectDTO();
	
	List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);

	List<Project> getListOfProject(String status);

	List<Project> getListOfProject(User manager);

	List<Project> getListOfProject(User manager, String status);

	Project insert(Project project);

	Project update(Project project);

	boolean delete(Project project);

	Project getProjectByProjectCode(String projectcode);
	
	ProjectDTO getProjectDTOByProjectCode(String projectcode);
}
