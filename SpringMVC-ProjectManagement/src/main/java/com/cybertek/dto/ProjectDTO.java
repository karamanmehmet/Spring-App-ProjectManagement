package com.cybertek.dto;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cybertek.util.Status;

public class ProjectDTO {

	
	private String code;

	private String name;
	private UserDTO manager;
	
	private int completedTasksCount;
	
	private int unFinishedTasksCount;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern="yyyy-MM-dd")
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

	public UserDTO getManager() {
		return manager;
	}

	public void setManager(UserDTO manager) {
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
	
	

	public int getCompletedTasksCount() {
		return completedTasksCount;
	}

	public void setCompletedTasksCount(int completedTasksCount) {
		this.completedTasksCount = completedTasksCount;
	}

	public int getUnFinishedTasksCount() {
		return unFinishedTasksCount;
	}

	public void setUnFinishedTasksCount(int unFinishedTasksCount) {
		this.unFinishedTasksCount = unFinishedTasksCount;
	}

	public ProjectDTO(String code, String name, UserDTO manager, LocalDate startDate, LocalDate endDate, Status status,
			String detail) {

		this.code = code;
		this.name = name;
		this.manager = manager;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.detail = detail;
	}
	

	
	
	
	public ProjectDTO(String code, String name, UserDTO manager, int completedTasksCount, int unFinishedTasksCount,
			LocalDate startDate, LocalDate endDate, Status status, String detail) {
		super();
		this.code = code;
		this.name = name;
		this.manager = manager;
		this.completedTasksCount = completedTasksCount;
		this.unFinishedTasksCount = unFinishedTasksCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.detail = detail;
	}

	public ProjectDTO() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ProjectDTO other = (ProjectDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	
	

}
