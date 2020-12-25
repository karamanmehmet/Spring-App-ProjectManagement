package com.cybertek.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "insertDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime insertDateTime;
	
	
	private String insertUserId;
	
	@Column(name = "lastUpdateDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdateDateTime;
	
	private String lastUpdateUserId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(LocalDateTime insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

	public String getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}

	public LocalDateTime getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public BaseEntity(long id, LocalDateTime insertDateTime, String insertUserId, LocalDateTime lastUpdateDateTime,
			String lastUpdateUserId) {
		super();
		this.id = id;
		this.insertDateTime = insertDateTime;
		this.insertUserId = insertUserId;
		this.lastUpdateDateTime = lastUpdateDateTime;
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public BaseEntity() {
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
		BaseEntity other = (BaseEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
