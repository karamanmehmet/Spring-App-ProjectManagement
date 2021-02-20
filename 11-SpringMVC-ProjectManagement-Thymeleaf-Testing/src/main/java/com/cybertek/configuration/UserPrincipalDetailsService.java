package com.cybertek.configuration;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybertek.dto.JwtResponse;
import com.cybertek.dto.LoginRequest;
import com.cybertek.entity.User;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.TokenService;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	


	
	private UserRepository userRepository;
	

	@Autowired
    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      
    	User user = this.userRepository.findByusername(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);



        
        
        return userPrincipal;
    }
}
