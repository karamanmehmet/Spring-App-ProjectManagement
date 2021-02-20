
package com.cybertek.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cybertek.aspect.LoggingAspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cybertek.configuration.UserPrincipal;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Role;
import com.cybertek.entity.User;
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
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleMapper roleMapper	,RoleRepository roleRepository		) {

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
	public User save(UserDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		User obj = userMapper.convertToEntity(dto);

		obj.setInsertUserId(principalUser);
		obj.setInsertDateTime(LocalDateTime.now());

		User user = userRepository.save(obj);
		return user;

	}

	@Override
	public UserDTO findByUserName(String username) {
		User user = userRepository.findByusername(username);
		return userMapper.convertToDto(user);
	}

	@Override
	public UserDTO update(UserDTO dto) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();



		User user = userRepository.findByusername(dto.getUsername());

		user.setFirstname(dto.getFirstname());
		user.setLastname(dto.getLastname());
		user.setUsername(dto.getUsername());
		user.setRole(roleMapper.convertToEntity(dto.getRole()));
		user.setGender(dto.getGender());
		user.setPhone(dto.getPhone());
		user.setPassword(dto.getPassword());
		user.setLastUpdateUserId(principalUser);
		user.setLastUpdateDateTime(LocalDateTime.now());

		userRepository.saveAndFlush(user);

		return findByUserName(dto.getUsername());

	}

	@Override
	public UserDTO deActivate(String username, boolean status) {

		String principalUser = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByusername(username);
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

}
