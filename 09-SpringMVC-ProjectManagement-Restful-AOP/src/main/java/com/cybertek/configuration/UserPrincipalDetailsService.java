package com.cybertek.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cybertek.entity.User;
import com.cybertek.repository.UserRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public UserPrincipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByusername(username);
		
		if (user.equals(null))
			throw new UsernameNotFoundException("User Not Found with username: " + username);

		return UserPrincipal.build(user);

	}
}
