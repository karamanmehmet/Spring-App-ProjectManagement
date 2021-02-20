package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.RoleDTO;

public interface RoleService {

	List<RoleDTO> listAllRoles();

	RoleDTO findById(long id);

}
