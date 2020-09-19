package com.cybertek.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.RoleDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.Role;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.implementation.RoleServiceImpl;
import com.cybertek.service.RoleService;
import com.cybertek.util.Gender;
import com.cybertek.util.Status;

public class DataGenerator {

	// Role - RoleDTO Related Datas Related

	public static Role adminRole = new Role(1, "Admin");
	public static Role managerRole = new Role(2, "Manager");
	public static Role employeeRole = new Role(3, "Employee");

	public static List<Role> getRoles() {

		List<Role> roles = new ArrayList<>();

		roles.add(employeeRole);
		roles.add(managerRole);
		roles.add(adminRole);

		return roles;
	}

	
		
	// user - userdto

	public static UserDTO getUserDTOByUser(User user) {

		RoleServiceImpl roleService = new RoleService();

		RoleDTO role = roleService.getRoleDTOById(user.getRole().getId());

		return new UserDTO(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(),
				user.isEnabled(), user.getPhone(), role, user.getGender());

	}

	
	
	public static User manager1 = new User(2, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "Delisa",
			"Norre", "T001@cybertek.com", "123", true, "8567412358", managerRole, Gender.Female);

	public static User manager2 = new User(3, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "Craig",
			"Jark", "P001@cybertek.com", "123", true, "7777775566", managerRole, Gender.Male);
	
	

	public static User user1 = new User(1, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "John", "Kesy",
			"admin@cybertek.com", "abc", true, "7459684532", adminRole, Gender.Male);

	public static User user2 = new User(2, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "Delisa",
			"Norre", "T001@cybertek.com", "123", true, "8567412358", managerRole, Gender.Female);

	public static User user3 = new User(3, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "Craig", "Jark",
			"P001@cybertek.com", "123", true, "7777775566", employeeRole, Gender.Male);

	public static User user4 = new User(4, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1, "Shaun",
			"Hayns", "S001@cybertek.com", "123", true, "3256987412", employeeRole, Gender.Male);
	
	
	//setting up active user and managers
	
	public static UserDTO activeUser = getUserDTOByUser(user1);
	
	public static UserDTO activeManager =getUserDTOByUser(manager1);
	
	
	
	
	public static List<User> getUsers() {

		List<User> users = new ArrayList<>();

		users.add(user1);

		users.add(user2);

		users.add(user3);

		users.add(user4);

		return users;
	}
	
	public static List<User> getManagers() {

		List<User> managers = new ArrayList<>();

		managers.add(manager1);
		managers.add(manager2);

		return managers;
	}

	
	
	
	
	public static User getUserByUserId(long userId) {

		User user = DataGenerator.getUsers().stream().filter(x -> x.getId() == userId).findFirst().get();

		return user;
	}

	
	
	
	
	//Project Data

	public static Project project1 = new Project(1, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1,
			"PRJ001", "Human Resource CRM", manager1, LocalDate.now(), LocalDate.now().plusDays(6), Status.IN_PROGRESS,
			"HCRM Detail Information");

	public static Project project2 = new Project(2, LocalDateTime.now(), 1, LocalDateTime.now().plusHours(1), 1,
			"PRJ002", "Infra Upgrade", manager2, LocalDate.now(), LocalDate.now().plusDays(3), Status.UAT_TEST,
			"Server Details");


	public static List<Project> getProjects() {

		List<Project> projects = new ArrayList<>();

		projects.add(project1);
		projects.add(project2);

		return projects;

	}

	public static List<ProjectDTO> converterProjectDTO(List<Project> projects) {

		List<ProjectDTO> list = projects.stream().map(x -> {

			return new ProjectDTO(x.getCode(), x.getName(), getUserDTOByUser(x.getManager()), x.getStartDate(),
					x.getEndDate(), x.getStatus(), x.getDetail());

		}).collect(Collectors.toList());

		return list;
	}

	public static ProjectDTO getProjectDTOByProject(Project project) {

		return new ProjectDTO(project.getCode(), project.getName(), getUserDTOByUser(project.getManager()),
				project.getStartDate(), project.getEndDate(), project.getStatus(), project.getDetail());
	}


	
	
	
	//Task Data
	public static List<Task> getTasks() {

		List<Task> tasks = new ArrayList<>();

		tasks.add(new Task(1, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(2), 1, "Database Connection",
				"DB Oracle Connection Should be comleted", user1, project1, manager1, LocalDateTime.now().minusDays(2),
				LocalDateTime.now().minusDays(5), Status.IN_PROGRESS));
		tasks.add(new Task(2, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(3), 1, "Server LAN Settings",
				"Servers should be communicate each others", user2, project2, manager1,
				LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(3), Status.UAT_TEST));
		tasks.add(new Task(3, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(6), 1, "REST Codes Development",
				"Generating GET/POST/PUT/DELETE Rest EndPoints", user1, project1, manager1,
				LocalDateTime.now().minusDays(16), LocalDateTime.now().minusDays(8), Status.COMPLETED));
		
		tasks.add(new Task(4, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(8), 1, "General Analysis",
				"Integration Analysis between APIs", user1, project1, manager1,
				LocalDateTime.now().minusDays(6), LocalDateTime.now().plusDays(22), Status.OPEN));
		
		tasks.add(new Task(5, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(19), 1, "Environment Setup",
				"Configure TestNG & REST Assured", user1, project1, manager1,
				LocalDateTime.now().minusDays(23), LocalDateTime.now().minusDays(1), Status.COMPLETED));
		
		tasks.add(new Task(6, LocalDateTime.now(), 1, LocalDateTime.now().plusDays(23), 1, "Deployment Activities - Docker",
				"Dockerize Java REST API", user1, project1, manager1,
				LocalDateTime.now().minusDays(11), LocalDateTime.now().plusDays(5), Status.IN_PROGRESS));
		return tasks;
	}

	public static List<TaskDTO> converterTaskDTO(List<Task> tasks) {

		// return List

		List<TaskDTO> list = tasks.stream().map(x -> {

			return new TaskDTO(x.getId(), x.getTitle(), x.getContent(), getUserDTOByUser(x.getUser()),
					getProjectDTOByProject(x.getProject()), getUserDTOByUser(x.getManager()), x.getStartDateTime(),
					x.getEndDateTime(), x.getStatus());

		}).collect(Collectors.toList());

		return list;
	}

	public static TaskDTO getTaskDTOByTask(Task x) {

		return new TaskDTO(x.getId(), x.getTitle(), x.getContent(), getUserDTOByUser(x.getUser()),
				getProjectDTOByProject(x.getProject()), getUserDTOByUser(x.getManager()), x.getStartDateTime(),
				x.getEndDateTime(), x.getStatus());
	}

	
	
	//Status Enum
	public static List<Status> getStatusList() {

		return Arrays.asList(Status.values());
	}

}
