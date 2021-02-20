package com.cybertek.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cybertek.dto.JwtResponse;
import com.cybertek.dto.LoginRequest;
import com.cybertek.dto.UserDTO;
import com.cybertek.service.TokenService;

import reactor.core.publisher.Mono;

@Service
public class TokenServiceImpl implements TokenService {

	
	private WebClient webClient;
	private static final String SIGNIN_URI="/auth/signin";

	

	public TokenServiceImpl(@Qualifier("restService-WebClient") WebClient webClient) {

		this.webClient = webClient;
	}
	
	
	
	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		return  webClient
				.post()
				.uri(SIGNIN_URI)
				.body(Mono.just(loginRequest), LoginRequest.class)
				.retrieve()
				.bodyToMono(JwtResponse.class)
				.block();
	}

}
