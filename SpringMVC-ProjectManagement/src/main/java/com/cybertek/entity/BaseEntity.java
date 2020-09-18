package com.cybertek.entity;

import java.time.LocalDateTime;

public class BaseEntity {

	private long id;
	private LocalDateTime insertDateTime;
	private long insertUserId;
	private LocalDateTime lastUpdateDateTime;
	private long lastUpdateUserId;

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

	public long getInsertUserId() {
		return insertUserId;
	}

	public void setInsertUserId(long insertUserId) {
		this.insertUserId = insertUserId;
	}

	public LocalDateTime getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public long getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(long lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public BaseEntity(long id, LocalDateTime insertDateTime, long insertUserId, LocalDateTime lastUpdateDateTime,
			long lastUpdateUserId) {
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
