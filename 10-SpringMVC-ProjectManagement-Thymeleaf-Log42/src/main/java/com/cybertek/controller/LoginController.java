package com.cybertek.controller;

import javax.servlet.http.HttpSession;

import com.cybertek.implementation.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cybertek.dto.WeatherDTO;
import com.cybertek.service.WeatherService;

@Controller
public class LoginController {




	private WeatherService weatherService;
	private HttpSession httpSession;
	 
	@Autowired 
	public LoginController(WeatherService weatherService, HttpSession httpSession) {
		super();
		this.weatherService = weatherService;
		this.httpSession = httpSession;
	}

	@GetMapping(value = { "", "/login" })
	public String login(Model model) {

		WeatherDTO weather = weatherService.weatherInformation();

		
		httpSession.setAttribute("temperature", weather.getTemperature().getTemp());
		return "login";
	}
	
	@GetMapping(value = { "access-denied" })
	public String addedDenied(Model model) {

		return "base/access_denied";
	}

}
