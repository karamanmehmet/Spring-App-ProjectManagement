package com.cybertek.util;

public enum Gender {

	Male("Male"), Female("Female");

	private final String value;

	private Gender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
