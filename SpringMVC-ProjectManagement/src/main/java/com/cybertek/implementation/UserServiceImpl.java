package com.cybertek.implementation;

import java.util.List;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;

public interface UserServiceImpl {

	User userByUserName(String userName);
	
	String userByUserId(Long id);
	
	List<User> listOfUsers();
	
	List<UserDTO> listOfUserDTO();
	
	List<User> listOfUsersByRole(String Role);
	
	User updateUser(User user);
	
	UserDTO getUserDTOByUsername(String username);
	
	boolean deleteUser(String userName);
	
	User insertUser(User user);

	List<UserDTO> getManagers();
	
}
