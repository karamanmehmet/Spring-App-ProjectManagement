package com.cybertek.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.repository.UserRepository;

@Component
public class UserMapper {

	UserRepository repository;

	RoleMapper roleMapper;

	@Autowired
	public UserMapper(UserRepository repository, RoleMapper roleMapper) {
		this.repository = repository;
		this.roleMapper = roleMapper;
	}

	public User convertToEntity(UserDTO dto) {

		User user = repository.findByusername(dto.getUsername());
		
		//first User insertion
		if (user == null) {

			return new User(dto.getFirstname(), dto.getLastname(), dto.getUsername(), dto.getPassword(),
					dto.isEnabled(), dto.getPhone(), roleMapper.convertToEntity(dto.getRole()), dto.getGender());
		}
		
		return user;

	}

	public UserDTO convertToDto(User entity) {

		return new UserDTO(entity.getFirstname(), entity.getLastname(), entity.getUsername(), entity.getPassword(),
				entity.isEnabled(), entity.getPhone(), roleMapper.convertToDto(entity.getRole()), entity.getGender());
	}

}
