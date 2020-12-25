package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.util.Status;

public interface TaskService {

	
	TaskDTO findById(long	id);
	
	List<TaskDTO> listAllTasks();
	

	
	List<TaskDTO> listAllByProject(ProjectDTO project);
	
	List<TaskDTO> listAllByUser(UserDTO user);
	
	List<TaskDTO> listAllByUserAndProject(UserDTO user,ProjectDTO project);
	
	Task save(TaskDTO dto);
	
	TaskDTO update(TaskDTO dto);
	
	void delete(long id);
	
	void deleteByProject(Project project);

	int totalNonCompletedTasks(String projectCode);
	
	int totalCompletedTasks(String projectCode);
	
	List<TaskDTO> listAllTasksByStatus(Status status);
	
	List<TaskDTO> listAllTasksByStatusIsNot(Status status);
	
}
