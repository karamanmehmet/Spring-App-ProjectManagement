package com.cybertek.service;

import java.util.List;

import com.cybertek.dto.RoleDTO;
import com.cybertek.entity.Role;

public interface RoleService {

	List<RoleDTO> listAllRoles();

	RoleDTO findById(long id);

}
