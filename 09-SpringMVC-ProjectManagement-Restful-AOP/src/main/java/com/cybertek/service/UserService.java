package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;

public interface UserService {

	List<UserDTO> listAllUsers();
	
	List<UserDTO> listAllUsersByRole(String role);
	
	List<UserDTO> listManagers();
	
	UserDTO save(UserDTO dto);
	
	UserDTO findByUserName(String username);
	
	User getByUserName(String username);
	
	UserDTO update(UserDTO dto);
	
	UserDTO deActivate(String username,boolean status);
	
	boolean delete(String username);

}
