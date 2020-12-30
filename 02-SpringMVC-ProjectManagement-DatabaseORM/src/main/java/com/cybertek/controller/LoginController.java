package com.cybertek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping(value = { "", "/login" })
	public String login(Model model) {

		return "login";
	}
	
	@GetMapping(value = { "access-denied" })
	public String addedDenied(Model model) {

		return "base/access_denied";
	}

}
