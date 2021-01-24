package com.cybertek.configuration;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cybertek.dto.JwtResponse;
import com.cybertek.dto.LoginRequest;
import com.cybertek.service.TokenService;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


	@Autowired
	private TokenService tokenService;
	
	
	@Autowired
	HttpSession  httpSession;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {

		
		String username= httpServletRequest.getParameter("txtUsername");
	    String password= httpServletRequest.getParameter("txtPassword");
	    
        
    	//after success login JWT token is creating and adding it into cookie
		LoginRequest loginRequest = new LoginRequest(username,password);
		
		JwtResponse tokenResponse = tokenService.authenticateUser(loginRequest);
		
		httpSession.setAttribute("Authorization", tokenResponse.getTokenType()+" "+tokenResponse.getAccessToken());
		
		
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

		if (roles.contains("ROLE_ADMIN")) {
			httpServletResponse.sendRedirect("/admin");
		}
		
		if (roles.contains("ROLE_MANAGER")) {
			httpServletResponse.sendRedirect("/project");
		} 
		
		if (roles.contains("ROLE_USER")) { 
			httpServletResponse.sendRedirect("/task/employee");
		}
		
		
		
		
		   

	}
}
