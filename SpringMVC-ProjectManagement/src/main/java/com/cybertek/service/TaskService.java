package com.cybertek.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cybertek.data.DataGenerator;
import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.implementation.TaskServiceImpl;

@Service
public class TaskService implements TaskServiceImpl {

	@Override
	public List<Task> getListOfTask() {
		return DataGenerator.getTasks();
	}

	@Override
	public List<Task> getListOfTask(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getListOfTask(User manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getListOfTask(User manager, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task insert(Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task update(Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Task> getListOfTaskByUser(User user) {
		return DataGenerator.getTasks().stream().filter(x -> x.getUser().equals(user)).collect(Collectors.toList());

	}

	@Override
	public Task getTaskById(Long id) {
		// TODO Auto-generated method stub
		return DataGenerator.getTasks().stream().filter(x -> x.getId() == id).findFirst().get();
	}

	@Override
	public List<TaskDTO> getListOfTaskDTO() {
		return DataGenerator.converterTaskDTO(getListOfTask());
	}

	@Override
	public List<Task> getListOfTask(Project projectCode) {
		// TODO Auto-generated method stub
		return DataGenerator.getTasks().stream().filter(x -> x.getProject().getCode().equals(projectCode.getCode()))
				.collect(Collectors.toList());
	}

}
