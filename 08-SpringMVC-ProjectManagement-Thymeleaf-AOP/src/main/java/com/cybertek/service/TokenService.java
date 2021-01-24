package com.cybertek.service;

import com.cybertek.dto.JwtResponse;
import com.cybertek.dto.LoginRequest;

public interface TokenService {

	JwtResponse authenticateUser(  LoginRequest loginRequest);
}
