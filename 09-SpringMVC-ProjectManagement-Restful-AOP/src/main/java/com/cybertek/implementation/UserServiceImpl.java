
package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Role;
import com.cybertek.entity.User;
import com.cybertek.exception.RoleNotFoundException;
import com.cybertek.exception.UserAlreadyExistAuthenticationException;
import com.cybertek.exception.UserNotFoundException;
import com.cybertek.mapper.RoleMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repository.RoleRepository;
import com.cybertek.repository.UserRepository;
import com.cybertek.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private UserMapper userMapper;

	private RoleMapper roleMapper;

	private RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper,
			RoleRepository roleRepository) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleMapper = roleMapper;
		this.roleRepository = roleRepository;

	}

	@Override
	public List<UserDTO> listAllUsers() {

		List<User> list = userRepository.findAll(Sort.by("firstname"));

		return list.stream().map(obj -> {
			return userMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> listAllUsersByRole(String description) {

		Role role = roleRepository.findByDescription(description);

		List<User> list = userRepository.findAllByRole(role);

		return list.stream().map(obj -> {
			return userMapper.convertToDto(obj);
		}).collect(Collectors.toList());
	}

	@Override
	public UserDTO save(UserDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		Role role = roleRepository.findByDescription(dto.getRole().getDescription());
		
		if(role == null) {
			throw new RoleNotFoundException("Role '"+dto.getRole().getDescription()+"' Not Found Exception");
		}

		
		User newUser= userRepository.findByusername(dto.getUsername());
		
		if(newUser != null) {
			throw new UserAlreadyExistAuthenticationException("User '"+dto.getUsername()+"' Already Exist Exception");
		}
		
		User obj = new User();

		obj.setRole(role);
		obj.setFirstname(dto.getFirstname());
		obj.setLastname(dto.getLastname());
		obj.setUsername(dto.getUsername());
		obj.setEnabled(dto.isEnabled());
		obj.setGender(dto.getGender());
		obj.setPhone(dto.getPhone());
		obj.setPassword(dto.getPassword());
		obj.setInsertUserId(principalUser);
		obj.setInsertDateTime(LocalDateTime.now());

		User user = userRepository.save(obj);

		return userMapper.convertToDto(user);

	}

	@Override
	public UserDTO findByUserName(String username) {
		User user = userRepository.findByusername(username);
		
		
		if(user == null) {
			throw new UserNotFoundException("User '"+username+"' Not Found Exception");
		}
		return userMapper.convertToDto(user);
	}

	@Override
	public UserDTO update(UserDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByusername(dto.getUsername());


		
		if(user == null) {
			throw new UserNotFoundException("User '"+dto.getUsername()+"' Not Found Exception");
		}

		
		
		// if role is going to update
		if (!dto.getRole().getDescription().equals("")) {
			
			Role role = roleRepository.findByDescription(dto.getRole().getDescription());
			if(role == null) {
				throw new RoleNotFoundException("Role  '"+dto.getRole().getDescription()+"' Not Found Exception");
			}
			
			
			user.setRole(role);
		}

		// if password update needed
		if (dto.getPassword() != null) {
			user.setPassword(dto.getPassword());

		}

		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setGender(dto.getGender());
		user.setPhone(dto.getPhone());

		user.setLastUpdateUserId(principalUser);
		user.setLastUpdateDateTime(LocalDateTime.now());

		userRepository.saveAndFlush(user);

		return findByUserName(dto.getUsername());

	}

	@Override
	public UserDTO deActivate(String username, boolean status) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByusername(username);
		
		
		if(user == null) {
			throw new UserNotFoundException("User  '"+username+"'  Not Found Exception");
		}
		
		
		
		user.setEnabled(status);

		user.setLastUpdateUserId(principalUser);
		user.setLastUpdateDateTime(LocalDateTime.now());

		userRepository.saveAndFlush(user);
		return findByUserName(username);
	}

	@Override
	public List<UserDTO> listManagers() {

		List<User> list = userRepository.listManagers();

		return list.stream().map(obj -> {
			return userMapper.convertToDto(obj);
		}).collect(Collectors.toList());

	}

	@Override
	public User getByUserName(String username) {

		return userRepository.findByusername(username);
	}

	@Override
	public boolean delete(String username) {
		User user = userRepository.findByusername(username);

		if(user == null) {
			throw new UserNotFoundException("User '"+username+"'  Not Found Exception");
		}
		
		try {
			userRepository.delete(user);

		} catch (Exception e) {
			return false;
		}

		return true;

	}

}
