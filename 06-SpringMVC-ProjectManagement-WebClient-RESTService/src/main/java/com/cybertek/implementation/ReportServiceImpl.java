package com.cybertek.implementation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.PublicProjectInfoDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.ReportService;

import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {

	private WebClient webClient;
	private HttpSession  httpSession;
	
	
	private static final String PROJECT_GENERAL_URI="/report/projectsInfo";
	private static final String PROJECT_ALL_URI="/report/projects";
	private static final String PROJECT_BYMANAGER_URI="/report/managerProjects";
	private static final String TASK_ALL_URI="/report/tasks";
	private static final String TASK_BYUSER_URI="/report/tasksByUser";	
	
	private static String token ="";	

	
	

	public ReportServiceImpl(@Qualifier("restService-WebClient") WebClient webClient,HttpSession  httpSession) {

		this.webClient = webClient;
		this.httpSession = httpSession;
		
		
	}

	
	
	
	@Override
	public List<PublicProjectInfoDTO> projectsInfo() {

		token=this.httpSession.getAttribute("Authorization").toString();
		
		return  webClient
				.get()
				.uri(PROJECT_GENERAL_URI)
				.header("Authorization", token)
				.retrieve()
				.bodyToFlux(PublicProjectInfoDTO.class)
				.collectList()
				.block();
		

	}

	@Override
	public List<TaskDTO> listAllTasks() {
		
		token=this.httpSession.getAttribute("Authorization").toString();
		
		return  webClient
				.get()
				.uri(TASK_ALL_URI)
				.header("Authorization", token)
				.retrieve()
				.bodyToFlux(TaskDTO.class)
				.collectList()
				.block();
	}

	@Override
	public List<TaskDTO> listAllTasksByUser(UserDTO user) {
		
		token=this.httpSession.getAttribute("Authorization").toString();
		
		return  webClient
				.post()
				.uri(TASK_BYUSER_URI)
				.header("Authorization", token)
				.body(Mono.just(user), UserDTO.class)
				.retrieve()
				.bodyToFlux(TaskDTO.class)
				.collectList()
				.block();
	}

	@Override
	public List<ProjectDTO> listAllProject() {
		
		token=this.httpSession.getAttribute("Authorization").toString();
	
		return  webClient
				.get()
				.uri(PROJECT_ALL_URI)
				.header("Authorization", token)
				.retrieve()
				.bodyToFlux(ProjectDTO.class)
				.collectList()
				.block();

	}

	@Override
	public List<ProjectDTO> listAllProjectByManager(UserDTO user) {
		
		token=this.httpSession.getAttribute("Authorization").toString();
		
		return  webClient
				.post()
				.uri(PROJECT_BYMANAGER_URI)
				.header("Authorization", token)
				.body(Mono.just(user), UserDTO.class)
				.retrieve()
				.bodyToFlux(ProjectDTO.class)
				.collectList()
				.block();
	}

}
