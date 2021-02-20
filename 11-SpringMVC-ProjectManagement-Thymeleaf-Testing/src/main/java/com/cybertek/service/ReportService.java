package com.cybertek.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.PublicProjectInfoDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;

public interface ReportService {

	
	List<PublicProjectInfoDTO> projectsInfo(); 
	
	List<TaskDTO> listAllTasks();
	
	List<TaskDTO> listAllTasksByUser(UserDTO user);
	
	List<ProjectDTO> listAllProject();
	
	List<ProjectDTO> listAllProjectByManager(UserDTO user);
}
