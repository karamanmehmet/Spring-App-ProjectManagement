package com.cybertek.dto;

import java.time.LocalDateTime;

import com.cybertek.util.Status;

public class TaskDTO {

	private long id;
	private String title;
	private String content;
	private UserDTO user;
	private ProjectDTO project;
	private UserDTO manager;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private Status status=Status.OPEN;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

	public UserDTO getManager() {
		return manager;
	}

	public void setManager(UserDTO manager) {
		this.manager = manager;
	}

	public TaskDTO(long id, String title, String content, UserDTO user, ProjectDTO project, UserDTO manager,Status status) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;
		this.manager = manager;
		this.startDateTime = LocalDateTime.now();
		this.endDateTime = null;
		this.status = status;
	}
	
	public TaskDTO(long id, String title, String content, UserDTO user, ProjectDTO project, UserDTO manager,LocalDateTime startDateTime,LocalDateTime endDateTime,Status status) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;
		this.manager = manager;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
	}

	public TaskDTO() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskDTO other = (TaskDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
