package com.cybertek.entity;

import java.time.LocalDateTime;

import com.cybertek.util.Status;

public class Task extends BaseEntity {
	private String title;
	private String content;
	private User user;
	private Project project;
	private User manager;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private Status status;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Task(long id, LocalDateTime insertDateTime, long insertUserId, LocalDateTime lastUpdateDateTime,
			long lastUpdateUserId, String title, String content, User user, Project project, User manager) {
		super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;
		this.manager = manager;
		this.startDateTime = LocalDateTime.now();
		this.endDateTime = null;
		this.status = Status.OPEN;
	}

	
	public Task(long id, LocalDateTime insertDateTime, long insertUserId, LocalDateTime lastUpdateDateTime,
			long lastUpdateUserId, String title, String content, User user, Project project, User manager,LocalDateTime startDateTime,LocalDateTime endDateTime,Status status) {
		super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;
		this.manager = manager;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
	}
	
	public Task() {

	}
	
	
}
