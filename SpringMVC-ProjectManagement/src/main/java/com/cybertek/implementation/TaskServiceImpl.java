package com.cybertek.implementation;

import java.util.List;

import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;

public interface TaskServiceImpl {

	List<Task> getListOfTask();
	
	List<TaskDTO> getListOfTaskDTO();
	
	List<Task> getListOfTask(Project projectCode);

	List<Task> getListOfTask(String status);

	List<Task> getListOfTask(User manager);
	
	List<Task> getListOfTaskByUser(User user);

	List<Task> getListOfTask(User manager, String status);

	Task insert(Task task);

	Task update(Task task);

	boolean delete(Task task);

	Task getTaskById(Long id);

	TaskDTO getTaskDTOById(Long id);

	List<TaskDTO> updateTaskDTO(TaskDTO taskDTO);

	List<TaskDTO> deleteTask(Long id);

	List<TaskDTO> getEmployeeTasks(UserDTO user);

	List<TaskDTO> updateTaskDTOForEmployee(UserDTO user,TaskDTO taskDTO);

	List<TaskDTO> archiveList(UserDTO user);
	
}
