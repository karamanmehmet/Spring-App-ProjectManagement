package com.cybertek.entity;

public class Role {

	private long id;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role(long id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public Role() {
	}
}
