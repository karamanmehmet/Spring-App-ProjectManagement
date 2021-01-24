package com.cybertek.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cybertek.dto.WeatherDTO;
import com.cybertek.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WebClient webClient;

	@Autowired
	public WeatherServiceImpl(@Qualifier("weather-WebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public WeatherDTO weatherInformation() {

		return webClient.get().retrieve().bodyToMono(WeatherDTO.class).block();

	}

}
