package com.cybertek.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.cybertek.util.Status;

public class Project extends BaseEntity {

	private String code;
	private String name;
	private User manager;
	private LocalDate startDate;
	private LocalDate endDate;
	private Status status;
	private String detail;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Project(long id, LocalDateTime insertDateTime, long insertUserId, LocalDateTime lastUpdateDateTime,
			long lastUpdateUserId, String code, String name, User manager, LocalDate startDate, LocalDate endDate, Status status,
			String detail) {
		super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
		this.code = code;
		this.name = name;
		this.manager = manager;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.detail = detail;
	}

	public Project() {
	}

	
	
	
}
