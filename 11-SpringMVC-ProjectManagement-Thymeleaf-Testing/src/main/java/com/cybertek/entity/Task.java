package com.cybertek.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.cybertek.util.Status;

@Entity

@NamedQuery(name = "Task.findAllByStatusNQ", query = "Select t from Task as t Where t.status = :status and t.user = :user")

@NamedStoredProcedureQueries({

		@NamedStoredProcedureQuery(name = "totalCompletedTasks", procedureName = "public.totalCompletedTasks", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "projectCode", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "total", type = Integer.class) }),

		@NamedStoredProcedureQuery(name = "totalNonCompletedTasks", procedureName = "public.totalNonCompletedTasks", parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "projectCode", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.OUT, name = "total", type = Integer.class) })

})

public class Task extends BaseEntity {
	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

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

	public Task(long id, LocalDateTime insertDateTime, String insertUserId, LocalDateTime lastUpdateDateTime,
			String lastUpdateUserId, String title, String content, User user, Project project) {
		super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;

		this.startDateTime = LocalDateTime.now();
		this.endDateTime = null;
		this.status = Status.OPEN;
	}

	public Task(String title, String content, User user, Project project, LocalDateTime startDateTime,
			LocalDateTime endDateTime, Status status) {

		this.title = title;
		this.content = content;
		this.user = user;
		this.project = project;

		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
	}

	public Task() {

	}

}
