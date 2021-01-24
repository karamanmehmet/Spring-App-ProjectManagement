package com.cybertek.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class WeatherDTO {
	
	private TemperatureDTO temperature;
	
	private String name;



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TemperatureDTO getTemperature() {
		return temperature;
	}

	@JsonSetter("main")
	public void setTemperature(TemperatureDTO temperature) {
		this.temperature = temperature;
	}
	


}
